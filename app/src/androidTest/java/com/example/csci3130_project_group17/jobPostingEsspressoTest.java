package com.example.csci3130_project_group17;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;




    @RunWith(AndroidJUnit4.class)
    public class jobPostingEsspressoTest {
        @Rule
        public ActivityScenarioRule<JobPosting> activityRule
                = new ActivityScenarioRule<>(JobPosting.class);

        @Test
        public void checkIfOnJobPostingPage(){
            onView(withId(R.id.textView8)).check(matches(withText("CREAT NEW JOB")));
            onView(withId(R.id.job)).check(matches(withText("JOB TITLE:")));
            onView(withId(R.id.editTextTextPersonName6)).check(matches(withText("")));
            onView(withId(R.id.PAY)).check(matches(withText("PAY RATE:")));
            onView(withId(R.id.editTextTextPersonName10)).check(matches(withText("")));
            onView(withId(R.id.type)).check(matches(withText("TYPE:")));
            onView(withId(R.id.editTextTextPersonName11)).check(matches(withText("")));
            onView(withId(R.id.LOCATION)).check(matches(withText("LOCATION:")));
            onView(withId(R.id.editTextTextPersonName12)).check(matches(withText("")));
            onView(withId(R.id.DES)).check(matches(withText("DESCRIPTION:")));
            onView(withId(R.id.inputforDES)).check(matches(withText("")));
            onView(withId(R.id.create)).check(matches(withText("Create")));
            onView(withId(R.id.Home)).check(matches(withText("HOME")));
        }

    }