package com.example.csci3130_project_group17;

public class Review {
    public String comment;
    public String jobID;
    public String revieweeID;
    public String reviewerID;
    public String reviewerName;
    public int rating;

    public Review() {
    }

    public Review(String comment, String jobID, String revieweeID, String reviewerID, String reviewerName, int rating) {
        this.comment = comment;
        this.jobID = jobID;
        this.revieweeID = revieweeID;
        this.reviewerID = reviewerID;
        this.reviewerName = reviewerName;
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getJobID() {
        return jobID;
    }

    public void setJobID(String jobID) {
        this.jobID = jobID;
    }

    public String getRevieweeID() {
        return revieweeID;
    }

    public void setRevieweeID(String revieweeID) {
        this.revieweeID = revieweeID;
    }

    public String getReviewerID() {
        return reviewerID;
    }

    public void setReviewerID(String reviewerID) {
        this.reviewerID = reviewerID;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
