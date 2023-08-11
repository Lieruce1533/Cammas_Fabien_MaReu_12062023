package com.artus.mareu.utils;

import android.view.Menu;

import androidx.appcompat.widget.Toolbar;

import com.artus.mareu.ui.meetings_list.MainActivity;

public class ToolbarMenuManager {

    private Toolbar mToolbar;
    private Menu mMenu;
    private MainActivity mMainActivity;

    /**
     * managing back button
     * @param visible
     */
    public void backButtonHandling(boolean visible){
        mMainActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(visible);
    }

    /**
     * Setting toolbar title
     * @param title
     */
    public void toolbarTitle(String title){
        mToolbar.setTitle(title);
    }

    /**
     * Setting menu to the toolbar
     */
    public void menuIsSet(boolean visibility){

    }
}
