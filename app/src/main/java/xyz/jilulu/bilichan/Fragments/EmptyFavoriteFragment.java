package xyz.jilulu.bilichan.Fragments;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import xyz.jilulu.bilichan.Helpers.FavoriteDBOperator;
import xyz.jilulu.bilichan.R;

public class EmptyFavoriteFragment extends Fragment {

    public EmptyFavoriteFragment() {
        // Required empty public constructor
    }

    public static EmptyFavoriteFragment newInstance() {
        return new EmptyFavoriteFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_empty_favorite_container, container, false);
//        reconstructView();
        return rootView;
    }

    // The user may have favorited or unfavorited certain posts, need to reconstruct the view
    @Override
    public void onResume() {
        super.onResume();
        reconstructView();
    }

    public void reconstructView() {
        FavoriteDBOperator dbOperator = new FavoriteDBOperator(getActivity());
        int numRecords = dbOperator.queryFavCount();
        if (numRecords != 0) inflateFavoriteFragment();
        else inflatePlaceHolderFragment();
    }

    public void inflateFavoriteFragment() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_empty_favorite_container, new FavoriteFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void inflatePlaceHolderFragment() {
        getFragmentManager().beginTransaction().replace(R.id.fragment_empty_favorite_container, new EmptyFragmentPlaceHolder()).commit();
    }

    public static class EmptyFragmentPlaceHolder extends Fragment {
        OnNavigationButtonClicked callBack;
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_empty_favorite_content, container, false);
            Button searchButton = (Button) rootView.findViewById(R.id.fragment_empty_favorite_search_button);
            Button discoverButton = (Button) rootView.findViewById(R.id.fragment_empty_favorite_discover_button);
            searchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callBack.onButtonClicked(v);
                }
            });
            discoverButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callBack.onButtonClicked(v);
                }
            });
            return rootView;
        }

        @TargetApi(23)
        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            try{
                callBack = (OnNavigationButtonClicked) getActivity();
            } catch (ClassCastException e) {
                throw new RuntimeException(getActivity().toString() + " must implement OnNavigationButtonClicked");
            }
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            try {
                callBack = (OnNavigationButtonClicked) activity;
            } catch (ClassCastException e) {
                throw new RuntimeException(getActivity().toString() + " must implement OnNavigationButtonClicked");
            }
        }

        public interface OnNavigationButtonClicked {
            void onButtonClicked(View v);
        }

    }
}

