package com.artus.mareu.ui.meetings_list;

import static org.greenrobot.eventbus.EventBus.TAG;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.artus.mareu.R;
import com.artus.mareu.databinding.ActivityMainBinding;
import com.artus.mareu.utils.ToolbarMenuManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jakewharton.threetenabp.AndroidThreeTen;


public class MainActivity extends AppCompatActivity  {

    private ActivityMainBinding binding;
    private FloatingActionButton fab;
    private Toolbar mainToolbar;
    public ActivityMainBinding getBinding() {
        return binding;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        mainToolbar = binding.toolbar.getRoot();
        setSupportActionBar(mainToolbar);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, MeetingsFragment.class, null)
                    .commit();
        }
        AndroidThreeTen.init(this);
        configureFab();
    }

    /**
     * handling the Button to launch the create meeting fragment
     */
    private void configureFab(){
        fab = binding.createMeetingFab;
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: start new fragment");
                getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_view, CreateMeetingFragment.class, null).addToBackStack("meetings")
                    .commit();
                fab.setVisibility(View.GONE);
            }
        });
    }



}