package xyz.janficko.teevee.commons;

import net.dean.jraw.http.oauth.Credentials;

import xyz.janficko.teevee.BuildConfig;
import xyz.janficko.teevee.R;

/**
 * Created by Jan on 15. 05. 2017.
 */

public class Constants {


    public static final Credentials CREDENTIALS = Credentials.installedApp(BuildConfig.CLIENT_ID, BuildConfig.REDIRECT_URI);
    public static final String KEY_USER_AGENT_OVERRIDE = "net.dean.jraw.USER_AGENT_OVERRIDE";
    public static final String KEY_REDDIT_USERNAME = "net.dean.jraw.REDDIT_USERNAME";
    public static final String PLATFORM = "android";
    public static final String[] REDDIT_SCOPES = {"identity", "edit", "flair", "history",
            "modconfig", "modflair", "modlog", "modposts", "modwiki", "mysubreddits",
            "privatemessages", "read", "report", "save", "submit", "subscribe", "vote", "wikiedit",
            "wikiread"};


    public static final long ONBOARDING_ANIMATION_DURATION = 500;
    public static final int[] ONBOARDING_PAGE_TITLE = {
            R.string.onboarding_title_welcome,
            R.string.onboarding_title_frontpage,
            R.string.onboarding_title_submark,
            R.string.onboarding_title_others
    };
    public static final int[] ONBOARDING_PAGE_DESCRIPTION = {
            R.string.onboarding_description_welcome,
            R.string.onboarding_description_frontpage,
            R.string.onboarding_description_submark,
            R.string.onboarding_description_others
    };
    public static final int[] ONBOARDING_PAGE_IMAGES = {
            R.drawable.lb_ic_search_mic,
            R.drawable.lb_ic_search_mic,
            R.drawable.lb_ic_search_mic,
            R.drawable.lb_ic_search_mic
    };

    public static final int GRID_ITEM_WIDTH = 200;
    public static final int GRID_ITEM_HEIGHT = 200;

    public static final int HEADER_ID[] = {
            0,
            1,
            2,
            3
    };
    public static final String HEADER_CATEGORY[] = {
            "Frontpage",
            "Submark",
            "Profile",
            "Settings"
    };

    public static final String SETTINGS_CATEGORY[] = {
            "Login",
            "Logout",
            "Reddit preferences",
            "About"
    };
    public static final String SETTINGS_ICON[] = {
            "ic_login",
            "ic_logout",
            "ic_info",
            "ic_info"
    };

    public static final String SUBSCRIBED_SUBREDDIT[] = {
            "ANDROID",
            "ASKREDDIT",
            "VIDEOS",
            "ANDROIDDEV",
            "EUROPE",
            "GAMING",
            "MULTICOPTER",
            "ROADCAM",
            "SOCCER",
            "TECHNOLOGY",
            "TODAYILEARNED",
    };


}
