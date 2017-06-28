package xyz.janficko.teevee.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Jan on 25. 06. 2017.
 */

public class Profile {

    @SerializedName("type") private Type mType;
    @SerializedName("commentKarma") private Integer mCommentKarma;
    @SerializedName("postKarma") private Integer mPostKarma;
    @SerializedName("dateCreated") private String mDateCreated;
    @SerializedName("comments") private List<Comment> mCommentList;

    public Type getType() {
        return mType;
    }

    public void setType(Type type) {
        this.mType = type;
    }

    public Integer getCommentKarma() {
        return mCommentKarma;
    }

    public void setCommentKarma(Integer commentKarma) {
        this.mCommentKarma = commentKarma;
    }

    public Integer getPostKarma() {
        return mPostKarma;
    }

    public void setPostKarma(Integer postKarma) {
        this.mPostKarma = postKarma;
    }

    public List<Comment> getCommentList() {
        return mCommentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.mCommentList = commentList;
    }

    public String getDateCreated() {
        return mDateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.mDateCreated = dateCreated;
    }
}
