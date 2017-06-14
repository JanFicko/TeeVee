package xyz.janficko.teevee.ui.search;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;

/**
 * Created by Jan on 18. 05. 2017.
 */

public abstract class LeanbackActivity extends FragmentActivity {
    @Override
    public boolean onSearchRequested() {
        startActivity(new Intent(this, SearchActivity.class));
        return true;
    }
}
