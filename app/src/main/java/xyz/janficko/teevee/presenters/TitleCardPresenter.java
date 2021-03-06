package xyz.janficko.teevee.presenters;

import android.content.Context;

import xyz.janficko.teevee.models.Submission;
import xyz.janficko.teevee.ui.views.TitleCardView;

/**
 * Created by Jan on 17. 06. 2017.
 */

public class TitleCardPresenter extends AbstractCardPresenter<TitleCardView> {

    public TitleCardPresenter(Context context) {
        super(context);
    }

    @Override
    protected TitleCardView onCreateView() {
        return new TitleCardView(getContext());
    }


    @Override
    public void onBindViewHolder(Object submission, TitleCardView cardView) {
        cardView.updateUi(submission);
    }

}
