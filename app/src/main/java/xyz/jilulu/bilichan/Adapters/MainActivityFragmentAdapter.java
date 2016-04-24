package xyz.jilulu.bilichan.Adapters;


import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import xyz.jilulu.bilichan.Fragments.DiscoverFragment;
import xyz.jilulu.bilichan.Fragments.FavoriteFragment;
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
            case 0:
                return new SearchFragment();
            case 1:
                return new DiscoverFragment();
            case 2:
                return new FavoriteFragment();
            case 3:
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
