package com.example.csci3130_project_group17;

import android.content.Context;
import android.service.autofill.OnClickAction;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class JobApplicationInstrumentedTest {
    @Rule
    public ActivityScenarioRule<JobApplication> myRule = new ActivityScenarioRule<>(JobApplication.class);

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.csci3130_project_group17", appContext.getPackageName());
    }

    //this is based on the acceptance test.
    @Test
    public void checkIfFirstNameIsEmpty() {
        onView(withId(R.id.Firstname)).perform(typeText(""));
        onView(withId(R.id.Apply)).perform(click());
    }

    @Test
    public void checkIfLastNameIsEmpty() {
        onView(withId(R.id.Lastname)).perform(typeText(""));
        onView(withId(R.id.Apply)).perform(click());
    }

    @Test
    public void checkIfEmailIsEmpty() {
        onView(withId(R.id.Email)).perform(typeText(""));
        onView(withId(R.id.Apply)).perform(click());
    }

    @Test
    public void checkIfPasswordIsEmpty() {
        onView(withId(R.id.Phonenumber)).perform(typeText(""));
        onView(withId(R.id.Apply)).perform(click());
    }

}

