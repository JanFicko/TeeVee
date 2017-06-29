package xyz.janficko.teevee.ui.detail;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v17.leanback.app.DetailsFragment;
import android.support.v17.leanback.app.DetailsFragmentBackgroundController;
import android.support.v17.leanback.media.MediaPlayerGlue;
import android.support.v17.leanback.widget.Action;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.ClassPresenterSelector;
import android.support.v17.leanback.widget.DetailsOverviewRow;
import android.support.v17.leanback.widget.FullWidthDetailsOverviewRowPresenter;
import android.support.v17.leanback.widget.FullWidthDetailsOverviewSharedElementHelper;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.OnItemViewSelectedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;

import net.dean.jraw.RedditClient;
import net.dean.jraw.auth.AuthenticationManager;
import net.dean.jraw.auth.AuthenticationState;

import java.util.concurrent.ExecutionException;

import xyz.janficko.teevee.R;
import xyz.janficko.teevee.TeeVee;
import xyz.janficko.teevee.models.Submission;
import xyz.janficko.teevee.models.CardListRow;
import xyz.janficko.teevee.models.DetailedCard;
import xyz.janficko.teevee.models.Type;
import xyz.janficko.teevee.presenters.CardPresenterSelector;
import xyz.janficko.teevee.presenters.DetailsDescriptionPresenter;
import xyz.janficko.teevee.util.Utils;

/**
 * Created by Jan on 17. 06. 2017.
 */

