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
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import xyz.jilulu.bilichan.R;
import xyz.jilulu.bilichan.activities.GalleryActivity;
import xyz.jilulu.bilichan.helpers.data.KonaTag;

/**
 * Created by jamesji on 4/3/2016.
 */
public class KonaTagAdapter extends RecyclerView.Adapter<KonaTagAdapter.ViewHolder> {
    private ArrayList<KonaTag> dataSet;
    private RecyclerView parent;
    private Context mContext;
    private int[] backgrounds;

    public KonaTagAdapter(ArrayList<KonaTag> dataSet, Context context) {
        mContext = context;
        this.dataSet = dataSet;
        backgrounds = generateBackgroundSet(dataSet.size());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_tag, parent, false);
        this.parent = (RecyclerView) parent;
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final KonaTag currentTag = dataSet.get(position);
        String tagString = currentTag.getTagName();

        holder.tag.setText(tagString);
        int count = Integer.parseInt(currentTag.getTagCount());

        holder.count.setText(count > 1000 ? count / 1000 + "k" : count + "");
        String largeType, betterVisual;
        int underscoreLocation = tagString.indexOf('_');
        largeType = "" + Character.toUpperCase(tagString.charAt(0)) + Character.toUpperCase(tagString.charAt(underscoreLocation + 1));
        holder.circledTV.setText(largeType);
        holder.circledTV.setBackgroundResource(backgrounds[position]);
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
            String drawableName = tagString + i;
            if (drawableName.contains("(")) {
                drawableName = drawableName.split("\\(")[0] + drawableName.split("\\(")[1];
                drawableName = drawableName.split("\\)")[0] + drawableName.split("\\)")[1];
            }
            Picasso.with(mContext)
                    .load(mContext.getResources().getIdentifier(drawableName, "drawable", mContext.getPackageName()))
                    .into(holder.imageViews[i]);
//            FetchThumbnailTask littleTask = new FetchThumbnailTask(tagString + i);
//            littleTask.execute();
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

    private int[] generateBackgroundSet(int size) {
        int[] background = {
                R.drawable.circle_around_text_0,
                R.drawable.circle_around_text_1,
                R.drawable.circle_around_text_2,
                R.drawable.circle_around_text_3,
                R.drawable.circle_around_text_4,
                R.drawable.circle_around_text_5,
                R.drawable.circle_around_text_6,
                R.drawable.circle_around_text_7,
                R.drawable.circle_around_text_8,
                R.drawable.circle_around_text_9
        };

        int[] ar = new int[size];
        for (int i = 0; i < size; i++) {
            ar[i] = background[(int) (Math.random() * 10)];
        }
        return ar;
    }
}
