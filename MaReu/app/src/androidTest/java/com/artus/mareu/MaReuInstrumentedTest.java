package com.artus.mareu;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.NavigationViewActions.navigateTo;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withTagValue;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.artus.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;


import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static java.util.EnumSet.allOf;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;


import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;


import com.artus.mareu.databinding.ItemviewMeetingBinding;
import com.artus.mareu.model.Meeting;
import com.artus.mareu.ui.meetings_list.MainActivity;
import com.artus.mareu.utils.AlertDialogIdlingResource;
import com.artus.mareu.utils.DeleteMeetingViewAction;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;

import java.util.ArrayList;
import java.util.Arrays;

@RunWith(AndroidJUnit4.class)
public class MaReuInstrumentedTest {


    private static int ITEM_COUNTS = 8;
    private AlertDialogIdlingResource alertDialogIdlingResource;
    private ItemviewMeetingBinding binding;



    @Rule
    public ActivityScenarioRule<MainActivity> rule = new ActivityScenarioRule<>(MainActivity.class);


    public void setUp() {
        // Initialize and register the IdlingResource
        alertDialogIdlingResource = new AlertDialogIdlingResource();
        IdlingRegistry.getInstance().register(alertDialogIdlingResource);
    }
    public void tearDown() {
        // Unregister the IdlingResource
        IdlingRegistry.getInstance().unregister(alertDialogIdlingResource);
    }

    /**
     * Test operational
     */
    @Test
    public void mareu_meetingList_shouldNotBeEmpty(){
        onView(withId(R.id.recycler_view_meetings)).check(matches(hasMinimumChildCount(1)));
    }

    /**
     * Test operational
     */
    @Test
    public void mareu_deletingMeeting_shouldRemoveItem(){
        //verifying list size
        onView(ViewMatchers.withId(R.id.recycler_view_meetings)).check(withItemCount(ITEM_COUNTS));
        //Checking if the delete button is working
        onView(ViewMatchers.withId(R.id.recycler_view_meetings))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1,new DeleteMeetingViewAction()));
        //checking if the confirmation dialog is displayed
        onView(ViewMatchers.withText("Delete this Meeting")).inRoot(isDialog()).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        //checking if the confirmation dialog as been dismissed without deleting any meeting
        onView(ViewMatchers.withText("Cancel")).inRoot(isDialog()).perform(click());
        onView(ViewMatchers.withId(R.id.recycler_view_meetings)).check(withItemCount(ITEM_COUNTS));
        //checking if the confirmation dialog as been dismissed after deleting a meeting
        onView(ViewMatchers.withId(R.id.recycler_view_meetings))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1,new DeleteMeetingViewAction()));
        onView(ViewMatchers.withText("Delete")).inRoot(isDialog()).perform(click());
        onView(ViewMatchers.withText("Delete this Meeting")).check(ViewAssertions.doesNotExist());
        onView(ViewMatchers.withId(R.id.recycler_view_meetings)).check(withItemCount(ITEM_COUNTS-1));


    }

    /**
     * Test operational
     */
    @Test
    public void mareu_meeting_fragment_menu_is_working() throws InterruptedException {
        //testing filter by date
        LocalDate dateToFilter = LocalDate.of(2023, 07, 12);
        int itemCountsOnTheDateToFilter = 5;
        onView(ViewMatchers.withId(R.id.recycler_view_meetings)).check(withItemCount(ITEM_COUNTS));
        onView(ViewMatchers.withId(R.id.item_filter)).perform(click());
        onView(ViewMatchers.withText("filter by date")).inRoot(isPlatformPopup()).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2023, 07, 12));
        onView(ViewMatchers.withText("OK")).inRoot(isDialog()).perform(click());
        onView(ViewMatchers.withId(R.id.recycler_view_meetings)).check(withItemCount(itemCountsOnTheDateToFilter));
        //testing reset filter
        onView(ViewMatchers.withId(R.id.item_filter)).perform(click());
        onView(ViewMatchers.withText("remove filter")).inRoot(isPlatformPopup()).perform(click());
        onView(ViewMatchers.withId(R.id.recycler_view_meetings)).check(withItemCount(ITEM_COUNTS));
        //testing filter by room
        onView(ViewMatchers.withId(R.id.item_filter)).perform(click());
        onView(ViewMatchers.withText("filter by room")).inRoot(isPlatformPopup()).perform(click());
        onView(ViewMatchers.withText("Gingerbread")).inRoot(isDialog()).perform(click());
        onView(ViewMatchers.withId(R.id.recycler_view_meetings)).check(withItemCount(1));
    }

    /**
     * Test operational
     */
    @Test
    public void mareu_create_meeting_fab_launch_create_fragment(){
        onView(ViewMatchers.withId(R.id.recycler_view_meetings)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        onView(ViewMatchers.withId(R.id.create_meeting_fab)).perform(click());
        onView(ViewMatchers.withId(R.id.editTextTitle)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }


    @Test
    public void mareu_create_meeting_fragment_is_editable(){
        onView(ViewMatchers.withId(R.id.create_meeting_fab)).perform(click());
        Meeting meetingToAdd = new Meeting(9,"Test meeting",
                LocalDateTime.of(2023,7,12,10,30),"froyo",
                new ArrayList<>(Arrays.asList("edouard@caramail.fr", "gontrand@lycos.fr","charles-kévin@lycos.fr")));
        onView(ViewMatchers.withId(R.id.editTextTitle)).perform(typeText("Test meeting"), closeSoftKeyboard());
        //check date picker
        onView(ViewMatchers.withId(R.id.layoutDate)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2023, 7, 12));
        onView(ViewMatchers.withText("OK")).inRoot(isDialog()).perform(click());
        onView(ViewMatchers.withId(R.id.TextDate)).check(matches(withText("2023-07-12")));
        //check time picker
        onView(ViewMatchers.withId(R.id.layoutTime)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(10,30));
        onView(ViewMatchers.withText("OK")).inRoot(isDialog()).perform(click());
        onView(ViewMatchers.withId(R.id.TextTime)).check(matches(withText("10:30")));
        //check spinner
        onView(ViewMatchers.withId(R.id.spinnerRoom)).check(matches(isDisplayed()));
        //onView(ViewMatchers.withId(R.id.spinnerRoom)).check((6));
        onView(ViewMatchers.withId(R.id.spinnerRoom)).perform(click());
        //I'm stuck here
        //onData(allOf(is(instanceOf(String.class)),is(COUNTRY).perform(click());
        //onData(allOf(is(instanceOf(String.class)), is("froyo")).perform(click()));
        //onData(ViewMatchers.withText("froyo")).perform(click());

        //



    }




}