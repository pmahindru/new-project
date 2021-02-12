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
            onView(withId(R.id.jobScreenTitle)).check(matches(withText("CREAT NEW JOB")));
            onView(withId(R.id.jobTitleLabel)).check(matches(withText("JOB TITLE:")));
            onView(withId(R.id.jobTitleInput)).check(matches(withText("")));
            onView(withId(R.id.jobPayRateLabel)).check(matches(withText("PAY RATE:")));
            onView(withId(R.id.jobTypeInput)).check(matches(withText("")));
            onView(withId(R.id.jobTypeLabel)).check(matches(withText("TYPE:")));
            onView(withId(R.id.jobPayRateInput)).check(matches(withText("")));
            onView(withId(R.id.jobLocationLabel)).check(matches(withText("LOCATION:")));
            onView(withId(R.id.jobLocationInput)).check(matches(withText("")));
            onView(withId(R.id.jobDescriptionLabel)).check(matches(withText("DESCRIPTION:")));
            onView(withId(R.id.jobDescriptionInput)).check(matches(withText("")));
            onView(withId(R.id.createJobButton)).check(matches(withText("Create")));
            onView(withId(R.id.homeButton)).check(matches(withText("HOME")));
        }

    }