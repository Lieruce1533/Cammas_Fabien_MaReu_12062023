package com.artus.mareu.ui.meetings_list;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.artus.mareu.model.Meeting;
import com.artus.mareu.DataSource.MareuApiService;
import com.example.mareu.databinding.FragmentMeetingsBinding;

import java.util.List;
import java.util.Objects;

public class MeetingsFragment extends Fragment {

    private MeetingsViewModel mViewModel;
    private FragmentMeetingsBinding binding;
    private RecyclerView mRecyclerView;
    private MeetingAdapter mAdapter;
    private List<Meeting> mMeetingList;


    public static MeetingsFragment newInstance() {
        return new MeetingsFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMeetingsBinding.inflate(inflater, container, false);
        View view =binding.getRoot();

        //connection to viewModel
        mViewModel = new ViewModelProvider(this).get(MeetingsViewModel.class);
        mViewModel.getLiveListMeeting().observe(getViewLifecycleOwner(), new Observer<List<Meeting>>() {
            @Override
            public void onChanged(List<Meeting> meetings) {
                mAdapter.notifyDataSetChanged();
            }
        });

        mRecyclerView = binding.recyclerViewMeetings;
        mRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        mAdapter = new MeetingAdapter(mViewModel.getLiveListMeeting().getValue());
        // Set CustomAdapter as the adapter for RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        // END_INCLUDE(initializeRecyclerView)
        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}