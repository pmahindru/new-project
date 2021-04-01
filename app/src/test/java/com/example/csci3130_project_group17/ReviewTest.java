package com.example.csci3130_project_group17;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ReviewTest {
    static Review reviewNormal;
    static Review emptyReview;
    static Review emptyReview2;
    static Review reviewEdgeCase1;
    static Review reviewEdgeCase2;


    @BeforeClass
    public static void setup() {
        reviewNormal = new Review("idReviewer","Sandra Oh", "455332", "idReviewee", 3, "comment");
        emptyReview = new Review();
        emptyReview2 = new Review();
        reviewEdgeCase1 = new Review("reviewer", "Ronald Weasley", "123456", "rewviewee", 0, "my comment");
        reviewEdgeCase2 = new Review("reviewer", "Ronald Weasley", "123456", "rewviewee", 6, "my comment");
    }

    @Test
    public void CheckReviewGetters(){
        reviewNormal.setComment("a comment");
        assertEquals(reviewNormal.getRating(),3);
        assertEquals("idReviewer", reviewNormal.getReviewerID() );
        assertEquals("idReviewee", reviewNormal.getRevieweeID());
        assertEquals("a comment",reviewNormal.getComment());
    }

    @Test
    public void CheckReviewSetters(){
        emptyReview.setReviewerID("idReviewer");
        assertEquals("idReviewer", emptyReview.getReviewerID());
        emptyReview.setRevieweeID("idReviewee");
        assertEquals("idReviewee", emptyReview.getRevieweeID());
        emptyReview.setRating(4);
        assertEquals(emptyReview.getRating(),4);
        emptyReview.setComment("comment");
        assertEquals("comment", emptyReview.getComment());
    }

    @Test
    public void CheckNormalRatingValid(){
        //normal case
        assertTrue(reviewNormal.CheckRatingValueIsValid());
    }

    @Test
    public void CheckZeroRating(){
        //edge case
        assertFalse(reviewEdgeCase1.CheckRatingValueIsValid());
    }

    @Test
    public void CheckSixRating(){
        //edge case
        assertFalse(reviewEdgeCase2.CheckRatingValueIsValid());
    }

    @Test
    public void CheckIfCommentPresent(){
        assertTrue(reviewNormal.CommentLeft());
        assertFalse(emptyReview2.CommentLeft());
    }
}
