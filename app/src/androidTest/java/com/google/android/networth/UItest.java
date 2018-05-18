package com.google.android.networth;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.logging.Logger;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class UItest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule(MainActivity.class);


    @Test
    public void changeText_sameActivity() {
        MainActivity stubActivity =  mActivityRule.getActivity();
        ValueHolder testValuholder = stubActivity.valueholder;

        testValuholder.setTotal(2000);
        testValuholder.setYesterday(1000);
        testValuholder.calculateDelta();

        // Check that the total
        String expected_total = "2k Eur";
        onView(withId(R.id.networth))
                .check(matches(withText(expected_total)));

        //Check the value for Yesterday
        String expected_yesterday = "Yesterday: 1k Eur (100%)";
        onView(withId(R.id.yesterday))
                .check(matches(withText(expected_yesterday)));

    }
}
