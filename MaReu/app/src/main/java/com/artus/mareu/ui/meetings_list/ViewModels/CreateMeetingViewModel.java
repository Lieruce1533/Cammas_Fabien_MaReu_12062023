package com.artus.mareu.ui.meetings_list.ViewModels;

import androidx.lifecycle.ViewModel;

import com.artus.mareu.model.Meeting;
import com.artus.mareu.repository.MareuRepository;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;

import java.util.List;

public class CreateMeetingViewModel extends ViewModel {

    MareuRepository mMareuRepository;

    private String room;
    private LocalDate date;

    private List<String> availableRooms;

    private List<Meeting> mMeetings;

    public CreateMeetingViewModel(MareuRepository mareuRepository) {
        mMareuRepository = mareuRepository;
    }

    public List<Meeting> fetchMeetings(String room, LocalDate date){
        mMeetings = mMareuRepository.getMeetings(room,date);
        return mMeetings;
    }

    public List<String> fetchFilteredRooms(LocalDateTime mDateTime, List<Meeting> meetings){
        availableRooms = mMareuRepository.getAvailableRooms(mDateTime,meetings);
        return availableRooms;
    }
    // TODO: Implement the ViewModel
}