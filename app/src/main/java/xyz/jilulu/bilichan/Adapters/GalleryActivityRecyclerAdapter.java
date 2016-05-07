package xyz.jilulu.bilichan.Adapters;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import xyz.jilulu.bilichan.Helpers.KonaObject;
import xyz.jilulu.bilichan.Helpers.UserFavObject;
import xyz.jilulu.bilichan.PhotoActivity;
import xyz.jilulu.bilichan.R;

/**
 * Created by jamesji on 29/2/2016.
 */
public class GalleryActivityRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private OnDataSetLoaded callback;
    private String tag;
    public ArrayList<KonaObject> mDataset = new ArrayList<>();
    public static final String EXTRA = xyz.jilulu.bilichan.Adapters.GalleryActivityRecyclerAdapter.class.getName();
    private CompositeSubscription compositeSubscription = new CompositeSubscription();

    public static class CardHolder extends RecyclerView.ViewHolder {
        public CardView mCardView;
        public ImageView img, icon;
        public TextView author, size;

        public CardHolder(CardView cardView) {
            super(cardView);
            mCardView = cardView;
            img = (ImageView) mCardView.findViewById(R.id.gallery_card_image_view);
            author = (TextView) mCardView.findViewById(R.id.gallery_card_author);
            size = (TextView) mCardView.findViewById(R.id.gallery_card_size);
            icon = (ImageView) mCardView.findViewById(R.id.gallery_card_status_icon);
        }
    }

    public static class ProgressHolder extends RecyclerView.ViewHolder {
        ProgressBar mProgressBar;

        public ProgressHolder(View v) {
            super(v);
            this.mProgressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        }
    }

    public GalleryActivityRecyclerAdapter(String tag, OnDataSetLoaded callback) {
        this.callback = callback;
        this.tag = tag;

        addSubscription();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ViewTypes.PROGRESS_ITEM)
            return new ProgressHolder(
                    LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.activity_gallery_card_view_progress_item, parent, false)
            );
        else if (viewType == ViewTypes.CARD_VIEW)
            return new CardHolder(
                    (CardView) LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.activity_gallery_card_view, parent, false)
            );
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return mDataset.get(position) == null ? ViewTypes.PROGRESS_ITEM : ViewTypes.CARD_VIEW;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        if (holder instanceof ProgressHolder) {
            ((ProgressHolder) holder).mProgressBar.setIndeterminate(true);
        } else {
            final CardHolder mViewHolder = (CardHolder) holder;
            Callback imageDownloaded = new Callback() {
                @Override
                public void onSuccess() {
                    mViewHolder.icon.setImageResource(R.drawable.ic_done);
                }

                @Override
                public void onError() {

                }
            };

            final KonaObject obj = mDataset.get(position);
            Picasso.with(mViewHolder.mCardView.getContext())
                    .load(obj.getPreviewURL())
                    .into(mViewHolder.img, imageDownloaded);

            mViewHolder.mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent revolutionaryIntent = new Intent(v.getContext(), PhotoActivity.class);
                    /*revolutionaryIntent.putExtra(Intent.EXTRA_TEXT, new String[] {"" + obj.getId(), obj.getTags(), obj.getPreviewURL(), obj.getFullSizeURL(), tag});*/
                    UserFavObject photoObj = new UserFavObject(obj.getId(), obj.getTags(), obj.getPreviewURL(), obj.getFullSizeURL(), tag);
                    revolutionaryIntent.putExtra(EXTRA, photoObj);
                    v.getContext().startActivity(revolutionaryIntent);
                }
            });
            mViewHolder.author.setText(obj.getAuthor());
            mViewHolder.size.setText(Integer.toString(obj.getFileSize()));
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public PostFetcher postFetcher;

    public Observable<ArrayList<KonaObject>> getObservable() {
        return Observable.create(new Observable.OnSubscribe<ArrayList<KonaObject>>() {
            @Override
            public void call(Subscriber<? super ArrayList<KonaObject>> subscriber) {
                ArrayList<KonaObject> konaObjects;
                if (postFetcher == null) postFetcher = new PostFetcher(tag);
                try {
                    konaObjects = postFetcher.fetchItems();
                    subscriber.onNext(konaObjects);
                    subscriber.onCompleted();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    private class PostFetcher {

        private OkHttpClient client = new OkHttpClient();
        private JsonParser parser = new JsonParser();

        private int pageNumber = 1;
        private String url;

        public PostFetcher(String title) {
            url = "http://konachan.net/post.json?tags=" + title +
                    "%20order:score%20rating:safe" + "&page=";
        }

        public ArrayList<KonaObject> fetchItems() throws IOException {
            String currentURL = url + pageNumber++;
            String jsonString = client.newCall(new Request.Builder().url(currentURL).build())
                    .execute().body().string();
            JsonArray jsonArray = parser.parse(jsonString).getAsJsonArray();
            ArrayList<KonaObject> konaObjects = new ArrayList<>(jsonArray.size());
            for (JsonElement jsonElement : jsonArray) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                konaObjects.add(new KonaObject(jsonObject));
            }
            return konaObjects;
        }
    }

    public void nextPage() {
//        Log.d("nextPage()", "triggered");
        mDataset.add(null);
        notifyDataSetChanged();
        addSubscription();
    }

    public void addSubscription() {
        compositeSubscription.add(getObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<KonaObject>>() {
                    @Override
                    public void onCompleted() {
                        callback.dataSetLoaded();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ArrayList<KonaObject> konaObjects) {
                        if (mDataset.size() != 0) mDataset.remove(null);
                        mDataset.addAll(konaObjects);
                        notifyDataSetChanged();
                    }
                })
        );
    }

    public interface OnDataSetLoaded {
        void dataSetLoaded();
    }

    private static final class ViewTypes {
        static final int PROGRESS_ITEM = 0;
        static final int CARD_VIEW = 1;
    }
}
