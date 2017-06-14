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

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import net.dean.jraw.RedditClient;
import net.dean.jraw.auth.AuthenticationManager;
import net.dean.jraw.auth.AuthenticationState;
import net.dean.jraw.auth.NoSuchTokenException;
import net.dean.jraw.http.NetworkException;
import net.dean.jraw.http.oauth.Credentials;
import net.dean.jraw.http.oauth.OAuthData;
import net.dean.jraw.http.oauth.OAuthException;
import net.dean.jraw.http.oauth.OAuthHelper;

import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.janficko.teevee.R;
import xyz.janficko.teevee.TeeVee;
import xyz.janficko.teevee.commons.Constants;
import xyz.janficko.teevee.ui.error.BrowseErrorActivity;
import xyz.janficko.teevee.ui.onboard.OnboardingActivity;
import xyz.janficko.teevee.ui.settings.AboutActivity;
import xyz.janficko.teevee.util.NetworkStatusUtil;
import xyz.janficko.teevee.util.SharedPreferenceUtil;

public class MainActivity extends Activity {

	private static final String TAG = MainActivity.class.getSimpleName();
	private SharedPreferenceUtil mSharedPreferenceUtil = TeeVee.getInstance().getSharedPreferenceUtil();
	private RedditClient mRedditClient = TeeVee.getInstance().getRedditClient();
	private AuthenticationState mAuthenticateState = AuthenticationManager.get().checkAuthState();
	private URL mAuthorizationUrl;

	@BindView(R.id.web_view)
	WebView mWebView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);

		if(mSharedPreferenceUtil.isFirstStart()){
			// TODO: Fix
			//startActivity(new Intent(this, OnboardingActivity.class));
		}
	}

	@Override
	protected void onResume() {
		super.onResume();

		if(NetworkStatusUtil.isNetworkConnected(this)){
			if(mAuthenticateState == AuthenticationState.NEED_REFRESH){
				new AsyncTask<Void, Void, Void>() {
					@Override
					protected Void doInBackground(Void... params) {
						try {
							AuthenticationManager.get().refreshAccessToken(Constants.CREDENTIALS);
						} catch (NoSuchTokenException | OAuthException e) {
							Log.e(TAG, "Could not refresh access token", e);
						}
						return null;
					}
				}.execute();
			}
		} else {
			startActivity(new Intent(this, BrowseErrorActivity.class));
		}
	}

	public void webViewRedditLogin(){
		mWebView.setVisibility(View.VISIBLE);
		final OAuthHelper helper = AuthenticationManager.get().getRedditClient().getOAuthHelper();
		mAuthorizationUrl = helper.getAuthorizationUrl(Constants.CREDENTIALS, true, true, Constants.REDDIT_SCOPES);
		mWebView.requestFocus(View.FOCUS_DOWN | View.FOCUS_LEFT | View.FOCUS_RIGHT | View.FOCUS_UP);
		mWebView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				if (url.contains("code=")) {
					// We've detected the redirect URL
					new AsyncTask<String, Void, String>() {
						@Override
						protected String doInBackground(String... params) {
							try {
								OAuthData data = AuthenticationManager.get().getRedditClient().getOAuthHelper().onUserChallenge(params[0], Constants.CREDENTIALS);
								AuthenticationManager.get().getRedditClient().authenticate(data);
								return AuthenticationManager.get().getRedditClient().getAuthenticatedUser();
							} catch (NetworkException | OAuthException e) {
								Log.e(TAG, "Could not log in", e);
								return null;
							}
						}

						@Override
						protected void onPostExecute(String s) {
							super.onPostExecute(s);

							Intent intent = new Intent(MainActivity.this, MainActivity.class);
							startActivity(intent);
							finishAffinity();
						}
					}.execute(url);
				} else if (url.contains("error=")) {
					Toast.makeText(MainActivity.this, getString(R.string.error_permission_needed), Toast.LENGTH_SHORT).show();
					mWebView.loadUrl(mAuthorizationUrl.toExternalForm());
				}
			}
		});
		mWebView.loadUrl(mAuthorizationUrl.toExternalForm());
	}

	public void logout(){
		new AsyncTask<Void,Void,Void>() {

			@Override
			protected Void doInBackground(Void... params) {

				TeeVee.getInstance()
						.getRedditClient()
						.getOAuthHelper()
						.revokeAccessToken(Constants.CREDENTIALS);
				mRedditClient.deauthenticate();

				return null;
			}

			@Override
			protected void onPostExecute(Void aVoid) {
				super.onPostExecute(aVoid);

				Intent intent = new Intent(MainActivity.this, MainActivity.class);
				startActivity(intent);
				finishAffinity();
			}
		}.execute();
	}

	public void openAbout(){
		Intent intent = new Intent(this.getBaseContext(), AboutActivity.class);
		startActivity(intent);
	}

}
