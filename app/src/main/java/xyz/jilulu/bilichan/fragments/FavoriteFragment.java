package xyz.jilulu.bilichan.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import xyz.jilulu.bilichan.Adapters.FavoriteFragmentAdapter;
import xyz.jilulu.bilichan.Helpers.FavoriteDBOperator;
import xyz.jilulu.bilichan.Helpers.UserFavObject;
import xyz.jilulu.bilichan.R;

/**
 * Created by jamesji on 24/4/2016.
 */
public class FavoriteFragment extends android.support.v4.app.Fragment {

    @Bind(R.id.fav_recycler)
    RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<UserFavObject> fav;

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

        FavoriteDBOperator dbOp = new FavoriteDBOperator(getActivity());
        fav = dbOp.queryDB();
        dbOp.closeDB();

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(rootView.getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new FavoriteFragmentAdapter(fav);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    public void notifyAdapterDataChanged() {
        FavoriteDBOperator dbOp = new FavoriteDBOperator(getActivity());
        fav.clear();
        fav.addAll(dbOp.queryDB());
        adapter.notifyDataSetChanged();
    }
}
