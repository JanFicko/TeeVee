package xyz.janficko.teevee.util;

import android.content.Context;
import android.content.SharedPreferences;

import xyz.janficko.teevee.commons.Keys;

/**
 * Created by Jan on 15. 05. 2017.
 */

public class SharedPreferenceUtil {

    private static SharedPreferenceUtil ourInstance;

    private SharedPreferences mSharedPreferences;

    public SharedPreferenceUtil(Context context) {
        //mSharedPreferences = context.getSharedPreferences(null, Context.MODE_PRIVATE);
        mSharedPreferences = context.getSharedPreferences(Keys.PREFERENCES, Context.MODE_PRIVATE);
    }

    public boolean containsKey(String key) {
        return mSharedPreferences.contains(key);
    }

    private void saveString(String key, String value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    private String retrieveString(String key, String defaultValue) {
        return mSharedPreferences.getString(key, defaultValue);
    }

    private void saveBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    private boolean retrieveBoolean(String key, boolean defaultValue) {
        return mSharedPreferences.getBoolean(key, defaultValue);
    }

    public void saveFirstStart(boolean value){
        saveBoolean(Keys.PREF_FIRST_START, value);
    }

    public boolean isFirstStart(){
        return retrieveBoolean(Keys.PREF_FIRST_START, true);
    }

    public void saveRedditToken(String key, String token){
        saveString(key, token);
    }

    public void saveRedditToken(String token){
        saveString(Keys.PREF_REDDIT_TOKEN, token);
    }

    public String retrieveRedditToken(String key){
        return retrieveString(key, null);
    }

}
