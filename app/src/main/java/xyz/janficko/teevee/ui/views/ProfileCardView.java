package xyz.janficko.teevee.ui.views;

import android.content.Context;
import android.support.v17.leanback.widget.BaseCardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import xyz.janficko.teevee.R;
import xyz.janficko.teevee.models.Profile;
import xyz.janficko.teevee.models.Submission;

public class ProfileCardView extends BaseCardView {

    public ProfileCardView(Context context) {
        super(context, null, R.style.TextCardStyle);
        LayoutInflater.from(getContext()).inflate(R.layout.card_profile, this);
        setFocusable(true);
    }

    public void updateUi(Object profile) {

    }

}
