/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package xyz.janficko.teevee.ui.home;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.DividerRow;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.PageRow;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v17.leanback.widget.SectionRow;
import android.support.v4.content.ContextCompat;
import android.view.View;

import net.dean.jraw.RedditClient;
import net.dean.jraw.auth.AuthenticationManager;
import net.dean.jraw.auth.AuthenticationState;

import xyz.janficko.teevee.TeeVee;
import xyz.janficko.teevee.commons.Constants;
import xyz.janficko.teevee.R;
import xyz.janficko.teevee.commons.Keys;
import xyz.janficko.teevee.models.Submission;
import xyz.janficko.teevee.presenters.ShadowRowPresenterSelector;
import xyz.janficko.teevee.ui.profile.ProfileFragment;
import xyz.janficko.teevee.ui.search.SearchActivity;
import xyz.janficko.teevee.ui.settings.SettingsFragment;
import xyz.janficko.teevee.ui.subreddit.SubredditFragment;
import xyz.janficko.teevee.util.SharedPreferenceUtil;

public class MainFragment extends BrowseFragment {

	private static final String TAG = MainFragment.class.getSimpleName();
	private SharedPreferenceUtil mSharedPreferenceUtil = TeeVee.getInstance().getSharedPreferenceUtil();
	private AuthenticationState mAuthenticateState = AuthenticationManager.get().checkAuthState();
	private RedditClient mRedditClient = TeeVee.getInstance().getRedditClient();
	private BackgroundManager mBackgroundManager;
	private ArrayObjectAdapter mRowsAdapter;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		setupUi();
		setupRowAdapter();
		setupEventListeners();

		mBackgroundManager = BackgroundManager.getInstance(getActivity());
		mBackgroundManager.attach(getActivity().getWindow());
		getMainFragmentRegistry().registerFragment(PageRow.class,
				new PageRowFragmentFactory(mBackgroundManager));
	}

	private void setupUi() {
		//setBadgeDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.videos_by_google_banner));
		setTitle(getString(R.string.browse_title));
		setHeadersState(HEADERS_ENABLED);
		setHeadersTransitionOnBackEnabled(true);
		setBrandColor(ContextCompat.getColor(getActivity(), R.color.header_background));
		setSearchAffordanceColor(ContextCompat.getColor(getActivity(), R.color.search_opaque));
		prepareEntranceTransition();
	}


	private void setupRowAdapter() {
		mRowsAdapter = new ArrayObjectAdapter(new ShadowRowPresenterSelector());
		setAdapter(mRowsAdapter);
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				createRows();
				startEntranceTransition();
			}
		}, 500);
	}

	private void createRows() {
		PageRow pageRow;
		for(int i=0; i< Constants.HEADER_ID.length; i++){
			if(!Constants.HEADER_CATEGORY[i].equals(getString(R.string.profile))
					|| (Constants.HEADER_CATEGORY[i].equals(getString(R.string.profile))
					&& mAuthenticateState == AuthenticationState.READY)){
				HeaderItem headerItem = new HeaderItem(Constants.HEADER_ID[i], Constants.HEADER_CATEGORY[i]);
				pageRow = new PageRow(headerItem);
				mRowsAdapter.add(pageRow);
			}
		}

		if(mAuthenticateState == AuthenticationState.READY){
			mRowsAdapter.add(new DividerRow());
			mRowsAdapter.add(new SectionRow(new HeaderItem(getString(R.string.my_subreddits))));

			for(int i=0; i<Constants.SUBSCRIBED_SUBREDDIT.length; i++){
				HeaderItem headerItem = new HeaderItem(0, Constants.SUBSCRIBED_SUBREDDIT[i]);
				pageRow = new PageRow(headerItem);
				mRowsAdapter.add(pageRow);
			}
		}
	}

	private void setupEventListeners() {
		setOnSearchClickedListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getActivity(), SearchActivity.class);
				startActivity(intent);
			}
		});

		setOnItemViewClickedListener(new OnItemViewClickedListener() {
			@Override
			public void onItemClicked(Presenter.ViewHolder viewHolder, Object item, RowPresenter.ViewHolder viewHolder1, Row row) {
				if (!(item instanceof Submission)) return;
				if (!(viewHolder.view instanceof ImageCardView)) return;

				/*ImageView imageView = ((ImageCardView) viewHolder.view).getMainImageView();
				Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
						imageView, DetailViewExampleFragment.TRANSITION_NAME).toBundle();
				Intent intent = new Intent(getActivity().getBaseContext(),
						DetailViewExampleActivity.class);
				Submission card = (Submission) item;
				int imageResId = card.getLocalImageResourceId(getContext());
				intent.putExtra(DetailViewExampleFragment.EXTRA_CARD, imageResId);
				startActivity(intent, bundle);*/
			}

		});
	}

	public static class PageRowFragmentFactory extends BrowseFragment.FragmentFactory {
		private final BackgroundManager mBackgroundManager;

		PageRowFragmentFactory(BackgroundManager backgroundManager) {
			this.mBackgroundManager = backgroundManager;
		}

		@Override
		public Fragment createFragment(Object rowObj) {
			Row row = (Row)rowObj;
			mBackgroundManager.setDrawable(null);
			if (row.getHeaderItem().getId() == Constants.HEADER_ID[0]) {
				Bundle bundle = new Bundle();
				bundle.putString(Keys.BUNDLE_SUBREDDIT, row.getHeaderItem().getName());

				SubredditFragment subredditFragment= new SubredditFragment();
				subredditFragment.setArguments(bundle);

				return subredditFragment;
			} else if (row.getHeaderItem().getId() == Constants.HEADER_ID[1]) {
				Bundle bundle = new Bundle();
				// TODO: replace
				bundle.putString(Keys.BUNDLE_SUBREDDIT, "All");

				SubredditFragment subredditFragment= new SubredditFragment();
				subredditFragment.setArguments(bundle);

				return subredditFragment;
			} else if (row.getHeaderItem().getId() == Constants.HEADER_ID[2]) {
				return new ProfileFragment();
			} else if (row.getHeaderItem().getId() == Constants.HEADER_ID[3]) {
				return new SettingsFragment(this);
			}

			throw new IllegalArgumentException(String.format("Invalid row %s", rowObj));

		}


	}
}
