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

import java.util.List;

import xyz.jilulu.bilichan.helpers.data.MuseMember;
import xyz.jilulu.bilichan.R;
import xyz.jilulu.bilichan.activities.MuseMemberActivity;

/**
 * Created by jamesji on 23/2/2016.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<MuseMember> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public CardView mCardView;

        public ViewHolder(CardView cardView) {
            super(cardView);
            mCardView = cardView;
            final Context context = mCardView.getContext();
            mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent museAmwayIntent = new Intent(context, MuseMemberActivity.class);
                    String intentKey = Intent.EXTRA_TEXT;
                    museAmwayIntent.putExtra(intentKey,
                            ((TextView) mCardView.findViewById(R.id.full_ja_name)).getText());
                    mCardView.getContext().startActivity(museAmwayIntent);
                }
            });
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<MuseMember> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_main_recycler_cardview_structure, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder((CardView) v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        String sepChar = "（";
        String[] museMemberNameStrings = mDataset.get(position).getMuseMemberName().split(sepChar);
        museMemberNameStrings[1] = museMemberNameStrings[1].substring(0, museMemberNameStrings[1].length() - 1);
        ((TextView) holder.mCardView.findViewById(R.id.full_ja_name)).setText(museMemberNameStrings[0]);
        ((TextView) holder.mCardView.findViewById(R.id.hiragana_name)).setText(museMemberNameStrings[1]);
        ((ImageView) holder.mCardView.findViewById(R.id.awesome_muse_member_image_view)).setImageResource(
                mDataset.get(position).getMuseMemberProfileID()
        );
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}
