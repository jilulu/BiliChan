package xyz.jilulu.bilichan.Fragments;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import xyz.jilulu.bilichan.Adapters.DiscoverFragmentAdapter;
import xyz.jilulu.bilichan.R;

/**
 * Created by jamesji on 25/4/2016.
 */
public class DiscoverFragment extends Fragment {

    public static final String posts_to_explore = "http://konachan.net/post.json?tags=holds:false%20%20limit:100&api_version=2&filter=1&include_tags=1&include_votes=1&include_pools=1";

    @Bind(R.id.fragment_discover_recycler_view)
    public RecyclerView mRecyclerView;
    public RecyclerView.Adapter mAdapter;
    public RecyclerView.LayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_discover, container, false);
        ButterKnife.bind(this, rootView);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL);
//        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        new FetchPhotoTask().execute(posts_to_explore);

        return rootView;
    }

    class FetchPhotoTask extends AsyncTask<String, Void, Void> {
        ArrayList<String> links = new ArrayList<>();
        @Override
        protected Void doInBackground(String... params) {
            String url = params[0];
            OkHttpClient client = new OkHttpClient.Builder().build();
            Request request = new Request.Builder().url(url).build();
            try {
                Response response = client.newCall(request).execute();
                System.out.println(response.headers());
                String jsonBody = response.body().string();
                JsonObject jsonDOM = new JsonParser().parse(jsonBody).getAsJsonObject();
                JsonArray posts = jsonDOM.get("posts").getAsJsonArray();
                for (int i = 0; i < posts.size(); i++) {
                    links.add(posts.get(i).getAsJsonObject().get("preview_url").getAsString());
                }
//                while (iterator.hasNext()) {
//                    links.add(posts.iterator().next().getAsJsonObject().get("preview_url").getAsString());
//                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mAdapter = new DiscoverFragmentAdapter(links.toArray(new String[0]));
            mRecyclerView.setAdapter(mAdapter);
        }
    }

}
