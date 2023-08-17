package com.artus.mareu.utils;



import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.DialogFragment;

import com.artus.mareu.R;
import com.artus.mareu.ui.meetings_list.MainActivity;
import com.artus.mareu.ui.meetings_list.Pickers.RoomPickerFragment;
import com.artus.mareu.ui.meetings_list.Pickers.datePickerFragment;

import java.util.ArrayList;

public class ToolbarMenuManager {

    private Toolbar mToolbar;
    private Menu mMenu;
    private ActionBar mActionBar;
    private MainActivity mMainActivity;
    private MenuProvider menuProvider;

    public ToolbarMenuManager(Toolbar toolbar, MainActivity mainActivity) {
        mToolbar = toolbar;
        mMainActivity = mainActivity;
        this.menuProvider= new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.menu_toolbar, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {

                return false;
            }
        };
        mMainActivity.setSupportActionBar(mToolbar);
        mActionBar= mMainActivity.getSupportActionBar();
    }


    /**
     * Managing the menu
     */
    public void setMeetingFragmentMenu() {
        // Inflate and set the menu for Fragment One
        mToolbar.inflateMenu(R.menu.menu_toolbar);
    }

    public void clearMenu() {
        // Clear the current menu items
        mToolbar.getMenu().clear();
    }


    /**
     * managing back button
     */
    public void showSystemBackButton() {
        // Show the system back button
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeButtonEnabled(true);

    }

    public void hideSystemBackButton() {
        // Hide the system back button
        mActionBar.setDisplayHomeAsUpEnabled(false);
        mActionBar.setHomeButtonEnabled(false);
    }


    /**
     * Setting toolbar title
     * @param title
     */
    public void updateTitle(String title){

        mActionBar.setTitle(title);
    }


    public void menuIsSet(boolean visibility){

    }


}
