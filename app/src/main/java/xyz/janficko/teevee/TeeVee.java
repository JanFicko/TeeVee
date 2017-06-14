package xyz.janficko.teevee;

import android.app.Application;
import android.content.Context;

import net.dean.jraw.RedditClient;
import net.dean.jraw.auth.AuthenticationManager;
import net.dean.jraw.auth.RefreshTokenHandler;

import xyz.janficko.teevee.service.JrawWrapper.AndroidRedditClient;
import xyz.janficko.teevee.service.JrawWrapper.AndroidTokenStore;
import xyz.janficko.teevee.util.SharedPreferenceUtil;

/**
 * Created by Jan on 15. 05. 2017.
 */

public class TeeVee extends Application {

    private static TeeVee mInstance = null;
    private static SharedPreferenceUtil mSharedPreferenceUtil = null;
    private static RedditClient mRedditClient = null;

    public static TeeVee getInstance(){
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mSharedPreferenceUtil = new SharedPreferenceUtil(this);
        initializeRedditClient(this);
    }

    public SharedPreferenceUtil getSharedPreferenceUtil(){
        return mSharedPreferenceUtil;
    }

    private static void initializeRedditClient(Context context){
        mRedditClient = new AndroidRedditClient(context);
        RefreshTokenHandler handler = new RefreshTokenHandler(new AndroidTokenStore(context), mRedditClient);
        AuthenticationManager.get().init(mRedditClient, handler);
    }

    public RedditClient getRedditClient(){
        return mRedditClient;
    }
}
