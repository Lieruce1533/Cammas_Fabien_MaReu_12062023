package com.artus.mareu.ui.meetings_list;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mareu.R;
import com.example.mareu.databinding.FragmentMeetingsBinding;

public class MeetingsFragment extends Fragment {

    private MeetingsViewModel mViewModel;
    private FragmentMeetingsBinding binding;
    private RecyclerView mRecyclerView;


    public static MeetingsFragment newInstance() {
        return new MeetingsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meetings, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MeetingsViewModel.class);
        // TODO: Use the ViewModel
    }

}