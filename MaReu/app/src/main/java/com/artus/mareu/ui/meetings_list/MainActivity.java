package com.artus.mareu.ui.meetings_list;

import static org.greenrobot.eventbus.EventBus.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;

import com.artus.mareu.R;
import com.artus.mareu.databinding.ActivityMainBinding;
import com.artus.mareu.di.MareuInjection;
import com.artus.mareu.repository.MareuRepository;
import com.artus.mareu.utils.MareuViewModelFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private ActivityMainBinding binding;
    private MeetingsViewModel mViewModel;
    private MareuViewModelFactory mMaReuViewModelFactory;
    private MareuRepository mMareuRepository;
    private List<String> mMeetingRooms;
    private final String LIST_KEY="meetings room";
    private String mRoom;
    private LocalDate mDate;
    FloatingActionButton fab;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setSupportActionBar(binding.toolbar.getRoot());
        mMareuRepository = MareuInjection.createMareuRepository();
        mMeetingRooms = mMareuRepository.getMeetingRooms();
        mMaReuViewModelFactory = new MareuViewModelFactory(mMareuRepository);
        mViewModel = new ViewModelProvider(this,mMaReuViewModelFactory).get(MeetingsViewModel.class);


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
     *
     * @return the instance of the factory
     */
    public MareuViewModelFactory getMyFactory(){
        return mMaReuViewModelFactory;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar, menu);
        return true;
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        mDate = LocalDate.of(year, month+1, dayOfMonth);
        mViewModel.loadLiveListMeeting(mRoom,mDate);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.filter_by_Date){
            DialogFragment datePicker = new datePickerFragment();
            datePicker.show(getSupportFragmentManager(), "date picker");
            return true;
        }else if (item.getItemId() == R.id.filter_by_room) {
            Bundle bundle = new Bundle();
            bundle.putStringArrayList(LIST_KEY, (ArrayList<String>) mMeetingRooms);
            DialogFragment roomPicker = new RoomPickerFragment();
            roomPicker.setArguments(bundle);
            roomPicker.show(getSupportFragmentManager(), "room picker");
            Log.d(TAG, "filtering begins with launching dialog room picker: is triggered in Main Activity");
            return true;
        }else if(item.getItemId() == R.id.item_remove_filter){
            mRoom = null;
            mDate = null;
            mViewModel.loadLiveListMeeting(mRoom, mDate);
            return true;
        }else
        return super.onOptionsItemSelected(item);

    };
    private void configureFab(){
        fab = binding.createMeetingFab;
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: start new fragment");
                //Fragment fragment = CreateMeetingFragment.newInstance();
                getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_view, CreateMeetingFragment.class, null)
                    //.addToBackStack(null)
                    .commit();
            }
        });
    }



}