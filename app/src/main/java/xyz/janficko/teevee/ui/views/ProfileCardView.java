package xyz.janficko.teevee.ui.views;

import android.content.Context;
import android.support.v17.leanback.widget.BaseCardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import xyz.janficko.teevee.R;
import xyz.janficko.teevee.models.Profile;
import xyz.janficko.teevee.models.Submission;
import xyz.janficko.teevee.models.Type;

public class ProfileCardView extends BaseCardView {

    private Context mContext;

    public ProfileCardView(Context context) {
        super(context, null, R.style.TextCardStyle);
        mContext = context;
        LayoutInflater.from(getContext()).inflate(R.layout.card_profile, this);
        setFocusable(false);
    }

    public void updateUi(Object object) {
        Profile profile = (Profile) object;

        TextView titleText = (TextView) findViewById(R.id.tv_title);
        TextView extraText = (TextView) findViewById(R.id.tv_extra_text);

        if(profile.getType() == Type.COMMENT_KARMA){
            titleText.setText(mContext.getString(R.string.comment_karma));
            extraText.setText(String.valueOf(profile.getCommentKarma()));
        } else if(profile.getType() == Type.LINK_KARMA){
            titleText.setText(mContext.getString(R.string.link_karma));
            extraText.setText(String.valueOf(profile.getPostKarma()));
        } else if(profile.getType() == Type.ACCOUNT_CREATED){
            titleText.setText(mContext.getString(R.string.created));
            extraText.setText(String.valueOf(profile.getDateCreated()));
        }

    }

}
