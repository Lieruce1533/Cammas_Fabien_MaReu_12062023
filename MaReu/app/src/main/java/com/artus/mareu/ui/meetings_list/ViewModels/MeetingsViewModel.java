package com.artus.mareu.ui.meetings_list.ViewModels;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.artus.mareu.model.Meeting;
import com.artus.mareu.repository.MareuRepository;
import org.threeten.bp.LocalDate;
import java.util.List;

/**
 * View model for the meetingsFragment, it will be created by the MareuViewModelFactory
 */
public class MeetingsViewModel extends ViewModel {

    private MareuRepository mMareuRepository;
    private String room;
    private LocalDate date;
    private MutableLiveData<List<Meeting>> liveListMeeting = new MutableLiveData<List<Meeting>>();

    /**
     * View model for the MeetingsFragment,it will be created by the MareuViewModelFactory
     * @param mareuRepository
     */
    public MeetingsViewModel(MareuRepository mareuRepository) {
        mMareuRepository = mareuRepository;
    }

    /**
     * creation of our liveData
     */
    public MutableLiveData<List<Meeting>> getLiveListMeeting() {

            loadLiveListMeeting(room, date);

        return liveListMeeting;
    }

    /**
     * to populate and update our livedata
     * @param room
     * @param date
     */
    public void loadLiveListMeeting(String room, LocalDate date) {
            List<Meeting> listMeeting = mMareuRepository.getMeetings(room, date);
            liveListMeeting.setValue(listMeeting);
    }

    /**
     * to delete a meeting an update the livedata
     * @param meeting
     */
    public void deleteThisMeeting(Meeting meeting){
       mMareuRepository.deleteMeeting(meeting);
       loadLiveListMeeting(room, date);
    }

}
