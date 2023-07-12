package com.artus.mareu.ui.meetings_list;

import static org.greenrobot.eventbus.EventBus.TAG;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
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
import com.artus.mareu.di.MareuInjection;
import com.artus.mareu.events.DeleteMeetingEvent;
import com.artus.mareu.events.FilterByRoomEvent;
import com.artus.mareu.model.Meeting;
import com.artus.mareu.databinding.FragmentMeetingsBinding;
import com.artus.mareu.repository.MareuRepository;
import com.artus.mareu.ui.meetings_list.Pickers.RoomPickerFragment;
import com.artus.mareu.ui.meetings_list.Pickers.datePickerFragment;
import com.artus.mareu.ui.meetings_list.ViewModels.MeetingsViewModel;
import com.artus.mareu.utils.MareuViewModelFactory;

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
    private Toolbar toolbar;


    public static MeetingsFragment newInstance() {
        return new MeetingsFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMeetingsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        //connection / creation du viewModel (shared with the main activity)
        mMareuRepository = MareuInjection.createMareuRepository();
        mMaReuViewModelFactory = new MareuViewModelFactory(mMareuRepository);
        mViewModel = new ViewModelProvider(requireActivity(),mMaReuViewModelFactory).get(MeetingsViewModel.class);
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
        mRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));


        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar = ((MainActivity) requireActivity()).getBinding().toolbar.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_toolbar, menu);
        super.onCreateOptionsMenu(menu, inflater);
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
            datePicker.show(getChildFragmentManager(), "date picker");
            return true;
        }else if (item.getItemId() == R.id.filter_by_room) {
            Bundle bundle = new Bundle();
            bundle.putStringArrayList(LIST_KEY, (ArrayList<String>) mMeetingRooms);
            DialogFragment roomPicker = new RoomPickerFragment();
            roomPicker.setArguments(bundle);
            roomPicker.show(getChildFragmentManager(), "room picker");
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
    @Override
    public void onResume() {
        super.onResume();
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

    @Subscribe
    public void onDeleteMeeting (DeleteMeetingEvent event){
        mViewModel.deleteThisMeeting(event.mMeeting);
        Log.d(TAG, "onDeleteMeeting: is triggered in fragment ");
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