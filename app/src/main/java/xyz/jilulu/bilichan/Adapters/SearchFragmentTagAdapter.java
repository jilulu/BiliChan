package xyz.jilulu.bilichan.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import xyz.jilulu.bilichan.Fragments.SearchFragment;
import xyz.jilulu.bilichan.GalleryActivity;
import xyz.jilulu.bilichan.Models.KonaTag;
import xyz.jilulu.bilichan.R;

/**
 * Created by jamesji on 23/4/2016.
 */
public class SearchFragmentTagAdapter extends ArrayAdapter<KonaTag> implements View.OnClickListener {
    private int resourceID;

    public SearchFragmentTagAdapter(Context context, int layoutResourceID, List<KonaTag> tags) {
        super(context, layoutResourceID, tags);
        resourceID = layoutResourceID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        KonaTag currentTag = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceID, null);
            viewHolder = new ViewHolder();
            viewHolder.count = (TextView) view.findViewById(R.id.tag_kona_search_post_count);
            viewHolder.tag = (TextView) view.findViewById(R.id.tag_kona_search_post_tag);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tag.setText(currentTag.name);
        viewHolder.count.setText(String.format(Locale.ENGLISH, "%d", currentTag.count));
        view.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        TextView tagTextView = (TextView) v.findViewById(R.id.tag_kona_search_post_tag);
        String tag = tagTextView.getText().toString();
        Intent intent = new Intent(getContext(), GalleryActivity.class);
        intent.putExtra(SearchFragment.EXTRA, tag);
        getContext().startActivity(intent);
    }

    class ViewHolder {
        TextView count;
        TextView tag;
    }
}
