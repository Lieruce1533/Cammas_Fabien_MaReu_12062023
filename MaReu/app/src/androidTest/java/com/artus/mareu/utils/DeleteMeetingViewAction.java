package com.artus.mareu.utils;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

import com.artus.mareu.R;
import com.artus.mareu.databinding.ItemviewMeetingBinding;
import org.hamcrest.Matcher;


public class DeleteMeetingViewAction implements ViewAction {



    @Override
    public Matcher<View> getConstraints() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Click on specific button";
    }

    @Override
    public void perform(UiController uiController, View view) {
        View deleteButton = view.findViewById(R.id.item_list_meeting_delete_button);
        deleteButton.performClick();

    }


}
    /**
     * Toast toast = Toast.makeText(binding.getRoot().getContext(), "You must fill all the fields", Toast.LENGTH_LONG);
     *         toast.show();
     */
