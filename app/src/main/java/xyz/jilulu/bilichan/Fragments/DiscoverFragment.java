package xyz.jilulu.bilichan.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import xyz.jilulu.bilichan.API.KonaDiscovery;
import xyz.jilulu.bilichan.Adapters.DiscoverFragmentAdapter;
import xyz.jilulu.bilichan.Models.ExploreItems;
import xyz.jilulu.bilichan.R;

/**
 * Created by jamesji on 25/4/2016.
 */
public class DiscoverFragment extends Fragment implements Callback<ExploreItems> {

    public static final String posts_to_explore = "http://konachan.net/post.json?tags=holds:false%20%20limit:100&api_version=2&filter=1&include_tags=1&include_votes=1&include_pools=1";

    @Bind(R.id.fragment_discover_recycler_view)
    public RecyclerView mRecyclerView;
    public RecyclerView.Adapter mAdapter;
    public RecyclerView.LayoutManager mLayoutManager;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://www.konachan.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    KonaDiscovery konaDiscovery = retrofit.create(KonaDiscovery.class);

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_discover, container, false);
        ButterKnife.bind(this, rootView);

        initLayoutManager();
        mRecyclerView.setLayoutManager(mLayoutManager);

        Call<ExploreItems> exploreItemsCall = konaDiscovery.getExplorePosts();
        exploreItemsCall.enqueue(this);

        return rootView;
    }

    public void initLayoutManager() {
        boolean multiColumns = PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean("compact_discovery", true);
        mRecyclerView.setHasFixedSize(true);
        if (multiColumns)
            mLayoutManager = new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL);
        else
            mLayoutManager = new LinearLayoutManager(getActivity());
    }

    @Override
    public void onResponse(Call<ExploreItems> call, retrofit2.Response<ExploreItems> response) {
        List<ExploreItems.ExploreItem> exploreItems = response.body().posts;
        mAdapter = new DiscoverFragmentAdapter(exploreItems);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onFailure(Call<ExploreItems> call, Throwable t) {
        t.printStackTrace();
    }

//    private String getFragmentTag(int viewPagerId, int fragmentPosition) {
//        return "android:switcher:" + viewPagerId + ":" + fragmentPosition;
//    }

}
