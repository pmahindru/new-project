package com.example.csci3130_project_group17;

public class Review {

    private String reviewerID;
    private String revieweeID;
    private int rating;
    private String comment;

    //constructors
    public Review(){}

    public Review(String reviewerID, String revieweeID, int rating, String comment) {
        this.reviewerID = reviewerID;
        this.revieweeID = revieweeID;
        this.rating = rating;
        this.comment = comment;
    }

    //getters and setters
    public String getReviewerID() {
        return reviewerID;
    }

    public void setReviewerID(String reviewerID) {
        this.reviewerID = reviewerID;
    }

    public String getRevieweeID() {
        return revieweeID;
    }

    public void setRevieweeID(String revieweeID) {
        this.revieweeID = revieweeID;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean CheckRatingValueIsValid(){
        if(this.rating >0 && this.rating <= 5 ){
            return true;
        }else{
            return false;
        }
    }

    public boolean CommentLeft(){
        if (this.comment==null){
            return false;
        } else{
            return true;
        }
    }



}
