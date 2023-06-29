package com.artus.mareu.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;

import com.artus.mareu.repository.MareuRepository;
import com.artus.mareu.ui.meetings_list.MeetingsViewModel;

public class MaReuViewModelFactory implements ViewModelProvider.Factory {

    private final MareuRepository mMareuRepository;

    public MaReuViewModelFactory(MareuRepository mareuRepository) {
        mMareuRepository = mareuRepository;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MeetingsViewModel(mMareuRepository);
    }
}
