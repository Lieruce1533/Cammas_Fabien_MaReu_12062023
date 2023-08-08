package com.artus.mareu;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.artus.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;


import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;


import com.artus.mareu.databinding.ItemviewMeetingBinding;
import com.artus.mareu.ui.meetings_list.MainActivity;
import com.artus.mareu.utils.AlertDialogIdlingResource;
import com.artus.mareu.utils.DeleteMeetingViewAction;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MaReuInstrumentedTest {

    private MainActivity mActivity;
    private static int ITEM_COUNTS = 8;
    private AlertDialogIdlingResource alertDialogIdlingResource;
    private ItemviewMeetingBinding binding;



    @Rule
    public ActivityScenarioRule<MainActivity> rule = new ActivityScenarioRule<>(MainActivity.class);

    /**
    @Before
    public void setUp() {
        // Initialize and register the IdlingResource
        alertDialogIdlingResource = new AlertDialogIdlingResource();
        IdlingRegistry.getInstance().register(alertDialogIdlingResource);
    }

    @After
    public void tearDown() {
        // Unregister the IdlingResource
        IdlingRegistry.getInstance().unregister(alertDialogIdlingResource);
    }
     */

    @Test
    public void mareu_meetingList_shouldNotBeEmpty(){
        onView(withId(R.id.recycler_view_meetings)).check(matches(hasMinimumChildCount(1)));
    }
    @Test
    public void mareu_deletingMeeting_shouldRemoveItem(){
        View itemView = LayoutInflater.from(ApplicationProvider.getApplicationContext())
                .inflate(R.layout.itemview_meeting, null);
        //verifying list size
        onView(ViewMatchers.withId(R.id.recycler_view_meetings)).check(withItemCount(ITEM_COUNTS));
        // Create a binding instance and a delete action
        binding = ItemviewMeetingBinding.bind(itemView);
        DeleteMeetingViewAction deleteAction = new DeleteMeetingViewAction(binding);

        onView(ViewMatchers.withId(R.id.recycler_view_meetings)).perform(RecyclerViewActions.actionOnItemAtPosition(1, deleteAction));
        //alertDialogIdlingResource.setIdleState(false);
        //checking if the confirmation dialog is displayed
        onView(ViewMatchers.withText("Delete this Meeting"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        //onView(ViewMatchers.withText("Cancel")).perform(click());
        //checking if the confirmation dialog as been dismissed without deleting any meeting
        //onView(ViewMatchers.withText("Delete this Meeting"))
        //        .check(ViewAssertions.matches((Matcher<? super View>) ViewAssertions.doesNotExist()));
        //onView(ViewMatchers.withId(R.id.recycler_view_meetings)).check(withItemCount(ITEM_COUNTS));
        //onView(ViewMatchers.withId(R.id.recycler_view_meetings)).perform((ViewAction) RecyclerViewActions.actionOnItemAtPosition(1, deleteAction));
        //onView(ViewMatchers.withText("Delete")).perform(click());
        //onView(ViewMatchers.withText("Delete this Meeting"))
        //        .check(ViewAssertions.matches((Matcher<? super View>) ViewAssertions.doesNotExist()));
        //onView(ViewMatchers.withId(R.id.recycler_view_meetings)).check(withItemCount(ITEM_COUNTS-1));
    }



}