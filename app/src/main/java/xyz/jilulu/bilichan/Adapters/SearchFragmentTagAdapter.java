package xyz.jilulu.bilichan.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import xyz.jilulu.bilichan.Helpers.KonaTag;
import xyz.jilulu.bilichan.R;

/**
 * Created by jamesji on 23/4/2016.
 */
public class SearchFragmentTagAdapter extends ArrayAdapter<KonaTag> {
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
        viewHolder.tag.setText(currentTag.getTagName());
        viewHolder.count.setText(currentTag.getTagCount());

        return view;
    }

    class ViewHolder {
        TextView count;
        TextView tag;
    }
}
