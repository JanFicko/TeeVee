package xyz.janficko.teevee.ui.views;

import android.content.Context;
import android.support.v17.leanback.widget.BaseCardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import xyz.janficko.teevee.R;
import xyz.janficko.teevee.models.Comment;
import xyz.janficko.teevee.models.Profile;
import xyz.janficko.teevee.models.Submission;

public class CommentsCardView extends BaseCardView implements View.OnFocusChangeListener {

    TextView mTvTitle;

    public CommentsCardView(Context context) {
        super(context, null, R.style.TextCardStyle);
        LayoutInflater.from(getContext()).inflate(R.layout.card_comment, this);
        setFocusable(true);
    }

    public void updateUi(Object object) {
        Profile profile = (Profile) object;
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        TextView tvComment = (TextView) findViewById(R.id.tv_comment);

        mTvTitle.setText(profile.getCommentList().get(0).getSubmissionTitle());
        tvComment.setText(profile.getCommentList().get(0).getComment());

        setOnFocusChangeListener(this);
    }

    @Override
    public void onFocusChange(View v, final boolean hasFocus) {
        mTvTitle.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(hasFocus){
                    mTvTitle.setSelected(true);
                } else {
                    mTvTitle.setSelected(false);
                }
            }
        }, 1000);
    }
}
