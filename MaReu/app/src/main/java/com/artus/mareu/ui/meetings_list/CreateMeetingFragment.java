package com.artus.mareu.ui.meetings_list;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.artus.mareu.R;
import com.artus.mareu.databinding.FragmentCreateMeetingBinding;
import com.artus.mareu.databinding.FragmentMeetingsBinding;

public class CreateMeetingFragment extends Fragment {

    private CreateMeetingViewModel mViewModel;
    private FragmentCreateMeetingBinding binding;


    public static CreateMeetingFragment newInstance() {
        return new CreateMeetingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateMeetingBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



}