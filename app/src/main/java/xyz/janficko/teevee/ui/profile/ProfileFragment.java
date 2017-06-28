package xyz.janficko.teevee.ui.profile;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v17.leanback.app.RowsFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.PresenterSelector;
import android.support.v17.leanback.widget.Row;

import net.dean.jraw.RedditClient;
import net.dean.jraw.auth.AuthenticationManager;
import net.dean.jraw.auth.AuthenticationState;
import net.dean.jraw.models.LoggedInAccount;

import java.util.ArrayList;
import java.util.List;

import xyz.janficko.teevee.R;
import xyz.janficko.teevee.TeeVee;
import xyz.janficko.teevee.models.Comment;
import xyz.janficko.teevee.models.Profile;
import xyz.janficko.teevee.models.CardListRow;
import xyz.janficko.teevee.models.CardRow;
import xyz.janficko.teevee.models.Type;
import xyz.janficko.teevee.presenters.CardPresenterSelector;
import xyz.janficko.teevee.presenters.ShadowRowPresenterSelector;
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
                mRowsAdapter.add(createProfileCardRow(row));
            }
        }
    }

    private Row createProfileCardRow(CardRow cardRow) {
        PresenterSelector presenterSelector = new CardPresenterSelector(getActivity());
        ArrayObjectAdapter adapter = new ArrayObjectAdapter(presenterSelector);
        for (Profile profile : cardRow.getProfileCards()) {
            adapter.add(profile);
        }

        HeaderItem headerItem = new HeaderItem(cardRow.getTitle());
        return new CardListRow(headerItem, adapter, cardRow);
    }

    class LoadProfileTask extends AsyncTask<Void, Void, CardRow[]> {

        @Override
        protected CardRow[] doInBackground(Void... params) {
            if(mAuthenticateState == AuthenticationState.READY) {
                LoggedInAccount loggedInAccount = mRedditClient.me();

                List<CardRow> cardRowList = new ArrayList<>();

                CardRow infoRow = new CardRow();
                infoRow.setTitle("u/" + loggedInAccount.getFullName());
                List<Profile> profileList = new ArrayList<>();
                for(int i=0; i<3; i++){
                    Profile profile = new Profile();

                    if(i == 0){
                        profile.setType(Type.COMMENT_KARMA);
                        profile.setCommentKarma(loggedInAccount.getCommentKarma());
                    } else if(i == 1){
                        profile.setType(Type.LINK_KARMA);
                        profile.setPostKarma(loggedInAccount.getLinkKarma());
                    }else if(i == 2){
                        profile.setType(Type.ACCOUNT_CREATED);
                        profile.setDateCreated(Utils.getDateFromMilliseconds(loggedInAccount.getCreated().getTime()));
                    }

                    profileList.add(profile);
                }
                infoRow.setProfileCards(profileList);
                cardRowList.add(infoRow);

                CardRow commentRow = new CardRow();
                commentRow.setTitle(getString(R.string.comments));

                List<Profile> profileComments = new ArrayList<>();
                for(int i=0; i<2; i++){
                    List<Comment> commentList = new ArrayList<>();
                    Profile commentProfile = new Profile();
                    commentProfile.setType(Type.COMMENT);
                    Comment comment = new Comment();

                    if(i == 0){
                        comment.setSubmissionTitle("Google Music 4921 is out to solve crashing.");
                        comment.setComment("I found out how to fix it for me. Disable Bluetooth, open Google Music, re-enable bluetooth.");
                    } else if(i == 1){
                        comment.setSubmissionTitle("Samsung’s UI fluidity problem needs fixing");
                        comment.setComment("The default samsung launcher app drawer lags horribly for the S8. I'm not a samsung fan boy but try using nova - you'll find that the app drawer is considerably smoother");
                    }
                    commentList.add(comment);
                    commentProfile.setCommentList(commentList);
                    profileComments.add(commentProfile);
                }
                commentRow.setProfileCards(profileComments);
                cardRowList.add(commentRow);
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