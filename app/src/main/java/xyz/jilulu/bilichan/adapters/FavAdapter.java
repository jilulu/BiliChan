package xyz.jilulu.bilichan.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.apmem.tools.layouts.FlowLayout;

import java.util.ArrayList;

import xyz.jilulu.bilichan.R;
import xyz.jilulu.bilichan.helpers.UserfavObject;

/**
 * Created by jamesji on 18/3/2016.
 */
public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder> {
    private ArrayList<UserfavObject> userfavObjectArrayList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView misakaText;
        public ImageView misakaImage;
        public CardView cv;
        public FlowLayout tagContainer;

        public ViewHolder(View cardView) {
            super(cardView);
            misakaText = (TextView) cardView.findViewById(R.id.misaka_text);
            misakaImage = (ImageView) cardView.findViewById(R.id.misaka_image);
            cv = (CardView) cardView;
            tagContainer = (FlowLayout) cardView.findViewById(R.id.tag_flow_manager);
        }
    }

    public FavAdapter(ArrayList<UserfavObject> userfavObjectArrayList) {
        this.userfavObjectArrayList = userfavObjectArrayList;
    }

    @Override
    public FavAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(FavAdapter.ViewHolder holder, int position) {
        Context commonContext = holder.cv.getContext();
        UserfavObject currentFav = userfavObjectArrayList.get(position);
        holder.misakaText.setText(currentFav.getTitle().split(" ")[0]);
        Picasso.with(commonContext).load(currentFav.getPrevURL()).into(holder.misakaImage);
        String[] tags = currentFav.getTitle().split(" ");
        for (int i = 0; i < tags.length; i++) {
            TextView tv = (TextView) LayoutInflater.from(commonContext).inflate(R.layout.tv_rounded_otg, holder.tagContainer, false);
            tv.setText(tags[i]);
            holder.tagContainer.addView(tv);
        }

    }

    @Override
    public int getItemCount() {
        return userfavObjectArrayList.size();
    }
}
