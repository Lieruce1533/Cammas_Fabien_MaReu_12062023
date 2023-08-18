package com.artus.mareu.ui.meetings_list;

import static org.greenrobot.eventbus.EventBus.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.artus.mareu.R;
import com.artus.mareu.events.DeleteMeetingEvent;
import com.artus.mareu.events.FilterByRoomEvent;
import com.artus.mareu.model.Meeting;
import com.artus.mareu.databinding.FragmentMeetingsBinding;
import com.artus.mareu.repository.MareuRepository;
import com.artus.mareu.ui.meetings_list.Pickers.RoomPickerFragment;
import com.artus.mareu.ui.meetings_list.Pickers.datePickerFragment;
import com.artus.mareu.ui.meetings_list.ViewModels.MeetingsViewModel;
import com.artus.mareu.utils.MareuViewModelFactory;
import com.artus.mareu.utils.ToolbarMenuManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.List;

public class MeetingsFragment extends Fragment implements DatePickerDialog.OnDateSetListener{

    private MeetingsViewModel mViewModel;
    private MareuRepository mMareuRepository;
    private FragmentMeetingsBinding binding;
    private RecyclerView mRecyclerView;
    private MeetingAdapter mAdapter;
    private List<Meeting> mMeetingList;
    private LocalDate mDate;
    private final String LIST_KEY="meetings room";
    private String mRoom;
    private List<String> mMeetingRooms;
    private MareuViewModelFactory mMaReuViewModelFactory;
    private MenuHost menuHost;
    private FloatingActionButton fab;
    private Toolbar toolbar;
    private ToolbarMenuManager toolbarManager;


    public static MeetingsFragment newInstance() {
        return new MeetingsFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMeetingsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        //connection / creation du viewModel (shared with the main activity)
        mMaReuViewModelFactory = new MareuViewModelFactory();
        mViewModel = new ViewModelProvider(requireActivity(),mMaReuViewModelFactory).get(MeetingsViewModel.class);
        mMareuRepository = MareuRepository.getInstance();
        mMeetingRooms = mMareuRepository.getMeetingRooms();
        //my observer
        final Observer<List<Meeting>> listObserver = new Observer<List<Meeting>>() {
            @Override
            public void onChanged(List<Meeting> meetings) {
                mAdapter = new MeetingAdapter(meetings);
                mRecyclerView.setAdapter(mAdapter);
            }
        };
        mViewModel.getLiveListMeeting().observe(getViewLifecycleOwner(), listObserver);
        mRecyclerView = binding.recyclerViewMeetings;
        setHomeMenuProvider();
        return view;
    }
    /**
     * management of the menu in the toolbar, and menu actions
     */

    private void setHomeMenuProvider(){
        menuHost = requireActivity();
        menuHost.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.menu_toolbar, menu);
            }
            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.filter_by_Date){
                    DialogFragment datePicker = new datePickerFragment();
                    datePicker.show(getChildFragmentManager(), "date picker");
                    return true;
                }else if (menuItem.getItemId() == R.id.filter_by_room) {
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList(LIST_KEY, (ArrayList<String>) mMeetingRooms);
                    DialogFragment roomPicker = new RoomPickerFragment();
                    roomPicker.setArguments(bundle);
                    roomPicker.show(getChildFragmentManager(), "room picker");
                    return true;
                }else if(menuItem.getItemId() == R.id.item_remove_filter){
                    mRoom = null;
                    mDate = null;
                    mViewModel.loadLiveListMeeting(mRoom, mDate);
                    return true;
                }else
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }



    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        mDate = LocalDate.of(year, month+1, dayOfMonth);
        mViewModel.loadLiveListMeeting(mRoom,mDate);
    }
    @Override
    public void onResume() {
        super.onResume();
        mViewModel.loadLiveListMeeting(null,null);
        fab = ((MainActivity) requireActivity()).getBinding().createMeetingFab;
        fab.setVisibility(View.VISIBLE);
    }
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }
    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
    /**
     * deletion of a meeting with confirmation.
     * @param event
     */
    @Subscribe
    public void onDeleteMeeting (DeleteMeetingEvent event){
        Activity activity = getActivity();
        AlertDialog.Builder confirmDeletion = new AlertDialog.Builder(activity);
        confirmDeletion.setTitle("Delete this Meeting");
        confirmDeletion.setMessage("Are you sure you want to delete the meeting entitled : "+ event.mMeeting.getTitle()+" ?");
        confirmDeletion.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mViewModel.deleteThisMeeting(event.mMeeting);
                Log.d(TAG, "onDeleteMeeting: is triggered in fragment ");
                dialog.dismiss();
            }
        });
        confirmDeletion.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        confirmDeletion.show();
    }
    @Subscribe
    public void onFilterMeetingRoom(FilterByRoomEvent event){
        mViewModel.loadLiveListMeeting(event.room, mDate);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}