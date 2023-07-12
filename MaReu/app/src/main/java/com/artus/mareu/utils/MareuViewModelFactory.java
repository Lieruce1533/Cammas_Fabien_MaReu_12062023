package com.artus.mareu.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.artus.mareu.repository.MareuRepository;
import com.artus.mareu.ui.meetings_list.ViewModels.CreateMeetingViewModel;
import com.artus.mareu.ui.meetings_list.ViewModels.MeetingsViewModel;

public class MareuViewModelFactory implements ViewModelProvider.Factory {

    private final MareuRepository mMareuRepository;

    public MareuViewModelFactory(MareuRepository mareuRepository) {
        this.mMareuRepository = mareuRepository;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(MeetingsViewModel.class)){
        return (T) new MeetingsViewModel(mMareuRepository);
    } else if (modelClass.isAssignableFrom(CreateMeetingViewModel.class)) {
            return (T) new CreateMeetingViewModel(mMareuRepository);
        }else {
            throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
        }

    }
}
