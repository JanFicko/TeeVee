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

package xyz.janficko.teevee.models;

import android.content.Context;

import com.google.gson.annotations.SerializedName;

public class DetailedCard {

    @SerializedName("title") private String mTitle = "";
    @SerializedName("user") private String mUser = "";
    @SerializedName("time") private String mTime = "";
    @SerializedName("commentCount") private String mCommentCount = "";
    @SerializedName("score") private String mScore = "";

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getUser() {
        return mUser;
    }

    public void setUser(String user) {
        this.mUser = user;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        this.mTime = mTime;
    }

    public String getCommentCount() {
        return mCommentCount;
    }

    public void setCommentCount(String commentCount) {
        this.mCommentCount = commentCount;
    }

    public String getScore() {
        return mScore;
    }

    public void setScore(String score) {
        this.mScore = score;
    }

    @SerializedName("description") private String mDescription = "";
    @SerializedName("text") private String mText = "";
    @SerializedName("localImageResource") private String mLocalImageResource = null;
    @SerializedName("price") private String mPrice = null;
    @SerializedName("characters") private Submission[] mCharacters = null;
    @SerializedName("recommended") private Submission[] mRecommended = null;
    @SerializedName("year") private int mYear = 0;
    @SerializedName("trailerUrl") private String mTrailerUrl = null;
    @SerializedName("videoUrl") private String mVideoUrl = null;


    public String getPrice() {
        return mPrice;
    }

    public int getYear() {
        return mYear;
    }

    public String getLocalImageResource() {
        return mLocalImageResource;
    }

    public String getText() {
        return mText;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getTrailerUrl() {
        return mTrailerUrl;
    }

    public String getVideoUrl() {
        return mVideoUrl;
    }

    public Submission[] getCharacters() {
        return mCharacters;
    }

    public void setCharacters(Submission[] characters) {
        mCharacters = characters;
    }

    public Submission[] getRecommended() {
        return mRecommended;
    }

    public void setRecommended(Submission[] recommended) {
        mRecommended = recommended;
    }

    public int getLocalImageResourceId(Context context) {
        return context.getResources()
                      .getIdentifier(getLocalImageResource(), "drawable", context.getPackageName());
    }
}
