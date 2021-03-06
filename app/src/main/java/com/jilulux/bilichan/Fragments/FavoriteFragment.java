package com.jilulux.bilichan.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.jilulux.bilichan.Adapters.FavoriteFragmentAdapter;
import com.jilulux.bilichan.Helpers.FavoriteDBOperator;
import com.jilulux.bilichan.Helpers.UserFavObject;
import com.jilulux.bilichan.R;

/**
 * Created by jamesji on 24/4/2016.
 */
public class FavoriteFragment extends Fragment {

    @Bind(R.id.fav_recycler)
    RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<UserFavObject> fav;
    private Integer postCount = null;
    FavoriteDBOperator dbOp;

    public FavoriteFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorite, container, false);
        ButterKnife.bind(this, rootView);
//        int i = getArguments().getInt(MainActivity.ARG_FRAGMENT_POSITION);
//        String fragmentTitle = getResources().getStringArray(R.array.fragment_title_array)[i];
//        getActivity().setTitle(fragmentTitle);

        dbOp = new FavoriteDBOperator(getActivity());
        postCount = dbOp.queryFavCount();
        fav = dbOp.queryDB();

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(rootView.getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new FavoriteFragmentAdapter(fav);
        recyclerView.setAdapter(adapter);

        return rootView;
    }
    @Override
    public void onStart() {
        super.onStart();
        if (postCount != null && postCount != dbOp.queryFavCount()) {
            notifyAdapterDataChanged();
        }
    }

    public void notifyAdapterDataChanged() {
        Log.d("FavoriteFragment", "Reloaded dataset. ");
        FavoriteDBOperator dbOp = new FavoriteDBOperator(getActivity());
        fav.clear();
        fav.addAll(dbOp.queryDB());
        adapter.notifyDataSetChanged();
    }
}
