package xyz.janficko.teevee.presenters;

import android.content.Context;

import xyz.janficko.teevee.models.Profile;
import xyz.janficko.teevee.models.Submission;
import xyz.janficko.teevee.ui.views.ProfileCardView;
import xyz.janficko.teevee.ui.views.TextCardView;

/**
 * Created by Jan on 25. 06. 2017.
 */

public class ProfileCardPresenter extends AbstractCardPresenter<ProfileCardView> {

    public ProfileCardPresenter(Context context) {
        super(context);
    }

    @Override
    protected ProfileCardView onCreateView() {
        return new ProfileCardView(getContext());
    }


    @Override
    public void onBindViewHolder(Object profile, final ProfileCardView cardView) {
        cardView.updateUi(profile);
    }

}
