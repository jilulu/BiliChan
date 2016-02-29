package xyz.jilulu.jamesji.bilifun.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;

import xyz.jilulu.jamesji.bilifun.R;

/**
 * Created by jamesji on 25/2/2016.
 */
public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Display the fragment as the main content.
        getFragmentManager().beginTransaction().replace(android.R.id.content, new PrefsFragment()).commit();
    }

    public static class PrefsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.preferences);
            getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
            Preference pref = findPreference("use_new_res_set");
            pref.setSummary(pref.getPreferenceManager().getSharedPreferences().getBoolean("use_new_res_set",false) ? "Higher quality" : "Lower quality");
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (key.equals("use_new_res_set")) {
                Preference preference = findPreference(key);
                preference.setSummary(sharedPreferences.getBoolean(key, false) ? "Higher quality" : "Lower quality");
            }
        }
    }

}
