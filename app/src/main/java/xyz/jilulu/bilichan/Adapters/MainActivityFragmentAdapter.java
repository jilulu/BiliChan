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

    private SearchFragment searchFragment;
    private DiscoverFragment discoverFragment;
    private EmptyFavoriteFragment emptyFavoriteFragment;
    private SettingsFragment settingsFragment;

    public MainActivityFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public Fragment retrieveItem(int position) {
        switch (position) {
            case MainActivity.SEARCH_FRAGMENT:
                return searchFragment;
            case MainActivity.DISCOVER_FRAGMENT:
                return discoverFragment;
            case MainActivity.FAVORITE_FRAGMENT:
                return emptyFavoriteFragment;
            case MainActivity.PREFERENCE_FRAGMENT:
                return settingsFragment;
            default:
                return null;
        }
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case MainActivity.SEARCH_FRAGMENT:
                searchFragment = new SearchFragment();
                return searchFragment;
            case MainActivity.DISCOVER_FRAGMENT:
                discoverFragment = new DiscoverFragment();
                return discoverFragment;
            case MainActivity.FAVORITE_FRAGMENT:
                emptyFavoriteFragment = new EmptyFavoriteFragment();
                return emptyFavoriteFragment;
            case MainActivity.PREFERENCE_FRAGMENT:
                settingsFragment = new SettingsFragment();
                return settingsFragment;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return MainActivity.NUMBER_OF_FRAGMENTS;
    }

//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
//        Fragment createdFragment = (Fragment) super.instantiateItem(container, position);
//
//    }
}
