package com.example.csci3130_project_group17;

import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.file.Path;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class JobApplicationUnitTest {
    static JobApplication jobApplication;

    @BeforeClass
    public static void setup() {
        jobApplication = new JobApplication();
    }



    // Checks if a field is empty
    @Test
    public void isEmpty() {
        assertTrue(jobApplication.isInputEmpty(""));
        assertFalse(jobApplication.isInputEmpty("xyz$56"));
        assertTrue(jobApplication.isInputEmpty(""));
        assertFalse(jobApplication.isInputEmpty("xyasdz$56"));
        assertTrue(jobApplication.isInputEmpty(""));
        assertFalse(jobApplication.isInputEmpty("sanju@dal.ca"));
        assertTrue(jobApplication.isInputEmpty(""));
        assertFalse(jobApplication.isInputEmpty("9022983534"));
        assertTrue(jobApplication.isInputEmpty(""));
        assertFalse(jobApplication.isInputEmpty("44.523423,-123.12312312"));
        assertTrue(jobApplication.isInputEmpty(""));
        assertFalse(jobApplication.isInputEmpty("xyz.pdf"));
    }

    // Checks if email is valid
    @Test
    public void isEmailValid() {
        assertTrue(jobApplication.emailCheck("sanju_123@dal.ca"));
        assertTrue(jobApplication.emailCheck("sanjana.123@dal.ca"));
    }

    // Checks if email is invalid
    @Test
    public void isEmailInvalid() {
        assertFalse(jobApplication.emailCheck("sanju.dal.ca"));
        assertFalse(jobApplication.emailCheck("sanju@dal"));
    }

}


