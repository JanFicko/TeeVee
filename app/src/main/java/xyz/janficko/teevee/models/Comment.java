package xyz.janficko.teevee.models;

/**
 * Created by Jan on 28. 06. 2017.
 */

public class Comment {

    private String submissionTitle;
    private String comment;

    public Comment(){}

    public String getSubmissionTitle() {
        return submissionTitle;
    }

    public void setSubmissionTitle(String submissionTitle) {
        this.submissionTitle = submissionTitle;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
