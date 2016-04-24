package xyz.jilulu.bilichan.Fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;

import xyz.jilulu.bilichan.R;

/**
 * Created by jamesji on 24/4/2016.
 */
public class SettingsFragment extends PreferenceFragment {
    private int tapCount = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        PreferenceScreen openSource = (PreferenceScreen) findPreference("open_source_libraries");
        openSource.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                new AlertDialog.Builder(getActivity()).setTitle("Open source libraries")
                        .setMessage("Gson: by Google\nButterKnife: by Jake Wharton\nOKHTTP3 & Picasso: by Square\nNTB: by DevLight\nPhotoView: by chrisbanes\nFlowLayout: by blazsolar")
                        .setPositiveButton("OK", null)
                        .show();
                return false;
            }
        });

        PreferenceScreen githubFirer = (PreferenceScreen) findPreference("about");
        githubFirer.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                tapCount += 1;
                if (tapCount == 3) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                    browserIntent.setData(Uri.parse("https://github.com/jilulu/BiliChan"));
                    startActivity(browserIntent);
                }
                return false;
            }
        });
    }
}
