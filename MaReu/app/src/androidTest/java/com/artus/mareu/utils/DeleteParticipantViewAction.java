package com.artus.mareu.utils;

import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

import com.artus.mareu.R;

import org.hamcrest.Matcher;

public class DeleteParticipantViewAction implements ViewAction {


    @Override
    public Matcher<View> getConstraints() {
        return null;
    }

    @Override
    public String getDescription() {
        return "click on delete participant button";
    }

    @Override
    public void perform(UiController uiController, View view) {
        View deleteParticipantButton = view.findViewById(R.id.remove_participant);
        deleteParticipantButton.performClick();
    }
}
