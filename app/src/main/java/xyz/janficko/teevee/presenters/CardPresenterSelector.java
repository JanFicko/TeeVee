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
import android.support.v17.leanback.widget.PresenterSelector;

import java.util.HashMap;

import xyz.janficko.teevee.R;
import xyz.janficko.teevee.models.Profile;
import xyz.janficko.teevee.models.Submission;
import xyz.janficko.teevee.models.Type;


/**
 * This PresenterSelector will decide what Presenter to use depending on a given card's type.
 */
public class CardPresenterSelector extends PresenterSelector {

    private final Context mContext;
    private final HashMap<Type, Presenter> presenters = new HashMap<Type, Presenter>();

    public CardPresenterSelector(Context context) {
        mContext = context;
    }

    @Override
    public Presenter getPresenter(Object item) {
        Presenter presenter = null;
        if (item instanceof Submission) {
            Submission submission = (Submission) item;
            presenter = presenters.get(submission.getType());
            if (presenter == null) {
                switch (submission.getType()) {
                    case TEXT:
                        presenter = new TextCardPresenter(mContext);
                        break;
                    case THUMBNAIL:
                        presenter = new ThumbnailCardPresenter(mContext);
                        break;
                    case TITLE:
                        presenter = new TitleCardPresenter(mContext);
                        break;

                    case SINGLE_LINE:
                        presenter = new SingleLineCardPresenter(mContext);
                        break;
                    case VIDEO_GRID:
                        presenter = new VideoCardViewPresenter(mContext, R.style.VideoGridCardTheme);
                        break;
                    case MOVIE:
                    case MOVIE_BASE:
                    case MOVIE_COMPLETE:
                    case SQUARE_BIG:
                    case GRID_SQUARE:
                    case GAME: {
                        int themeResId = R.style.MovieCardSimpleTheme;
                        if (submission.getType() == Type.MOVIE_BASE) {
                            themeResId = R.style.MovieCardBasicTheme;
                        } else if (submission.getType() == Type.MOVIE_COMPLETE) {
                            themeResId = R.style.MovieCardCompleteTheme;
                        } else if (submission.getType() == Type.SQUARE_BIG) {
                            themeResId = R.style.SquareBigCardTheme;
                        } else if (submission.getType() == Type.GRID_SQUARE) {
                            themeResId = R.style.GridCardTheme;
                        }
                        presenter = new ImageCardViewPresenter(mContext, themeResId);
                        break;
                    }
                    case SIDE_INFO:
                        presenter = new SideInfoCardPresenter(mContext);
                        break;
                    case ICON:
                        presenter = new IconCardPresenter(mContext);
                        break;
                    default:
                        presenter = new ImageCardViewPresenter(mContext);
                        break;
                }
            }
            presenters.put(submission.getType(), presenter);
        } else if(item instanceof Profile){
            Profile profile = (Profile) item;
            presenter = presenters.get(profile.getType());
            if (presenter == null) {
                switch (profile.getType()) {
                    case COMMENT_KARMA:
                    case LINK_KARMA:
                    case ACCOUNT_CREATED:
                        presenter = new ProfileCardPresenter(mContext);
                        break;
                    case COMMENT:
                        presenter = new CommentsCardPresenter(mContext);
                        break;
                }
            }
            presenters.put(profile.getType(), presenter);
        }
        return presenter;
    }

}
