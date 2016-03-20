package xyz.jilulu.bilichan.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import xyz.jilulu.bilichan.R;
import xyz.jilulu.bilichan.activities.GalleryActivity;
import xyz.jilulu.bilichan.helpers.KonaTag;

/**
 * Created by jamesji on 4/3/2016.
 */
public class KonaTagAdapter extends RecyclerView.Adapter<KonaTagAdapter.ViewHolder> {
    private ArrayList<KonaTag> dataSet;
    private RecyclerView parent;
    private LruCache<String, BitmapDrawable> mMemoryCache;
    private File cacheDir;

    public KonaTagAdapter(ArrayList<KonaTag> dataSet) {
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
        if (getBitmapFromMemoryCache(key) == null)
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
        String _tagName = currentTag.getTagName();

        holder.tag.setText(_tagName);
        int count = Integer.parseInt(currentTag.getTagCount());

        holder.count.setText(count > 1000 ? count / 1000 + "k" : count + "");
        String largeType, betterVisual;
        int underscoreLocation = _tagName.indexOf('_');
        largeType = "" + Character.toUpperCase(_tagName.charAt(0)) + Character.toUpperCase(_tagName.charAt(underscoreLocation + 1));
        holder.circledTV.setText(largeType);
        betterVisual = underscoreLocation == -1 ? Character.toUpperCase(_tagName.charAt(0)) + _tagName.substring(1) :
                (Character.toUpperCase(_tagName.charAt(0)) + _tagName.substring(1, underscoreLocation) +
                        " " + Character.toUpperCase(_tagName.charAt(underscoreLocation + 1)) + _tagName.substring(underscoreLocation + 2));
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


        BitmapDrawable[] bmpDrawable = new BitmapDrawable[4];
        for (int i = 0; i < bmpDrawable.length; i++) {
            holder.imageViews[i].setImageResource(R.drawable.fav_holder);
            holder.imageViews[i].setTag(_tagName + i);
            bmpDrawable[i] = getBitmapFromMemoryCache(_tagName + i);
            File tempFile = new File(cacheDir, _tagName + i);
            if (bmpDrawable[i] == null && tempFile.exists()) {
                try {
                    BufferedSource source = Okio.buffer(Okio.source(tempFile));
                    byte[] bmpBytes = source.readByteArray();
                    addBitmapToMemoryCache(_tagName + i, new BitmapDrawable(BitmapFactory.decodeByteArray(bmpBytes, 0, bmpBytes.length)));
                    bmpDrawable[i] = getBitmapFromMemoryCache(_tagName + i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if (bmpDrawable[0] != null && bmpDrawable[1] != null && bmpDrawable[2] != null && bmpDrawable[3] != null) {
            for (int i = 0; i < bmpDrawable.length; i++)
                holder.imageViews[i].setImageDrawable(bmpDrawable[i]);
        } else{
            (new FetchThumbnailTask()).execute(_tagName);
            Log.d("BindViewHolder", position + ": ViewHolder Binded");
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

    class FetchThumbnailTask extends AsyncTask<String, Integer, Void> {
        private final String[] url_json = {"http://konachan.net/post.json?tags=", "%20order:score%20rating:safe"};
        private String tag;
        private ConnectionPool pool = new ConnectionPool(1, 5, TimeUnit.MINUTES);

        private final OkHttpClient client = new OkHttpClient.Builder()
                .connectionPool(pool)
                .build();

        @Override
        protected Void doInBackground(final String... params) {
            tag = params[0];
            String url = url_json[0] + tag + url_json[1];
            Log.d("OKHTTP", url);
            Request req = new Request.Builder()
                    .url(url)
                    .build();
            client.newCall(req).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    publishProgress(-1);
                }

                @Override
                public void onResponse(Call call, final Response res) throws IOException {
                    String json_response = res.body().string();
                    JsonParser parser = new JsonParser();
                    JsonArray arr = parser.parse(json_response).getAsJsonArray();
                    String[] imagePreviewLinks = new String[4];
                    for (int i = 0; i < imagePreviewLinks.length; i++) {
                        final int currentDownloadNum = i;
                        imagePreviewLinks[i] = arr.get(i).getAsJsonObject().get("preview_url").getAsString();

                        final File tempFile = new File(cacheDir, tag + currentDownloadNum);

                        client.newCall(new Request.Builder().url(imagePreviewLinks[i]).build()).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                publishProgress(-1);
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                byte[] responseBytes = response.body().bytes();
                                Bitmap bmp = BitmapFactory.decodeByteArray(responseBytes, 0, responseBytes.length);
                                BitmapDrawable drawable = new BitmapDrawable(bmp);

                                BufferedSink sink = Okio.buffer(Okio.sink(tempFile));
                                sink.write(responseBytes);
                                sink.close();

                                addBitmapToMemoryCache(tag + currentDownloadNum, drawable);
                                publishProgress(currentDownloadNum);
                                Log.d("OKHTTP", "Image No." + (currentDownloadNum + 1) + " downloaded. Added to Cache. ");
                            }
                        });

                    }

                }

            });

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            // Server denied connection
            if (values[0] == -1) {
                Toast.makeText(parent.getContext(), "You're hurting KonaChan. ", Toast.LENGTH_SHORT).show();
                return;
            }

            int progressUpdatedItem = values[0];
            String key = tag + progressUpdatedItem;
            ImageView imgV = (ImageView) parent.findViewWithTag(key);
            BitmapDrawable bmpD = getBitmapFromMemoryCache(key);
            if (imgV != null && bmpD != null) {
                imgV.setImageDrawable(bmpD);
            }
        }

    }
}
