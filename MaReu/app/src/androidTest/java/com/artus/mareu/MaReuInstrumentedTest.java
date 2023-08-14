package com.artus.mareu;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withChild;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.artus.mareu.utils.CustomViewActions.getCountSpinnerItems;
import static com.artus.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;

import android.widget.DatePicker;
import android.widget.TimePicker;


import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;


import com.artus.mareu.model.Meeting;
import com.artus.mareu.ui.meetings_list.MainActivity;
import com.artus.mareu.utils.DeleteMeetingViewAction;
import com.artus.mareu.utils.DeleteParticipantViewAction;

import org.hamcrest.Matchers;
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




    @Rule
    public ActivityScenarioRule<MainActivity> rule = new ActivityScenarioRule<>(MainActivity.class);


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
                LocalDateTime.of(2023,7,12,10,30),"Froyo",
                new ArrayList<>(Arrays.asList("jack.leak@Veggies.net", "donald.peas@Veggies.net")));
        onView(ViewMatchers.withId(R.id.editTextTitle)).perform(typeText("Test meeting"), closeSoftKeyboard());
        //check if text is right in the edit text
        onView(ViewMatchers.withId(R.id.editTextTitle)).check(matches(withText("Test meeting")));
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
        onView(ViewMatchers.withId(R.id.spinnerRoom)).perform((getCountSpinnerItems(is(6))));
        onView(ViewMatchers.withId(R.id.spinnerRoom)).perform(click());
        onData(is("Froyo")).perform(click());
        onView(withId(R.id.spinnerRoom)).check(matches(withSpinnerText(containsString("Froyo"))));
        //check participants
        //onView(ViewMatchers.withId(R.id.recycler_view_participants)).check(withItemCount(0));
        onView(ViewMatchers.withId(R.id.text_input_participant)).perform(typeText("mike.green@Veggies.net"), closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.Add_participant)).perform(click());
        onView(ViewMatchers.withId(R.id.text_input_participant)).perform(typeText("jack.leak@Veggies.net"), closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.Add_participant)).perform(click());
        onView(ViewMatchers.withId(R.id.text_input_participant)).perform(typeText("donald.peas@Veggies.net"), closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.Add_participant)).perform(click());
        onView(ViewMatchers.withId(R.id.recycler_view_participants)).check(withItemCount(3));
        onView(ViewMatchers.withId(R.id.recycler_view_participants)).perform(RecyclerViewActions.actionOnItemAtPosition(0, new DeleteParticipantViewAction()));
        onView(ViewMatchers.withId(R.id.recycler_view_participants)).check(withItemCount(2));
        //check the saving button, should create the new meeting with all the information given and add it to the meeting list so we should see it in the
        onView(ViewMatchers.withId(R.id.button_add)).perform(click());
        //check the list meeting as grown of 1 meeting
        onView(ViewMatchers.withId(R.id.recycler_view_meetings)).check(withItemCount(ITEM_COUNTS +1));
        onView(ViewMatchers.withId(R.id.recycler_view_meetings)).perform(RecyclerViewActions.scrollToLastPosition());
        onView(allOf(withId(R.id.item_list_meeting_title), withText("Test meeting"))).check(matches(isDisplayed()));

    }

    /**
     * tests for the toolbar, after refactoring de code
     * toolbar on fragment meeting, menu is visible, backbutton isn't.
     * toolbar on fragment create meeting, menu is gone, back button is visible.
     * then instrumented test are finished
     */



}