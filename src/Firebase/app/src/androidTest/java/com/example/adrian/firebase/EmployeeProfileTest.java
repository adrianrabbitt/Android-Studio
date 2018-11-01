package com.example.adrian.firebase;

/**
 * Created by Adrian on 19/04/2018.
 */

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class EmployeeProfileTest {



    @Rule
    public ActivityTestRule<Employee> mActivityRule = new ActivityTestRule<>(Employee.class);



    @Test
    public void Employer() {
        try {
            // Type email and password

            Espresso.onView(ViewMatchers.withId(R.id.settings)).perform(ViewActions.click());
            Espresso.onView(ViewMatchers.withId(R.id.info)).perform(ViewActions.click());

            // Click login button
            //  Espresso.onView(ViewMatchers.withId(R.id.signup)).perform(ViewActions.click());
        } catch ( Exception e ) {
            //view not displayed logic
        }

    }


}