package com.artus.mareu.ui.meetings_list;

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
import com.artus.mareu.service.MareuApiService;
import com.example.mareu.R;
import com.example.mareu.databinding.FragmentMeetingsBinding;

import java.util.List;

public class MeetingsFragment extends Fragment {

    private MeetingsViewModel mViewModel;

    private final MareuApiService mMareuApiService = new MareuApiService();
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
        //je charge ma liste pour tester
        mMeetingList = mMareuApiService.getMeetings();
        //# bientôt elle sera fourni par mon viewModel
        mRecyclerView = binding.recyclerViewMeetings;
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mAdapter = new MeetingAdapter(mMeetingList);
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MeetingsViewModel.class);
        // TODO: Use the ViewModel
        // I must set mMeetingList here...
    }

}