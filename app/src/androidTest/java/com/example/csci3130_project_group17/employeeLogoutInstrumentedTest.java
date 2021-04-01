package com.example.csci3130_project_group17;

import android.content.Context;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class employeeLogoutInstrumentedTest {

    @Rule
    public ActivityScenarioRule<DashboardEmployee> myRule = new ActivityScenarioRule<>(DashboardEmployee.class);

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.csci3130_project_group17", appContext.getPackageName());
    }

    //this is based on the acceptance test.
    @Test
    public void checkIfEmployeeRedirectToLogin() {
        onView(withId(R.id.logoutButton2)).perform(click());
        onView(withId(R.id.switchtodashboard)).check(matches(withText(R.string.login)));
    }

}

