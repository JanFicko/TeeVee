package xyz.janficko.teevee.ui.detail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import xyz.janficko.teevee.R;
import xyz.janficko.teevee.commons.Keys;

/**
 * Created by Jan on 17. 06. 2017.
 */

public class DetailActivity extends Activity {

    private String mSubmissionId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        setSubmissionId(intent.getStringExtra(Keys.INTENT_SUBMISSION_ID));
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public String getSubmissionId() {
        return mSubmissionId;
    }

    public void setSubmissionId(String postId) {
        this.mSubmissionId = postId;
    }
}
