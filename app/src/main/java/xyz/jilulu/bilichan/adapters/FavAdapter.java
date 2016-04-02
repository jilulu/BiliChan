package xyz.jilulu.bilichan.adapters;

import android.content.Context;
import android.content.Intent;
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
import xyz.jilulu.bilichan.activities.RevolutionaryPhotoView;
import xyz.jilulu.bilichan.helpers.data.UserfavObject;

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
        final UserfavObject currentFav = userfavObjectArrayList.get(position);
        holder.misakaText.setText(currentFav.getTitle());
        Picasso.with(commonContext).load(currentFav.getPrevURL()).into(holder.misakaImage);
        String[] tags = currentFav.getTag().split(" ");
        for (int i = 0; i < tags.length; i++) {
            TextView tv = (TextView) LayoutInflater.from(commonContext).inflate(R.layout.tv_rounded_otg, holder.tagContainer, false);
            tv.setText(tags[i]);
            holder.tagContainer.addView(tv);
        }
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), RevolutionaryPhotoView.class);
                intent.putExtra(Intent.EXTRA_TEXT,
                        new String[]{"" + currentFav.getPostID(), currentFav.getTag(), currentFav.getPrevURL(), currentFav.getFullURL(), currentFav.getTitle()});
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userfavObjectArrayList.size();
    }
}
