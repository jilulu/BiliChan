package xyz.jilulu.bilichan.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
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

import xyz.jilulu.bilichan.Helpers.UserFavObject;
import xyz.jilulu.bilichan.PhotoActivity;
import xyz.jilulu.bilichan.R;

/**
 * Created by jamesji on 24/4/2016.
 */
public class FavoriteFragmentAdapter extends RecyclerView.Adapter<FavoriteFragmentAdapter.ViewHolder> {
    private ArrayList<UserFavObject> userfavObjectArrayList;
    private Typeface robotoSlab;

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

    public FavoriteFragmentAdapter(ArrayList<UserFavObject> userfavObjectArrayList) {
        this.userfavObjectArrayList = userfavObjectArrayList;
    }

    @Override
    public FavoriteFragmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_favorite_card_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(FavoriteFragmentAdapter.ViewHolder holder, int position) {
        Context commonContext = holder.cv.getContext();
        if (robotoSlab == null)
            robotoSlab = Typeface.createFromAsset(commonContext.getAssets(), "robotoslab_regular.ttf");
        final UserFavObject currentFav = userfavObjectArrayList.get(position);
        holder.misakaText.setText(currentFav.getTitle());
        holder.misakaText.setTypeface(robotoSlab);
        Picasso.with(commonContext).load(currentFav.getPrevURL()).into(holder.misakaImage);
        String[] tags = currentFav.getTag().split(" ");
        for (String tag : tags) {
            TextView tv = (TextView) LayoutInflater.from(commonContext).inflate(R.layout.fragment_favorite_card_item_textview, holder.tagContainer, false);
            tv.setText(tag);
            holder.tagContainer.addView(tv);
        }
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PhotoActivity.class);
                intent.putExtra(GalleryActivityRecyclerAdapter.EXTRA, currentFav);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userfavObjectArrayList.size();
    }
}
