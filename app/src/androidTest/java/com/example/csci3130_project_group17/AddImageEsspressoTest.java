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
public class AddImageEsspressoTest {
    @Rule
    public ActivityScenarioRule<AddImage> activityRule
            = new ActivityScenarioRule<>(AddImage.class);

    @Test
    public void checkIfOnAddImage(){
        onView(withId(R.id.textView3)).check(matches(withText("  Enter job name")));
        onView(withId(R.id.ChooseIMG)).check(matches(withText("CHOOSE IMAGE")));
        onView(withId(R.id.UploadIMG)).check(matches(withText("UPLOAD")));
        onView(withId(R.id.text)).check(matches(withText("")));

    }

}
