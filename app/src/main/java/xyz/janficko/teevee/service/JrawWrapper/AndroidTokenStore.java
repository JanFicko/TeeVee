package xyz.janficko.teevee.service.JrawWrapper;

import android.content.Context;
import android.content.SharedPreferences;

import net.dean.jraw.auth.NoSuchTokenException;
import net.dean.jraw.auth.TokenStore;

import xyz.janficko.teevee.R;
import xyz.janficko.teevee.TeeVee;
import xyz.janficko.teevee.util.SharedPreferenceUtil;

public class AndroidTokenStore implements TokenStore {

    private static final String TAG = AndroidTokenStore.class.getSimpleName();
    private SharedPreferenceUtil mSharedPreferenceUtil = TeeVee.getInstance().getSharedPreferenceUtil();
    private final Context mContext;

    public AndroidTokenStore(Context context) {
        mContext = context;
    }

    @Override
    public boolean isStored(String key) {
        return mSharedPreferenceUtil.containsKey(key);
    }

    @Override
    public String readToken(String key) throws NoSuchTokenException {
        String token = mSharedPreferenceUtil.retrieveRedditToken(key);
        if (token == null)
            throw new NoSuchTokenException("Token for key '" + key + "' does not exist");
        return token;
    }

    @Override
    public void writeToken(String key, String token) {
        mSharedPreferenceUtil.saveRedditToken(token);
    }
}
