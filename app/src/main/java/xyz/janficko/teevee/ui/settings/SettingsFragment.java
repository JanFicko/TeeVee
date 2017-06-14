package xyz.janficko.teevee.ui.settings;

import android.app.Activity;
import android.os.Handler;
import android.support.v17.leanback.app.RowsFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;

import net.dean.jraw.auth.AuthenticationManager;
import net.dean.jraw.auth.AuthenticationState;

import xyz.janficko.teevee.R;
import xyz.janficko.teevee.commons.Constants;
import xyz.janficko.teevee.models.Card;
import xyz.janficko.teevee.models.CardListRow;
import xyz.janficko.teevee.ui.home.MainFragment;

/**
 * Created by Jan on 22. 05. 2017.
 */

public class SettingsFragment extends RowsFragment {
    private AuthenticationState mAuthenticateState = AuthenticationManager.get().checkAuthState();
    private final ArrayObjectAdapter mRowsAdapter;

    public SettingsFragment(MainFragment.PageRowFragmentFactory factory) {
        ListRowPresenter selector = new ListRowPresenter();
        mRowsAdapter = new ArrayObjectAdapter(selector);
        setAdapter(mRowsAdapter);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadData();
            }
        }, 200);
    }

    private void loadData() {
        if (isAdded()) {
            mRowsAdapter.add(createCardRow());
            getMainFragmentAdapter().getFragmentHost().notifyDataReady(
                    getMainFragmentAdapter());
        }
    }

    private ListRow createCardRow() {
        SettingsIconPresenter iconCardPresenter = new SettingsIconPresenter(getActivity());
        ArrayObjectAdapter adapter = new ArrayObjectAdapter(iconCardPresenter);
        HeaderItem headerItem = new HeaderItem(getString(R.string.settings));
        for(int i=0; i< Constants.SETTINGS_CATEGORY.length; i++) {
            Card card = new Card();
            if(mAuthenticateState == AuthenticationState.READY
                    && (i == 1
                    || i == 2
                    || i == 3)){
                card.setTitle(Constants.SETTINGS_CATEGORY[i]);
                card.setType(Card.Type.ICON);
                card.setLocalImageResource(Constants.SETTINGS_ICON[i]);
                adapter.add(card);
            } else if((mAuthenticateState == AuthenticationState.NONE || mAuthenticateState == AuthenticationState.NEED_REFRESH)
                    && (i == 0
                    || i == 3)){
                card.setTitle(Constants.SETTINGS_CATEGORY[i]);
                card.setType(Card.Type.ICON);
                card.setLocalImageResource(Constants.SETTINGS_ICON[i]);
                adapter.add(card);
            }
        }

        return new CardListRow(headerItem, adapter, null);
    }
}
