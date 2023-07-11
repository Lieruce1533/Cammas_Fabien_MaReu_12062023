package com.artus.mareu.ui.meetings_list;

import androidx.lifecycle.ViewModel;

import com.artus.mareu.repository.MareuRepository;

import org.threeten.bp.LocalDate;

public class CreateMeetingViewModel extends ViewModel {

    MareuRepository mMareuRepository;
    private String room;
    private LocalDate date;

    public CreateMeetingViewModel(MareuRepository mareuRepository) {
        mMareuRepository = mareuRepository;
    }


// TODO: Implement the ViewModel
}