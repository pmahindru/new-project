package com.example.csci3130_project_group17;

import android.content.Context;
import android.content.Intent;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class SignUpInstrumentedTest {

    @Rule
    public ActivityScenarioRule<SignUp> myRule = new ActivityScenarioRule<>(SignUp.class);

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.csci3130_project_group17", appContext.getPackageName());
    }

    //this is based on the acceptance test.

    @Test
    public void checkIfSignUpPageIsShown() {
        onView(withId(R.id.textView2)).check(matches(withText(R.string.TITLE)));
        onView(withId(R.id.switchbutton)).check(matches(withText(R.string.SIGNUPBUTTON)));
    }

    @Test
    public void checkIfFirstNameIsEmpty() {
        onView(withId(R.id.fnameinput)).perform(typeText(""));
        onView(withId(R.id.switchbutton)).perform(click());
    }

    @Test
    public void checkIfLastNameIsEmpty() {
        onView(withId(R.id.lnameinput)).perform(typeText(""));
        onView(withId(R.id.switchbutton)).perform(click());
    }

    @Test
    public void checkIfEmailIsEmpty() {
        onView(withId(R.id.emailinput)).perform(typeText(""));
        onView(withId(R.id.switchbutton)).perform(click());
    }

    @Test
    public void checkIfPasswordIsEmpty() {
        onView(withId(R.id.passwordinput)).perform(typeText(""));
        onView(withId(R.id.switchbutton)).perform(click());
    }

    @Test
    public void checkIfUserExists() {
        onView(withId(R.id.fnameinput)).perform(typeText("Sanju"));
        onView(withId(R.id.lnameinput)).perform(typeText("Swami"));
        onView(withId(R.id.emailinput)).perform(typeText("sanju@dal.ca"));
        onView(withId(R.id.passwordinput)).perform(typeText("Lol!123"));
        onView(withId(R.id.switchbutton)).perform(click());
        //check if we move back to the login page instead
    }

    @Test
    public void checkIfSignUpIsSuccessful() {
        onView(withId(R.id.fnameinput)).perform(typeText("Sanju"));
        onView(withId(R.id.lnameinput)).perform(typeText("Swami"));
        onView(withId(R.id.emailinput)).perform(typeText("lol@dal.ca"));
        onView(withId(R.id.passwordinput)).perform(typeText("Lol!123"));
        onView(withId(R.id.switchbutton)).perform(click());
        // add the check for if the activity switches to dashboard
    }







}