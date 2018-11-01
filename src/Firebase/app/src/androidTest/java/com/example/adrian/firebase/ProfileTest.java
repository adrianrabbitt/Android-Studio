package com.example.adrian.firebase;

/**
 * Created by Adrian on 19/04/2018.
 */

import android.support.test.espresso.action.ViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class ProfileTest {



    @Rule
    public ActivityTestRule<Main2Activity> mActivityRule = new ActivityTestRule<>(Main2Activity.class);



    @Test
    public void SwipeTabsRight() {
        try {
            // Type email and password
            onView(withId(R.id.viewpager)).perform(swipeLeft());
            onView(withId(R.id.viewpager)).perform(swipeRight());


            // Click login button
            onView(withId(R.id.signup)).perform(ViewActions.click());
        } catch ( Exception e ) {
            //view not displayed logic
        }

    }


}