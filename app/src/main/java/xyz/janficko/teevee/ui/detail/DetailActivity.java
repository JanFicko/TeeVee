package xyz.janficko.teevee.ui.detail;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import xyz.janficko.teevee.R;

/**
 * Created by Jan on 17. 06. 2017.
 */

public class DetailActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    }
}
