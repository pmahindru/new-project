package com.example.csci3130_project_group17;

import android.provider.Settings;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
@RunWith(AndroidJUnit4.class)
public class jobHistoryTest {

    @Rule
    public ActivityScenarioRule<LogIn> activityRule = new ActivityScenarioRule<>(LogIn.class);

    @Before
    public void setUp() throws Exception {
        onView(withId(R.id.email)).perform(typeText("sanju@dal.ca"));
        onView(withId(R.id.password)).perform(typeText("Lol!123"));
        onView(withId(R.id.switchtodashboard)).perform(click());
        onView(withId(R.id.employeehistoryButton)).perform(click());
    }

    @Test
    public void checkIfSwitchToHistory (){
        onView(withId(R.id.historyTItleView)).check(matches(withText("Job History")));
    }

    @Test
    public void checkIfToggleDisplayed (){
        onView(withId(R.id.toggleButtonState)).check(matches(withText("Current")));
    }

    @Test
    public void checkIfToggleDisplayStateChange (){
        onView(withId(R.id.toggleButtonState)).perform(click());
        onView(withId(R.id.toggleButtonState)).check(matches(withText("Completed")));
    }

    @Test
    public void checkIfRecyclerViewDisplayed (){
        onView(ViewMatchers.withId(R.id.historyRecyclerView))
                .check(matches(isDisplayed()));
    }




}