public class DetailFragment extends DetailsFragment implements OnItemViewClickedListener,
        OnItemViewSelectedListener {

    private DetailActivity mDetailActivity;

    private net.dean.jraw.models.Submission mSubmission;

    public static final String TRANSITION_NAME = "t_for_transition";
    public static final String EXTRA_CARD = "card";

    private static final long ACTION_OPEN_LINK = 1;
    private static final long ACTION_UPVOTE = 2;
    private static final long ACTION_DOWNVOTE = 3;

    private Action mActionOpenLink;
    private Action mActionUpvote;
    private Action mActionDownvote;
    private ArrayObjectAdapter mRowsAdapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mDetailActivity = (DetailActivity) getActivity();
    }

    @Override
    public void onResume() {
        super.onResume();

        setupUi();
        setupEventListeners();

    }

    private void setupUi() {
        try {
            mSubmission = new LoadSubmissionTask().execute(mDetailActivity.getSubmissionId()).get();
        } catch (InterruptedException |ExecutionException e) {
            e.printStackTrace();
        }

        DetailedCard data = new DetailedCard();
        data.setTitle(mSubmission.getTitle());
        data.setUser("u/" + mSubmission.getAuthor());
        data.setScore(Utils.scoreWithSuffix(mSubmission.getScore()));
        data.setCommentCount(Utils.scoreWithSuffix(mSubmission.getCommentCount()));
        data.setTime(Utils.getDateFromMilliseconds(mSubmission.getCreated().getTime()));

        FullWidthDetailsOverviewRowPresenter rowPresenter = new FullWidthDetailsOverviewRowPresenter(
                new DetailsDescriptionPresenter(getActivity())) {

            @Override
            protected RowPresenter.ViewHolder createRowViewHolder(ViewGroup parent) {
                // Customize Actionbar and Content by using custom colors.
                RowPresenter.ViewHolder viewHolder = super.createRowViewHolder(parent);

                View actionsView = viewHolder.view.
                        findViewById(R.id.details_overview_actions_background);
                actionsView.setBackgroundColor(getActivity().getResources().
                        getColor(R.color.detail_view_actionbar_background));

                View detailsView = viewHolder.view.findViewById(R.id.details_frame);
                detailsView.setBackgroundColor(
                        getResources().getColor(R.color.detail_view_background));
                return viewHolder;
            }
        };

        FullWidthDetailsOverviewSharedElementHelper mHelper = new FullWidthDetailsOverviewSharedElementHelper();
        mHelper.setSharedElementEnterTransition(getActivity(), TRANSITION_NAME);
        rowPresenter.setListener(mHelper);
        rowPresenter.setParticipatingEntranceTransition(false);
        prepareEntranceTransition();

        ListRowPresenter shadowDisabledRowPresenter = new ListRowPresenter();
        shadowDisabledRowPresenter.setShadowEnabled(false);

        // Setup PresenterSelector to distinguish between the different rows.
        ClassPresenterSelector rowPresenterSelector = new ClassPresenterSelector();
        rowPresenterSelector.addClassPresenter(DetailsOverviewRow.class, rowPresenter);
        rowPresenterSelector.addClassPresenter(CardListRow.class, shadowDisabledRowPresenter);
        rowPresenterSelector.addClassPresenter(ListRow.class, new ListRowPresenter());
        mRowsAdapter = new ArrayObjectAdapter(rowPresenterSelector);

        // Setup action and detail row.
        DetailsOverviewRow detailsOverview = new DetailsOverviewRow(data);
        //int imageResId = data.getLocalImageResourceId(getActivity());

        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null && extras.containsKey(EXTRA_CARD)) {
           // imageResId = extras.getInt(EXTRA_CARD, imageResId);
        }
        //detailsOverview.setImageDrawable(getResources().getDrawable(imageResId, null));
        ArrayObjectAdapter actionAdapter = new ArrayObjectAdapter();

        mActionOpenLink = new Action(ACTION_OPEN_LINK, getString(R.string.open_link));
        mActionUpvote = new Action(ACTION_UPVOTE, getString(R.string.upvote));
        mActionDownvote = new Action(ACTION_DOWNVOTE, getString(R.string.downvote));

        actionAdapter.add(mActionOpenLink);
        actionAdapter.add(mActionUpvote);
        actionAdapter.add(mActionDownvote);
        detailsOverview.setActionsAdapter(actionAdapter);
        mRowsAdapter.add(detailsOverview);

        // Setup related row.
        /*ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(
                new CardPresenterSelector(getActivity()));
        for (Submission characterSubmission : data.getCharacters()) listRowAdapter.add(characterSubmission);
        HeaderItem header = new HeaderItem(0, getString(R.string.header_related));
        mRowsAdapter.add(new CardListRow(header, listRowAdapter, null));*/

        // Setup recommended row.
        /*listRowAdapter = new ArrayObjectAdapter(new CardPresenterSelector(getActivity()));
        for (Submission card : data.getRecommended()) listRowAdapter.add(card);
        header = new HeaderItem(1, getString(R.string.header_recommended));
        mRowsAdapter.add(new ListRow(header, listRowAdapter));*/

        setAdapter(mRowsAdapter);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startEntranceTransition();
            }
        }, 500);
        //initializeBackground(data);
    }

    private void setupEventListeners() {
        setOnItemViewSelectedListener(this);
        setOnItemViewClickedListener(this);
    }

    @Override
    public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,
                              RowPresenter.ViewHolder rowViewHolder, Row row) {
        if (!(item instanceof Action)) return;
        Action action = (Action) item;

        Toast.makeText(getActivity(), getString(R.string.action_cicked), Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item,
                               RowPresenter.ViewHolder rowViewHolder, Row row) {
        if (mRowsAdapter.indexOf(row) > 0) {
            int backgroundColor = getResources().getColor(R.color.detail_view_related_background);
            getView().setBackgroundColor(backgroundColor);
        } else {
            getView().setBackground(null);
        }
    }

    class LoadSubmissionTask extends AsyncTask<String, Void, net.dean.jraw.models.Submission> {

        private AuthenticationState mAuthenticateState = AuthenticationManager.get().checkAuthState();
        private RedditClient mRedditClient = TeeVee.getInstance().getRedditClient();

        @Override
        protected net.dean.jraw.models.Submission doInBackground(String... params) {

            net.dean.jraw.models.Submission submission = mRedditClient.getSubmission(params[0]);

            return submission;
        }
    }

}
