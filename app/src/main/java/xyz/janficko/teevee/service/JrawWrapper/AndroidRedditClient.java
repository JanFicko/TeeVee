package xyz.janficko.teevee.service.JrawWrapper;


import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import net.dean.jraw.RedditClient;
import net.dean.jraw.http.HttpRequest;
import net.dean.jraw.http.NetworkException;
import net.dean.jraw.http.RestResponse;
import net.dean.jraw.http.UserAgent;
import net.dean.jraw.http.oauth.InvalidScopeException;

import xyz.janficko.teevee.BuildConfig;
import xyz.janficko.teevee.commons.Constants;

public class AndroidRedditClient extends RedditClient {

    private static final String TAG = AndroidRedditClient.class.getSimpleName();

    private static UserAgent getUserAgent(Context context) {
        try {
            // Get the app's <meta-data> tags from the manifest
            Bundle bundle = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA)
                    .metaData;

            // If there are no <meta-data> tags the Bundle will be null
            if (bundle == null)
                throw new IllegalStateException("Please specify a <meta-data> for either " + Constants.KEY_REDDIT_USERNAME +
                        " or " + Constants.KEY_USER_AGENT_OVERRIDE);

            String userAgent = bundle.getString(Constants.KEY_USER_AGENT_OVERRIDE, null);
            if (userAgent != null)
                return UserAgent.of(userAgent);

            String username = bundle.getString(Constants.KEY_REDDIT_USERNAME, null);
            if (username == null)
                throw new IllegalStateException("No <meta-data> for " + Constants.KEY_REDDIT_USERNAME);
            return UserAgent.of(Constants.PLATFORM, context.getPackageName(), BuildConfig.VERSION_NAME, username);
        } catch (PackageManager.NameNotFoundException e) {
            throw new IllegalStateException("Could not find package metadata for own package", e);
        }
    }

    public AndroidRedditClient(Context context) {
        this(getUserAgent(context));
    }

    /**
     * Traditional RedditClient constructor
     */
    public AndroidRedditClient(UserAgent userAgent) {
        super(userAgent);
    }

    @Override
    public RestResponse execute(HttpRequest request) throws NetworkException, InvalidScopeException {
        if (getUserAgent().trim().isEmpty()) {
            throw new IllegalStateException("No UserAgent specified");
        }
        return super.execute(request);
    }
}