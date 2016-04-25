package xyz.jilulu.bilichan.Adapters;


import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import xyz.jilulu.bilichan.Fragments.DiscoverFragment;
import xyz.jilulu.bilichan.Fragments.EmptyFavoriteFragment;
import xyz.jilulu.bilichan.Fragments.SearchFragment;
import xyz.jilulu.bilichan.Fragments.SettingsFragment;
import xyz.jilulu.bilichan.MainActivity;

/**
 * Created by jamesji on 23/4/2016.
 */
public class MainActivityFragmentAdapter extends FragmentPagerAdapter {

    public MainActivityFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case MainActivity.SEARCH_FRAGMENT:
                return new SearchFragment();
            case MainActivity.DISCOVER_FRAGMENT:
                return new DiscoverFragment();
            case MainActivity.FAVORITE_FRAGMENT:
                return new EmptyFavoriteFragment();
            case MainActivity.PREFERENCE_FRAGMENT:
                return new SettingsFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return MainActivity.NUMBER_OF_FRAGMENTS;
    }
}
