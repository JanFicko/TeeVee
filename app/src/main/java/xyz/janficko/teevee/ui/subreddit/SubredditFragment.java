package xyz.janficko.teevee.ui.subreddit;

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
import net.dean.jraw.paginators.SubredditPaginator;

import java.util.ArrayList;
import java.util.List;

import xyz.janficko.teevee.TeeVee;
import xyz.janficko.teevee.commons.Keys;
import xyz.janficko.teevee.models.Submission;
import xyz.janficko.teevee.models.CardListRow;
import xyz.janficko.teevee.models.CardRow;
import xyz.janficko.teevee.models.Type;
import xyz.janficko.teevee.presenters.CardPresenterSelector;
import xyz.janficko.teevee.presenters.ShadowRowPresenterSelector;
import xyz.janficko.teevee.ui.detail.DetailActivity;
import xyz.janficko.teevee.util.Utils;

/**
 * Created by Jan on 9. 06. 2017.
 */

public class SubredditFragment extends RowsFragment implements OnItemViewClickedListener {

    private static final String TAG = SubredditFragment.class.getSimpleName();
    private AuthenticationState mAuthenticateState = AuthenticationManager.get().checkAuthState();
    private RedditClient mRedditClient = TeeVee.getInstance().getRedditClient();
    private ArrayObjectAdapter mRowsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if(bundle != null){
            new LoadSubredditTask().execute(bundle.getString(Keys.BUNDLE_SUBREDDIT));
        }

        mRowsAdapter = new ArrayObjectAdapter(new ShadowRowPresenterSelector());
        setAdapter(mRowsAdapter);

        setOnItemViewClickedListener(this);
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
        for (Submission submission : cardRow.getSubmissionCards()) {
            adapter.add(submission);
        }

        HeaderItem headerItem = new HeaderItem(cardRow.getTitle());
        return new CardListRow(headerItem, adapter, cardRow);
    }

    @Override
    public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {

        Intent intent = new Intent(getActivity().getBaseContext(),
                DetailActivity.class);
        Submission submission = (Submission) item;
        intent.putExtra(Keys.INTENT_SUBMISSION_ID, submission.getId());
        startActivity(intent);
    }

    class LoadSubredditTask extends AsyncTask<String, Void, CardRow[]> {

        @Override
        protected CardRow[] doInBackground(String... params) {
            if(mAuthenticateState == AuthenticationState.READY) {
                SubredditPaginator subredditPaginator;
                if (params[0].equalsIgnoreCase("FRONTPAGE")) {
                    subredditPaginator = new SubredditPaginator(mRedditClient);
                } else {
                    subredditPaginator = new SubredditPaginator(mRedditClient, params[0]);
                }

                List<CardRow> cardRowList = new ArrayList<>();

                CardRow cardRow = new CardRow();
                cardRow.setTitle(params[0]);
                List<Submission> submissionList = new ArrayList<>();
                for (net.dean.jraw.models.Submission link : subredditPaginator.next()) {
                    Submission submission = new Submission();

                    submission.setId(link.getId());
                    submission.setTitle(link.getTitle());

                    String subreddit = (params[0].equalsIgnoreCase("FRONTPAGE") || params[0].equalsIgnoreCase("ALL")) ? "r/" + link.getSubredditName() + " | " : "";
                    String author = "u/" + link.getAuthor() + " | ";
                    String score = Utils.scoreWithSuffix(link.getScore());
                    String postInfo =
                            subreddit +
                            author +
                            score;
                    submission.setPostInfo(postInfo);

                    if(link.isSelfPost() && link.getSelftext().length() != 0){
                        submission.setType(Type.TEXT);
                        submission.setExtraText(link.getSelftext());
                    } else if(link.getThumbnail() != null){
                        submission.setType(Type.THUMBNAIL);
                        submission.setThumbnail(link.getThumbnail());
                    } else {
                        submission.setType(Type.TITLE);
                    }

                    submissionList.add(submission);
                }

                cardRow.setSubmissionCards(submissionList);
                cardRowList.add(cardRow);

                return cardRowList.toArray(new CardRow[cardRowList.size()]);
            }

            return null;
        }

        @Override
        protected void onPostExecute(CardRow[] rows) {
            super.onPostExecute(rows);

            if(rows != null){
                createRows(rows);
            }

            getMainFragmentAdapter().getFragmentHost().notifyDataReady(getMainFragmentAdapter());
        }
    }
}
