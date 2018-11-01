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
public class UploadTest {



    @Rule
    public ActivityTestRule<Certificates> mActivityRule = new ActivityTestRule<>(Certificates.class);



    @Test
    public void back() {
        try {


            Espresso.onView(ViewMatchers.withId(R.id.back)).perform(ViewActions.click());

        } catch ( Exception e ) {
            //view not displayed logic
        }

    }


}