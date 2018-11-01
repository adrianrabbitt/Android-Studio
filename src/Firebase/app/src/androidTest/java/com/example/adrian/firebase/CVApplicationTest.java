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
public class CVApplicationTest {



    @Rule
    public ActivityTestRule<CV> mActivityRule = new ActivityTestRule<>(CV.class);



    @Test
    public void Regester() {
        try {
            // Type email and password
            Espresso.onView(ViewMatchers.withId(R.id.firstName))
                    .perform(ViewActions.typeText("Test"), ViewActions.closeSoftKeyboard());

            // Type email and password
            Espresso.onView(ViewMatchers.withId(R.id.secondName))
                    .perform(ViewActions.typeText("TestSecondName"), ViewActions.closeSoftKeyboard());

            Espresso.onView(ViewMatchers.withId(R.id.email))
                    .perform(ViewActions.typeText("test2@gmail.com"), ViewActions.closeSoftKeyboard());

            // Type email and password
            Espresso.onView(ViewMatchers.withId(R.id.mobile))
                    .perform(ViewActions.typeText("08777777777"), ViewActions.closeSoftKeyboard());

            // Type email and password
            Espresso.onView(ViewMatchers.withId(R.id.phone))
                    .perform(ViewActions.typeText("0877777777"), ViewActions.closeSoftKeyboard());
            // Type email and password
            Espresso.onView(ViewMatchers.withId(R.id.Address1))
                    .perform(ViewActions.typeText("Kilsarn Loughduff"), ViewActions.closeSoftKeyboard());

            // Type email and password
            Espresso.onView(ViewMatchers.withId(R.id.Address2))
                    .perform(ViewActions.typeText("Co Cavan"), ViewActions.closeSoftKeyboard());

            // Type email and password
            Espresso.onView(ViewMatchers.withId(R.id.Info))
                    .perform(ViewActions.typeText("This is for specific Information"), ViewActions.closeSoftKeyboard());

            // Type email and password
            Espresso.onView(ViewMatchers.withId(R.id.ptitle))
                    .perform(ViewActions.typeText("JobTest"), ViewActions.closeSoftKeyboard());

            // Type email and password
            Espresso.onView(ViewMatchers.withId(R.id.skills))
                    .perform(ViewActions.typeText("creative"), ViewActions.closeSoftKeyboard());

            Espresso.onView(ViewMatchers.withId(R.id.Password))
                    .perform(ViewActions.typeText("12345678"), ViewActions.closeSoftKeyboard());
            Espresso.onView(ViewMatchers.withId(R.id.re))
                    .perform(ViewActions.typeText("12345678"), ViewActions.closeSoftKeyboard());



            // Click login button

        } catch ( Exception e ) {
            //view not displayed logic
        }

    }


}