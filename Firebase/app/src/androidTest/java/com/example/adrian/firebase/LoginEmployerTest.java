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
public class LoginEmployerTest {



    @Rule
    public ActivityTestRule<LoginEmployer> mActivityRule = new ActivityTestRule<>(LoginEmployer.class);



    @Test
    public void LoginEmloyer() {
        try {
            // Type email and password
            Espresso.onView(ViewMatchers.withId(R.id.email))
                    .perform(ViewActions.typeText("kevin@gmail.com"), ViewActions.closeSoftKeyboard());
            Espresso.onView(ViewMatchers.withId(R.id.password))
                    .perform(ViewActions.typeText("12345678"), ViewActions.closeSoftKeyboard());

            // Click login button
            Espresso.onView(ViewMatchers.withId(R.id.appCompatButtonLogin)).perform(ViewActions.click());
        } catch ( Exception e ) {
            //view not displayed logic
        }

    }


}