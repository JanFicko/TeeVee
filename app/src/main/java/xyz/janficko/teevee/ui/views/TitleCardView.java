package xyz.janficko.teevee.ui.views;

import android.content.Context;
import android.support.v17.leanback.widget.BaseCardView;
import android.view.LayoutInflater;
import android.widget.TextView;

import xyz.janficko.teevee.R;
import xyz.janficko.teevee.models.Submission;
import xyz.janficko.teevee.util.Utils;

/**
 * Created by Jan on 17. 06. 2017.
 */

public class TitleCardView  extends BaseCardView {

    private TextView mTvTitle;

    public TitleCardView(Context context) {
        super(context, null, R.style.TextCardStyle);
        LayoutInflater.from(getContext()).inflate(R.layout.card_title, this);
        setFocusable(true);
    }

    public void updateUi(Object object) {
        Submission submission = (Submission) object;
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        TextView postInfo = (TextView) findViewById(R.id.tv_post_info);

        mTvTitle.setText(submission.getTitle());
        int numberOfLines = Utils.countLine(submission.getTitle().length());
        mTvTitle.setLines(numberOfLines);

        postInfo.setText(submission.getPostInfo());
    }

}
