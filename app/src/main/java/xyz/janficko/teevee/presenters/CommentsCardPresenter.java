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

import xyz.janficko.teevee.ui.views.CommentsCardView;
import xyz.janficko.teevee.ui.views.TextCardView;

public class CommentsCardPresenter extends AbstractCardPresenter<CommentsCardView> {

    public CommentsCardPresenter(Context context) {
        super(context);
    }

    @Override
    protected CommentsCardView onCreateView() {
        return new CommentsCardView(getContext());
    }

    @Override
    public void onBindViewHolder(Object profile, final CommentsCardView cardView) {
        cardView.updateUi(profile);
    }

}
