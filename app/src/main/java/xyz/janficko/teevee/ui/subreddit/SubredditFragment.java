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
import android.util.Log;
import android.widget.Toast;

import net.dean.jraw.RedditClient;
import net.dean.jraw.auth.AuthenticationManager;
import net.dean.jraw.auth.AuthenticationState;
import net.dean.jraw.models.Submission;
import net.dean.jraw.paginators.SubredditPaginator;

import java.util.ArrayList;
import java.util.List;

import xyz.janficko.teevee.TeeVee;
import xyz.janficko.teevee.commons.Keys;
import xyz.janficko.teevee.models.Card;
import xyz.janficko.teevee.models.CardListRow;
import xyz.janficko.teevee.models.CardRow;
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
        for (Card card : cardRow.getCards()) {
            adapter.add(card);
        }

        HeaderItem headerItem = new HeaderItem(cardRow.getTitle());
        return new CardListRow(headerItem, adapter, cardRow);
    }

    @Override
    public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {

        Intent intent = new Intent(getActivity().getBaseContext(),
                DetailActivity.class);
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
                List<Card> cardList = new ArrayList<>();
                for (Submission link : subredditPaginator.next()) {
                    Card card = new Card();

                    card.setId(link.getId());
                    card.setTitle(link.getTitle());

                    String subreddit = (params[0].equalsIgnoreCase("FRONTPAGE") || params[0].equalsIgnoreCase("ALL")) ? "r/" + link.getSubredditName() + " | " : "";
                    String author = "u/" + link.getAuthor() + " | ";
                    String score = Utils.scoreWithSuffix(link.getScore());
                    String postInfo =
                            subreddit +
                            author +
                            score;
                    card.setPostInfo(postInfo);

                    if(link.isSelfPost() && link.getSelftext().length() != 0){
                        card.setType(Card.Type.TEXT);
                        card.setExtraText(link.getSelftext());
                    } else if(link.getThumbnail() != null){
                        card.setType(Card.Type.THUMBNAIL);
                        card.setThumbnail(link.getThumbnail());
                    } else {
                        card.setType(Card.Type.TITLE);
                    }

                    cardList.add(card);
                }

                cardRow.setCards(cardList);
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
