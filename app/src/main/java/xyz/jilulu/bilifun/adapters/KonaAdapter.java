package xyz.jilulu.bilifun.adapters;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import xyz.jilulu.bilifun.R;
import xyz.jilulu.bilifun.activities.RevolutionaryPhotoView;
import xyz.jilulu.bilifun.helpers.KonaObject;

/**
 * Created by jamesji on 29/2/2016.
 */
public class KonaAdapter extends RecyclerView.Adapter<KonaAdapter.ViewHolder> {
    private ArrayList<KonaObject> mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView mCardView;
        public ImageView img, icon;
        public TextView author, size;

        public ViewHolder(CardView cardView) {
            super(cardView);
            mCardView = cardView;
            img = (ImageView) mCardView.findViewById(R.id.gallery_card_image_view);
            author = (TextView) mCardView.findViewById(R.id.gallery_card_author);
            size = (TextView) mCardView.findViewById(R.id.gallery_card_size);
            icon = (ImageView) mCardView.findViewById(R.id.gallery_card_status_icon);
        }
    }

    public KonaAdapter(ArrayList<KonaObject> dataset) {
        mDataset = dataset;
    }

    @Override
    public KonaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_gallery_card_view, parent, false);
        // set the view's size, margins, paddings and layout parameters
        // ...
        ViewHolder vh = new ViewHolder((CardView) v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final ViewHolder mViewHolder = holder;
        Callback imageDownloaded = new Callback() {
            @Override
            public void onSuccess() {
                mViewHolder.icon.setImageResource(R.drawable.view_done);
            }

            @Override
            public void onError() {

            }
        };

        final KonaObject obj = mDataset.get(position);
        Picasso.with(holder.mCardView.getContext())
                .load(obj.getPreviewURL())
                .into(holder.img, imageDownloaded);
//        holder.img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent fullScreenIntent = new Intent(v.getContext(), FullscreenActivity.class);
//                String[] extraTexts = {"", obj.getFullSizeURL()};
//                fullScreenIntent.putExtra(Intent.EXTRA_TEXT, extraTexts);
//                v.getContext().startActivity(fullScreenIntent);
//            }
//        });
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent revolutionaryIntent = new Intent(v.getContext(), RevolutionaryPhotoView.class);
                revolutionaryIntent.putExtra(Intent.EXTRA_TEXT, obj.getLargeSizeURL());
                v.getContext().startActivity(revolutionaryIntent);
            }
        });
        holder.author.setText(obj.getAuthor());
        holder.size.setText(Integer.toString(obj.getFileSize()));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
