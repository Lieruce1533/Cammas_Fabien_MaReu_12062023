package com.artus.mareu;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import com.artus.mareu.R;
import com.artus.mareu.ui.meetings_list.MainActivity;

@RunWith(AndroidJUnit4.class)
public class MaReuInstrumentedTest {

    private MainActivity mActivity;
    private ActivityScenario<MainActivity> scenario;


    @Rule
    public ActivityScenarioRule<MainActivity> rule = new ActivityScenarioRule<>(MainActivity.class);

    /**@Before
    public void setup(){
        scenario = rule.getScenario();
    }
     */

    @Test
    public void meetingList_shouldNotBeEmpty(){
            onView(withId(R.id.recycler_view_meetings)).check(matches(hasMinimumChildCount(1)));
    }



}