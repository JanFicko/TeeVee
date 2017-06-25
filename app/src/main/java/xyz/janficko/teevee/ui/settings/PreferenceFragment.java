package xyz.janficko.teevee.ui.settings;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v17.preference.LeanbackPreferenceFragment;
import android.support.v17.preference.LeanbackSettingsFragment;
import android.support.v7.preference.DialogPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceScreen;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Stack;

import xyz.janficko.teevee.R;

/**
 * Created by Jan on 2. 06. 2017.
 */

public class PreferenceFragment extends LeanbackSettingsFragment implements DialogPreference.TargetFragment {

    private final Stack<Fragment> fragments = new Stack<Fragment>();

    @Override
    public void onPreferenceStartInitialScreen() {
        startPreferenceFragment(buildPreferenceFragment(R.xml.reddit_preferences, null));
    }

    @Override
    public boolean onPreferenceStartFragment(android.support.v14.preference.PreferenceFragment preferenceFragment,
                                             Preference preference) {
        return false;
    }

    @Override
    public boolean onPreferenceStartScreen(android.support.v14.preference.PreferenceFragment preferenceFragment,
                                           PreferenceScreen preferenceScreen) {
        android.support.v14.preference.PreferenceFragment frag = buildPreferenceFragment(R.xml.reddit_preferences, preferenceScreen.getKey());
        startPreferenceFragment(frag);
        return true;
    }

    @Override
    public Preference findPreference(CharSequence prefKey) {
        return ((android.support.v14.preference.PreferenceFragment) fragments.peek()).findPreference(prefKey);
    }

    private android.support.v14.preference.PreferenceFragment buildPreferenceFragment(int preferenceResId, String root) {
        android.support.v14.preference.PreferenceFragment fragment = new PrefFragment();
        Bundle args = new Bundle();
        args.putInt("preferenceResource", preferenceResId);
        args.putString("root", root);
        fragment.setArguments(args);
        return fragment;
    }

    public class PrefFragment extends LeanbackPreferenceFragment {

        @Override
        public void onCreatePreferences(Bundle bundle, String s) {
            String root = getArguments().getString("root", null);
            int prefResId = getArguments().getInt("preferenceResource");
            if (root == null) {
                addPreferencesFromResource(prefResId);
            } else {
                setPreferencesFromResource(prefResId, root);
            }
        }

        @Override
        public boolean onPreferenceTreeClick(Preference preference) {
            final String[] keys = {"default_subreddit_preference"};
            if (Arrays.asList(keys).contains(preference.getKey())) {
                Toast.makeText(getActivity(), "Implement your own action handler.", Toast.LENGTH_SHORT).show();
                return true;
            }
            return super.onPreferenceTreeClick(preference);
        }

        @Override
        public void onAttach(Context context) {
            fragments.push(this);
            super.onAttach(context);
        }
    }
}
