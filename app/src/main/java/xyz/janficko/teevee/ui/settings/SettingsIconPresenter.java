/*
 * Copyright (C) 2015 The Android Open Source Project
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

package xyz.janficko.teevee.ui.settings;

import android.content.Context;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v4.content.ContextCompat;
import android.view.View;

import xyz.janficko.teevee.R;
import xyz.janficko.teevee.commons.Constants;
import xyz.janficko.teevee.presenters.ImageCardViewPresenter;
import xyz.janficko.teevee.ui.home.MainActivity;

public class SettingsIconPresenter extends ImageCardViewPresenter {

    private static final String TAG = SettingsIconPresenter.class.getSimpleName();
    private MainActivity mMainActivity;

    public SettingsIconPresenter(Context context) {
        super(context, R.style.IconCardTheme);
        mMainActivity = (MainActivity) context;
    }

    @Override
    protected ImageCardView onCreateView() {
        final ImageCardView imageCardView = super.onCreateView();
        imageCardView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    setImageBackground(imageCardView, R.color.settings_card_background_focused);
                } else {
                    setImageBackground(imageCardView, R.color.settings_card_background);
                }
            }
        });
        setImageBackground(imageCardView, R.color.settings_card_background);
        imageCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(imageCardView.getTitleText().equals(Constants.SETTINGS_CATEGORY[0])){
                    //mMainActivity.webViewRedditLogin();
                } else if(imageCardView.getTitleText().equals(Constants.SETTINGS_CATEGORY[1])){
                    //mMainActivity.logout();
                } else if(imageCardView.getTitleText().equals(Constants.SETTINGS_CATEGORY[2])){

                } else if(imageCardView.getTitleText().equals(Constants.SETTINGS_CATEGORY[3])){
                    //mMainActivity.openAbout();
                }
            }
        });
        return imageCardView;
    }

    private void setImageBackground(ImageCardView imageCardView, int colorId) {
        imageCardView.setBackgroundColor(ContextCompat.getColor(getContext(), colorId));
    }
}
