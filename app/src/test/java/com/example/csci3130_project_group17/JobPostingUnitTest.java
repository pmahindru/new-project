package com.example.csci3130_project_group17;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class JobPostingUnitTest {
    static JobPosting jobPosting;

    @BeforeClass
    public static void setup() {
        jobPosting = new JobPosting();
    }

    @Test
    public void checkIfJobTitleIsEmpty() {
        assertTrue(jobPosting.jobTitleIsEmpty(""));
        assertFalse(jobPosting.jobTitleIsEmpty("House Painter"));
    }

    @Test
    public void checkIfJobTypeIsEmpty() {
        assertTrue(jobPosting.jobTypeIsEmpty(""));
        assertFalse(jobPosting.jobTypeIsEmpty("Maintenance"));
    }

    @Test
    public void checkIfJobLocationIsEmpty() {
        assertTrue(jobPosting.jobLocationIsEmpty(""));
        assertFalse(jobPosting.jobLocationIsEmpty("Halifax"));
    }

    @Test
    public void checkIfJobPayRateIsEmpty() {
        assertTrue(jobPosting.jobPayRateIsEmpty(""));
        assertFalse(jobPosting.jobPayRateIsEmpty("15.00"));
    }

    @Test
    public void checkIfJobPayDescriptionIsEmpty() {
        assertTrue(jobPosting.jobDescriptionIsEmpty(""));
        assertFalse(jobPosting.jobDescriptionIsEmpty("This is a really nice job!"));
    }

    @AfterClass
    public static void tearDown(){
        System.gc();
    }

}
