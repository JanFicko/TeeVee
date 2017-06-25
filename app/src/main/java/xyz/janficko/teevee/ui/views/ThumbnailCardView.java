package xyz.janficko.teevee.ui.views;

import android.content.Context;
import android.support.v17.leanback.widget.BaseCardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import xyz.janficko.teevee.R;
import xyz.janficko.teevee.models.Submission;

/**
 * Created by Jan on 17. 06. 2017.
 */

public class ThumbnailCardView extends BaseCardView implements View.OnFocusChangeListener {

    TextView mTvTitle;

    public ThumbnailCardView(Context context) {
        super(context, null, R.style.ThumbnailCardStyle);
        LayoutInflater.from(getContext()).inflate(R.layout.card_thumbnail, this);
        setFocusable(true);
    }

    public void updateUi(Object object) {
        Submission submission = (Submission) object;
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        ImageView thumbnail = (ImageView) findViewById(R.id.iv_thumbnail);
        TextView postInfo = (TextView) findViewById(R.id.tv_post_info);

        Glide.with(getContext())
                .load(submission.getThumbnail())
                .placeholder(R.drawable.ic_thumbnail)
                .centerCrop()
                .into(thumbnail);
        postInfo.setText(submission.getPostInfo());

        mTvTitle.setText(submission.getTitle());

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
