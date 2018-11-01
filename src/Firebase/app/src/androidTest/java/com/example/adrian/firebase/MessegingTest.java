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
public class MessegingTest {



    @Rule
    public ActivityTestRule<ChatApplication> mActivityRule = new ActivityTestRule<>(ChatApplication.class);



    @Test
    public void MessageUser() {
        try {
            // Type email and password
            Espresso.onView(ViewMatchers.withId(R.id.edittext))
                    .perform(ViewActions.typeText("Test"), ViewActions.closeSoftKeyboard());




            Espresso.onView(ViewMatchers.withId(R.id.img)).perform(ViewActions.click());
        } catch ( Exception e ) {

        }

    }


}