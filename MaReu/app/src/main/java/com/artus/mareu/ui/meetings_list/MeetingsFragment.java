package com.artus.mareu.ui.meetings_list;

import static org.greenrobot.eventbus.EventBus.TAG;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.artus.mareu.events.DeleteMeetingEvent;
import com.artus.mareu.events.FilterByRoomEvent;
import com.artus.mareu.model.Meeting;
import com.artus.mareu.databinding.FragmentMeetingsBinding;
import com.artus.mareu.utils.MareuViewModelFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.threeten.bp.LocalDate;

import java.util.List;

public class MeetingsFragment extends Fragment {

    private MeetingsViewModel mViewModel;

    private FragmentMeetingsBinding binding;
    private RecyclerView mRecyclerView;
    private MeetingAdapter mAdapter;
    private List<Meeting> mMeetingList;
    private LocalDate mDate;
    private MareuViewModelFactory mMaReuViewModelFactory;


    public static MeetingsFragment newInstance() {
        return new MeetingsFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMeetingsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        //connection / creation du viewModel (shared with the main activity)

        mMaReuViewModelFactory = ((MainActivity) requireActivity()).getMyFactory();
        mViewModel = new ViewModelProvider(requireActivity(),mMaReuViewModelFactory).get(MeetingsViewModel.class);

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
        return view;
    }

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