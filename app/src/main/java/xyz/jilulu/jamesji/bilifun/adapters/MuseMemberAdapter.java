package xyz.jilulu.jamesji.bilifun.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import xyz.jilulu.jamesji.bilifun.R;
import xyz.jilulu.jamesji.bilifun.activities.FullscreenActivity;
import xyz.jilulu.jamesji.bilifun.helpers.MuseMemberProfiles;

/**
 * Created by jamesji on 25/2/2016.
 */
public class MuseMemberAdapter extends RecyclerView.Adapter<MuseMemberAdapter.ViewHolder> {
    private MuseMemberProfiles liveMuseMember;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public CardView mCardView;

        public ViewHolder(CardView cardView) {
            super(cardView);
            mCardView = cardView;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MuseMemberAdapter(MuseMemberProfiles myDataset) {
        liveMuseMember = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MuseMemberAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_muse_member_recycler_view, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder((CardView) v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        View commonParent = holder.mCardView.findViewById(R.id.commonParent);
        ImageView profile = (ImageView) commonParent.findViewById(R.id.museMemberProfileImage);
        TextView jaName = (TextView) commonParent.findViewById(R.id.museMemberName);
        TextView hiraganaSpelling = (TextView) commonParent.findViewById(R.id.museMemberNameHiragana);
        TextView romajiSpelling = (TextView) commonParent.findViewById(R.id.museMemberNameRomaji);
        TextView basicText = (TextView) commonParent.findViewById(R.id.basicsText);
        TextView mangaText = (TextView) commonParent.findViewById(R.id.mangaText);
        TextView animeText = (TextView) commonParent.findViewById(R.id.animeText);

        profile.setImageResource(liveMuseMember.getResID());
        jaName.setText(liveMuseMember.getJaName());
        hiraganaSpelling.setText(liveMuseMember.getHiragana());
        romajiSpelling.setText(liveMuseMember.getRomaji());
        basicText.setText(liveMuseMember.getBasics());
        mangaText.setText(liveMuseMember.getMangaText());
        animeText.setText(liveMuseMember.getAnimeText());

        Context context = holder.mCardView.getContext();
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        final boolean newRes = sharedPreferences.getBoolean("use_new_res_set", true);
        String req;
        if (newRes) {
            req = "http://android.jilulu.xyz/an_res/" + liveMuseMember.getRomaji().split(" ")[1].toLowerCase() + ".jpg";
            Picasso.with(context).load(req).into(profile);
        }

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] extras = {liveMuseMember.getJaName(), Integer.toString(liveMuseMember.getResID()), liveMuseMember.getRomaji()};
                if (newRes) {
                    extras[1] = "http://android.jilulu.xyz/an_res/" + liveMuseMember.getRomaji().split(" ")[1].toLowerCase() + ".jpg";
                }
                Intent fullScreenIntent=  new Intent(v.getContext(), FullscreenActivity.class).putExtra(Intent.EXTRA_TEXT, extras);
                v.getContext().startActivity(fullScreenIntent);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return 1;
    }

}
