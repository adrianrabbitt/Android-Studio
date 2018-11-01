package com.example.adrian.firebase;

/**
 * Created by Adrian on 19/04/2018.
 */

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class OptionsTesting {

    private String mStringToBetyped;

    @Rule
    public ActivityTestRule<LoginEmployee> mActivityRule = new ActivityTestRule<>(LoginEmployee.class);



    @Test
    public void ChooseEmployee() {
//        onView(withId(R.id.Employee)).perform(click());
//        onView(withId(R.id.Employee)).check(matches(not(isEnabled())));

    }

    @Test
    public void ChooseEmployer() {
//        onView(withId(R.id.Employer)).perform(click());
//        onView(withId(R.id.Employer)).check(matches(not(isEnabled())));

    }
}