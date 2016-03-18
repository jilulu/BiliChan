package xyz.jilulu.bilichan.fragments;

import android.app.Fragment;
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
import xyz.jilulu.bilichan.R;
import xyz.jilulu.bilichan.activities.MainActivity;
import xyz.jilulu.bilichan.adapters.FavAdapter;
import xyz.jilulu.bilichan.helpers.DBOperator;
import xyz.jilulu.bilichan.helpers.UserfavObject;

/**
 * Created by jamesji on 11/3/2016.
 */
public class FavFragment extends Fragment {

    @Bind(R.id.fav_recycler)
    RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public FavFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fav, container, false);
        ButterKnife.bind(this, rootView);
        int i = getArguments().getInt(MainActivity.ARG_FRAGMENT_POSITION);
        String fragmentTitle = getResources().getStringArray(R.array.fragment_title_array)[i];
        getActivity().setTitle(fragmentTitle);

        DBOperator dbOp = new DBOperator(getActivity());
        ArrayList<UserfavObject> fav = dbOp.queryDB();
        dbOp.closeDB();

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(rootView.getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new FavAdapter(fav);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

}
