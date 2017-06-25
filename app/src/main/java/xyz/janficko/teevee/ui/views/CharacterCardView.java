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

package xyz.janficko.teevee.ui.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v17.leanback.widget.BaseCardView;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.dean.jraw.models.Subreddit;

import xyz.janficko.teevee.R;
import xyz.janficko.teevee.models.Submission;


public class CharacterCardView extends BaseCardView {

    public CharacterCardView(Context context) {
        super(context, null, R.style.CharacterCardStyle);
        LayoutInflater.from(getContext()).inflate(R.layout.card_characted, this);
        setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                ImageView mainImage = (ImageView) findViewById(R.id.main_image);
                View container = findViewById(R.id.container);
                if (hasFocus) {
                    container.setBackgroundResource(R.drawable.character_focused);
                    mainImage.setBackgroundResource(R.drawable.character_focused);
                } else {
                    container.setBackgroundResource(R.drawable.character_not_focused_padding);
                    mainImage.setBackgroundResource(R.drawable.character_not_focused);
                }
            }
        });
        setFocusable(true);
    }

    public void updateUi(Object object) {
        Submission submission = (Submission) object;
        TextView primaryText = (TextView) findViewById(R.id.primary_text);
        final ImageView imageView = (ImageView) findViewById(R.id.main_image);

        primaryText.setText(submission.getTitle());
        if (submission.getLocalImageResourceName() != null) {
            int resourceId = submission.getLocalImageResourceId(getContext());
            Bitmap bitmap = BitmapFactory
                    .decodeResource(getContext().getResources(), resourceId);
            RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(getContext().getResources(), bitmap);
            drawable.setAntiAlias(true);
            drawable.setCornerRadius(Math.max(bitmap.getWidth(), bitmap.getHeight()) / 2.0f);
            imageView.setImageDrawable(drawable);
        }
    }


}
