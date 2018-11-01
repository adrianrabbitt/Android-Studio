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
public class RegesterTest {



    @Rule
    public ActivityTestRule<Signup> mActivityRule = new ActivityTestRule<>(Signup.class);



    @Test
    public void SignUp() {
        try {
            // Type email and password
            Espresso.onView(ViewMatchers.withId(R.id.Name))
                    .perform(ViewActions.typeText("Test"), ViewActions.closeSoftKeyboard());
            Espresso.onView(ViewMatchers.withId(R.id.Email))
                    .perform(ViewActions.typeText("test2@gmail.com"), ViewActions.closeSoftKeyboard());

            Espresso.onView(ViewMatchers.withId(R.id.Password))
                    .perform(ViewActions.typeText("12345678"), ViewActions.closeSoftKeyboard());

            Espresso.onView(ViewMatchers.withId(R.id.re))
                    .perform(ViewActions.typeText("12345678"), ViewActions.closeSoftKeyboard());



            // Click login button
            Espresso.onView(ViewMatchers.withId(R.id.signup)).perform(ViewActions.click());
        } catch ( Exception e ) {
            //view not displayed logic
        }

    }


}