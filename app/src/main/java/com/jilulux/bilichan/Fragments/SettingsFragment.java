package com.jilulux.bilichan.Fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;

import com.jilulux.bilichan.MainActivity;
import com.jilulux.bilichan.R;

/**
 * Created by jamesji on 24/4/2016.
 */
public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener {
    private int tapCount = 0;

    PreferenceScreen openSource;
    PreferenceScreen githubFirer;
    SwitchPreference discoverCompactToggle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        openSource = (PreferenceScreen) findPreference("open_source_libraries");
        openSource.setOnPreferenceClickListener(this);

        githubFirer = (PreferenceScreen) findPreference("about");
        githubFirer.setOnPreferenceClickListener(this);

        discoverCompactToggle = (SwitchPreference) findPreference("compact_discovery");
        discoverCompactToggle.setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if (preference.equals(githubFirer)) {
            tapCount += 1;
            if (tapCount == 3) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse("https://github.com/jilulu/BiliChan"));
                startActivity(browserIntent);
            }
        } else if (preference.equals(openSource)) {
            new AlertDialog.Builder(getActivity()).setTitle("Open source libraries")
                    .setMessage("Gson: by Google\nButterKnife: by Jake Wharton\nOKHTTP3 & Picasso: by Square\nNTB: by DevLight\nPhotoView: by chrisbanes\nFlowLayout: by blazsolar")
                    .setPositiveButton("OK", null)
                    .show();
        }
        return true;
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference.equals(discoverCompactToggle)) {
            System.out.println(((MainActivity) getActivity()).fragmentPagerAdapter.retrieveItem(MainActivity.DISCOVER_FRAGMENT));
        }
        return true;
    }

    private String getFragmentTag(int viewPagerId, int fragmentPosition) {
        return "android:switcher:" + viewPagerId + ":" + fragmentPosition;
    }

}
