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

package xyz.janficko.teevee.presenters;

import android.content.Context;

import xyz.janficko.teevee.models.Submission;
import xyz.janficko.teevee.ui.views.ThumbnailCardView;

public class ThumbnailCardPresenter extends AbstractCardPresenter<ThumbnailCardView> {

    public ThumbnailCardPresenter(Context context) {
        super(context);
    }

    @Override
    protected ThumbnailCardView onCreateView() {
        return new ThumbnailCardView(getContext());
    }

    @Override
    public void onBindViewHolder(Object submission, ThumbnailCardView cardView) {
        cardView.updateUi(submission);
    }

}
