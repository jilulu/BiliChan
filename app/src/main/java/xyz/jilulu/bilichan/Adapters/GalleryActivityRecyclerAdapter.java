package xyz.jilulu.bilichan.Adapters;

/**
 * Created by jamesji on 24/4/2016.
 */

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import xyz.jilulu.bilichan.Helpers.KonaObject;
import xyz.jilulu.bilichan.PhotoActivity;
import xyz.jilulu.bilichan.R;

/**
 * Created by jamesji on 29/2/2016.
 */
public class GalleryActivityRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private String tag;
    private ArrayList<KonaObject> mDataset;

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

    public GalleryActivityRecyclerAdapter(ArrayList<KonaObject> dataset, String tag) {
        mDataset = dataset;
        this.tag = tag;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == 1) {
            return new ProgressHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_gallery_card_view_progress_item, parent, false));
        }
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_gallery_card_view, parent, false);
        // set the view's size, margins, paddings and layout parameters
        // ...
        CardHolder vh = new CardHolder((CardView) v);
        return vh;
    }

    @Override
    public int getItemViewType(int position) {
        return mDataset.get(position) != null ? 0 : 1;
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
                    revolutionaryIntent.putExtra(Intent.EXTRA_TEXT, new String[] {"" + obj.getId(), obj.getTags(), obj.getPreviewURL(), obj.getFullSizeURL(), tag});
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
}
