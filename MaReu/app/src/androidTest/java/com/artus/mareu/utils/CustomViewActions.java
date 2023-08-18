package com.artus.mareu.utils;

import android.view.View;
import android.widget.Spinner;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.matcher.ViewMatchers;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;

import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
public class CustomViewActions {
    public static ViewAction getCountSpinnerItems(final Matcher<Integer> matcher) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(Spinner.class);
            }

            @Override
            public String getDescription() {
                return "Counting spinner items";
            }

            @Override
            public void perform(UiController uiController, View view) {
                Spinner spinner = (Spinner) view;
                int itemCount = spinner.getAdapter().getCount();
                MatcherAssert.assertThat(itemCount, matcher);
            }
        };
    }

}
