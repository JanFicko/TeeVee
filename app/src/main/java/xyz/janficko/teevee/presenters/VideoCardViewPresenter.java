/*
 * Copyright (C) 2016 The Android Open Source Project
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

package xyz.janficko.teevee.presenters;

import android.content.Context;
import android.support.v17.leanback.widget.ImageCardView;

import com.bumptech.glide.Glide;

import xyz.janficko.teevee.models.Submission;
import xyz.janficko.teevee.models.VideoSubmission;

/**
 * Presenter for rendering video cards on the Vertical Grid fragment.
 */
public class VideoCardViewPresenter extends ImageCardViewPresenter {

    public VideoCardViewPresenter(Context context, int cardThemeResId) {
        super(context, cardThemeResId);
    }

    public VideoCardViewPresenter(Context context) {
        super(context);
    }

    @Override
    public void onBindViewHolder(Object object, final ImageCardView cardView) {
        Submission submission = (Submission) object;
        super.onBindViewHolder(submission, cardView);
        VideoSubmission videoCard = (VideoSubmission) submission;
        Glide.with(getContext())
                .load(videoCard.getImageUrl())
                .asBitmap()
                .into(cardView.getMainImageView());

    }

}
