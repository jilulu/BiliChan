package xyz.jilulu.bilichan.Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import xyz.jilulu.bilichan.R;

/**
 * Created by jamesji on 25/4/2016.
 */
public class DiscoverFragmentAdapter extends RecyclerView.Adapter<DiscoverFragmentAdapter.ViewHolder> {

    private String[] links;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public ImageView imageView;

        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
            imageView = (ImageView) v.findViewById(R.id.fragment_discover_card_image_view);
        }
    }

    public DiscoverFragmentAdapter(String[] links) {
        this.links = links;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_discover_card, parent, false);
        return new ViewHolder((CardView) v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(holder.cardView.getContext()).load(links[position]).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return links.length;
    }
}
