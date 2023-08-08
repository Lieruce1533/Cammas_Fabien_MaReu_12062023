package com.artus.mareu.utils;

import android.view.View;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import com.artus.mareu.databinding.ItemviewMeetingBinding;
import org.hamcrest.Matcher;


public class DeleteMeetingViewAction implements ViewAction {

    ItemviewMeetingBinding binding;

    public DeleteMeetingViewAction(ItemviewMeetingBinding binding) {
        this.binding = binding;
    }

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
        View button = binding.itemListMeetingDeleteButton;
        // Maybe check for null
        button.performClick();
    }


}
