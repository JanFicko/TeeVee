package xyz.janficko.teevee.ui.profile;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v17.leanback.app.RowsFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.PresenterSelector;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;

import net.dean.jraw.RedditClient;
import net.dean.jraw.auth.AuthenticationManager;
import net.dean.jraw.auth.AuthenticationState;
import net.dean.jraw.models.LoggedInAccount;
import net.dean.jraw.paginators.SubredditPaginator;

import java.util.ArrayList;
import java.util.List;

import xyz.janficko.teevee.TeeVee;
import xyz.janficko.teevee.commons.Keys;
import xyz.janficko.teevee.models.Submission;
import xyz.janficko.teevee.models.CardListRow;
import xyz.janficko.teevee.models.CardRow;
import xyz.janficko.teevee.presenters.CardPresenterSelector;
import xyz.janficko.teevee.presenters.ShadowRowPresenterSelector;
import xyz.janficko.teevee.ui.detail.DetailActivity;
import xyz.janficko.teevee.ui.subreddit.SubredditFragment;
import xyz.janficko.teevee.util.Utils;

public class ProfileFragment extends RowsFragment {

    private static final String TAG = SubredditFragment.class.getSimpleName();
    private AuthenticationState mAuthenticateState = AuthenticationManager.get().checkAuthState();
    private RedditClient mRedditClient = TeeVee.getInstance().getRedditClient();
    private ArrayObjectAdapter mRowsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new LoadProfileTask().execute();

        mRowsAdapter = new ArrayObjectAdapter(new ShadowRowPresenterSelector());
        setAdapter(mRowsAdapter);
    }

    private void createRows(CardRow[] rows) {
        for (CardRow row : rows) {
            if (row.getType() == CardRow.TYPE_DEFAULT) {
                mRowsAdapter.add(createCardRow(row));
            }
        }
    }

    private Row createCardRow(CardRow cardRow) {
        PresenterSelector presenterSelector = new CardPresenterSelector(getActivity());
        ArrayObjectAdapter adapter = new ArrayObjectAdapter(presenterSelector);
        for (Submission submission : cardRow.getCards()) {
            adapter.add(submission);
        }

        HeaderItem headerItem = new HeaderItem(cardRow.getTitle());
        return new CardListRow(headerItem, adapter, cardRow);
    }

    class LoadProfileTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            if(mAuthenticateState == AuthenticationState.READY) {
                LoggedInAccount loggedInAccount = mRedditClient.me();

                List<CardRow> cardRowList = new ArrayList<>();
                CardRow cardRow = new CardRow();

                cardRow.setTitle(loggedInAccount.getFullName());
                List<Submission> submissionList = new ArrayList<>();


                submissionList.add(null);

                cardRow.setCards(submissionList);
                cardRowList.add(cardRow);

                return null;
            }

            return null;
        }
    }
}