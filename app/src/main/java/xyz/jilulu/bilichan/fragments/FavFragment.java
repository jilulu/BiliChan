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
import xyz.jilulu.bilichan.helpers.db.DBOperator;
import xyz.jilulu.bilichan.helpers.data.UserfavObject;

/**
 * Created by jamesji on 11/3/2016.
 */
public class FavFragment extends Fragment {

    @Bind(R.id.fav_recycler)
    RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<UserfavObject> fav;

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
        fav = dbOp.queryDB();
        dbOp.closeDB();

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(rootView.getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new FavAdapter(fav);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    public void notifyAdapterDataChanged() {
        DBOperator dbOp = new DBOperator(getActivity());
        fav.clear();
        fav.addAll(dbOp.queryDB());
        adapter.notifyDataSetChanged();
    }

//    // Exploring fragment lifecycle
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        Toast.makeText(context, "onAttach()", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        Toast.makeText(getActivity(), "onActivityCreated()", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        Toast.makeText(getActivity(), "onDestroyView()", Toast.LENGTH_SHORT).show();
//
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        Toast.makeText(getActivity(), "onDetach()", Toast.LENGTH_SHORT).show();
//
//    }
}
