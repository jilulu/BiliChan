package xyz.jilulu.bilichan.Adapters;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import xyz.jilulu.bilichan.Helpers.UserFavObject;
import xyz.jilulu.bilichan.Models.ExploreItems;
import xyz.jilulu.bilichan.PhotoActivity;
import xyz.jilulu.bilichan.R;

/**
 * Created by jamesji on 25/4/2016.
 */
public class DiscoverFragmentAdapter extends RecyclerView.Adapter<DiscoverFragmentAdapter.ViewHolder> {

    //    private String[] links;
    private ArrayList<ExploreItems.ExploreItem> exploreItems = new ArrayList<>();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public ImageView imageView;

        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
            imageView = (ImageView) v.findViewById(R.id.fragment_discover_card_image_view);
        }
    }

    public DiscoverFragmentAdapter(List<ExploreItems.ExploreItem> exploreItems) {
        this.exploreItems.addAll(exploreItems);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_discover_card, parent, false);
        return new ViewHolder((CardView) v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Picasso.with(holder.cardView.getContext())
                .load(exploreItems.get(position).preview_url)
                .into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PhotoActivity.class);
                intent.putExtra(GalleryActivityRecyclerAdapter.EXTRA, new UserFavObject(exploreItems.get(holder.getAdapterPosition()), "discovered"));
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return exploreItems.size();
    }
}
