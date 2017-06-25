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
 *
 */

package xyz.janficko.teevee.presenters;

import android.content.Context;
import android.support.v17.leanback.widget.Presenter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import xyz.janficko.teevee.R;
import xyz.janficko.teevee.models.DetailedCard;
import xyz.janficko.teevee.util.ResourceCache;

/**
 * This presenter is used to render a {@link DetailedCard} in the {@link
 * DetailFragment}.
 */
public class DetailsDescriptionPresenter extends Presenter {

    private ResourceCache mResourceCache = new ResourceCache();
    private Context mContext;

    public DetailsDescriptionPresenter(Context context) {
        mContext = context;
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_detail, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        TextView tvTitle = mResourceCache.getViewById(viewHolder.view, R.id.tv_title);
        TextView tvUser = mResourceCache.getViewById(viewHolder.view, R.id.tv_user);
        TextView tvTime = mResourceCache.getViewById(viewHolder.view, R.id.tv_time);
        TextView tvComments = mResourceCache.getViewById(viewHolder.view, R.id.tv_comments);
        TextView tvScore = mResourceCache.getViewById(viewHolder.view, R.id.tv_score);

        DetailedCard card = (DetailedCard) item;
        tvTitle.setText(card.getTitle());
        tvUser.setText(card.getTitle());
        tvTime.setText(card.getTitle());
        tvComments.setText(card.getTitle());
        tvScore.setText(card.getTitle());
    }

    @Override public void onUnbindViewHolder(ViewHolder viewHolder) {
        // Nothing to do here.
    }
}
