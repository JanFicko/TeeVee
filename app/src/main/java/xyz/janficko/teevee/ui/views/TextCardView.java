package xyz.janficko.teevee.ui.views;

import android.content.Context;
import android.support.v17.leanback.widget.BaseCardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import xyz.janficko.teevee.R;
import xyz.janficko.teevee.models.Submission;

public class TextCardView extends BaseCardView implements View.OnFocusChangeListener {

    TextView mTvTitle;

    public TextCardView(Context context) {
        super(context, null, R.style.TextCardStyle);
        LayoutInflater.from(getContext()).inflate(R.layout.card_text, this);
        setFocusable(true);
    }

    public void updateUi(Object object) {
        Submission submission = (Submission) object;
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        TextView extraText = (TextView) findViewById(R.id.tv_extra_text);
        TextView postInfo = (TextView) findViewById(R.id.tv_post_info);

        mTvTitle.setText(submission.getTitle());
        extraText.setText(submission.getExtraText());
        postInfo.setText(submission.getPostInfo());

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
