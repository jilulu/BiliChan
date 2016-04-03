package xyz.jilulu.bilichan.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import xyz.jilulu.bilichan.R;
import xyz.jilulu.bilichan.activities.GalleryActivity;
import xyz.jilulu.bilichan.helpers.data.KonaTag;

/**
 * Created by jamesji on 4/3/2016.
 */
public class KonaTagAdapter extends RecyclerView.Adapter<KonaTagAdapter.ViewHolder> {
    private ArrayList<KonaTag> dataSet;
    private RecyclerView parent;
    private LruCache<String, BitmapDrawable> mMemoryCache;
    private File cacheDir;
    private HashMap<String, JsonArray> konaChanResponses = new HashMap<>();
    private ArrayList<String> httpRequestInProgress = new ArrayList<>();
    private Context mContext;

    public KonaTagAdapter(ArrayList<KonaTag> dataSet, Context context) {
        mContext = context;
        this.dataSet = dataSet;
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 2;
        System.out.println(cacheSize);
        mMemoryCache = new LruCache<String, BitmapDrawable>(cacheSize) {
            @Override
            protected int sizeOf(String key, BitmapDrawable drawable) {
                return drawable.getBitmap().getByteCount();
            }
        };
    }

    public void addBitmapToMemoryCache(String key, BitmapDrawable bitmapDrawable) {
        if (getBitmapFromMemoryCache(key) == null && bitmapDrawable != null)
            mMemoryCache.put(key, bitmapDrawable);
    }

    public BitmapDrawable getBitmapFromMemoryCache(String key) {
        return mMemoryCache.get(key);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_tag, parent, false);
        this.parent = (RecyclerView) parent;
        ViewHolder vh = new ViewHolder(v);
        if (cacheDir == null) {
            cacheDir = parent.getContext().getCacheDir();
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final KonaTag currentTag = dataSet.get(position);
        String tagString = currentTag.getTagName();

        holder.tag.setText(tagString);
        int count = Integer.parseInt(currentTag.getTagCount());

        holder.count.setText(count > 1000 ? count / 1000 + "k" : count + "");
        String largeType, betterVisual;
        int underscoreLocation = tagString.indexOf('_');
        largeType = "" + Character.toUpperCase(tagString.charAt(0)) + Character.toUpperCase(tagString.charAt(underscoreLocation + 1));
        holder.circledTV.setText(largeType);
        betterVisual = underscoreLocation == -1 ? Character.toUpperCase(tagString.charAt(0)) + tagString.substring(1) :
                (Character.toUpperCase(tagString.charAt(0)) + tagString.substring(1, underscoreLocation) +
                        " " + Character.toUpperCase(tagString.charAt(underscoreLocation + 1)) + tagString.substring(underscoreLocation + 2));
        holder.betterVisualization.setText(betterVisual);

        final Context ct = holder.cv.getContext();

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ct, "Clicked", Toast.LENGTH_SHORT).show();
                String[] url = {currentTag.getTagName(), currentTag.getTagName()};
                Intent konaIntent = new Intent(ct, GalleryActivity.class);
                konaIntent.putExtra(Intent.EXTRA_TEXT, url);
                ct.startActivity(konaIntent);
            }
        });

        for (int i = 0; i < 4; i++) {
            holder.imageViews[i].setTag(tagString + i);
            holder.imageViews[i].setImageResource(R.drawable.fav_holder);
            FetchThumbnailTask littleTask = new FetchThumbnailTask(tagString + i);
            littleTask.execute();
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView count;
        TextView tag;
        TextView circledTV;
        TextView betterVisualization;
        ImageView[] imageViews = new ImageView[4];

        public ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.search_tag_card_view);
            count = (TextView) cv.findViewById(R.id.post_count);
            tag = (TextView) cv.findViewById(R.id.post_tag);
            circledTV = (TextView) cv.findViewById(R.id.search_tag_circled_textview);
            betterVisualization = (TextView) cv.findViewById(R.id.better_tag_visualization);
            imageViews[0] = (ImageView) cv.findViewById(R.id.search_tag_preview_1);
            imageViews[1] = (ImageView) cv.findViewById(R.id.search_tag_preview_2);
            imageViews[2] = (ImageView) cv.findViewById(R.id.search_tag_preview_3);
            imageViews[3] = (ImageView) cv.findViewById(R.id.search_tag_preview_4);
        }
    }

    class FetchThumbnailTask extends AsyncTask<Void, Integer, Void> {

        /*
            There're in total three case senarios to be considered:
            1. In LRU memory cache. -> Fetch directly.
            2. In local disk cache. -> Fetch and add to LRU cache to speed up later IO.
            3. In the cloud.        -> Fetch and add to both LRU and disk cache.
         */

        final String[] url_json = {"http://konachan.net/post.json?tags=", "%20order:score%20rating:safe"};

        ConnectionPool pool = new ConnectionPool(1, 5, TimeUnit.MINUTES);
        final OkHttpClient client = new OkHttpClient.Builder()
                .connectionPool(pool)
                .build();

        private String tag;

        private String imagePreviewLink;

        public FetchThumbnailTask(String tag) {
            this.tag = tag;
        }

        private Bitmap fetchedBitmap;

        @Override
        protected Void doInBackground(Void... params) {
            final BitmapDrawable drawable = getBitmapFromMemoryCache(tag);
            if (drawable != null) { // CASE 1 IN LRU CACHE
                fetchedBitmap = drawable.getBitmap();
                Log.d("LRU", tag);
                return null;
            } else if (new File(cacheDir, tag).exists()) { // CASE 2 IN DISK CACHE
                try {
                    BufferedSource bs = Okio.buffer(Okio.source(new File(cacheDir, tag)));
                    byte[] bytes = bs.readByteArray();
                    addBitmapToMemoryCache(tag, new BitmapDrawable(BitmapFactory.decodeByteArray(bytes, 0, bytes.length)));
                    fetchedBitmap = getBitmapFromMemoryCache(tag).getBitmap();
                    Log.d("DiskCache", tag);
                    return null;
                } catch (IOException ee) { // No disk cache
                    ee.printStackTrace();
                }
            } else { // CASE 3 IN THE CLOUD
                final String realTag = tag.substring(0, tag.length() - 1);
                final int imageNum = Integer.parseInt(tag.substring(tag.length() - 1));
                if (konaChanResponses.containsKey(realTag)) {
                    imagePreviewLink = konaChanResponses.get(realTag).get(imageNum).getAsJsonObject().get("preview_url").getAsString();
                    return null;
                } else {
                    boolean DOMDownloadedByAnotherThread = false;
                    while (httpRequestInProgress.contains(realTag)) {
                        try {
                            Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        DOMDownloadedByAnotherThread = true;
                    }
                    if (DOMDownloadedByAnotherThread) {
                        imagePreviewLink = konaChanResponses.get(realTag).get(imageNum).getAsJsonObject().get("preview_url").getAsString();
                        return null;
                    }
                    Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
                    httpRequestInProgress.add(realTag);
                    String url = url_json[0] + realTag + url_json[1];
                    Log.d("OKHTTP", url);
                    Request req = new Request.Builder().url(url).build();
                    try {
                        Response response = client.newCall(req).execute();
                        String json_response = response.body().string();
                        JsonParser parser = new JsonParser();
                        JsonArray arr = parser.parse(json_response).getAsJsonArray();
                        konaChanResponses.put(realTag, arr);
                        imagePreviewLink = konaChanResponses.get(realTag).get(imageNum).getAsJsonObject().get("preview_url").getAsString();
                        httpRequestInProgress.remove(realTag);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            }
            return null;

        }


        @Override
        protected void onPostExecute(Void aVoid) {
            if (imagePreviewLink == null && fetchedBitmap != null) {
                ImageView imgV = (ImageView) parent.findViewWithTag(tag);
                if (imgV != null)
                    imgV.setImageBitmap(fetchedBitmap);
            }
            else if (imagePreviewLink != null) {
                final ImageView imageView = (ImageView) parent.findViewWithTag(tag);
                if (imageView != null) {
                    Picasso.with(mContext).load(imagePreviewLink).into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            try {
                                BufferedSink bs = Okio.buffer(Okio.sink(new File(cacheDir, tag)));
                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                bs.write(stream.toByteArray());
                                bs.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            imageView.setImageBitmap(bitmap);
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    });
                }
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            switch (values[0]) {
                case -1:
                    Toast.makeText(parent.getContext(), "You're hurting KonaChan. ", Toast.LENGTH_SHORT).show();
                    break;
                case 0:
                    ImageView v = (ImageView) parent.findViewWithTag(tag);
                    if (v != null)
                        v.setImageBitmap(
                                getBitmapFromMemoryCache(tag).getBitmap()
                        );
                    break;
            }
        }

    }
}
