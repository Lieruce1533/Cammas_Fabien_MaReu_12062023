package com.artus.mareu.ui.meetings_list.ViewModels;


import static org.greenrobot.eventbus.EventBus.TAG;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.artus.mareu.model.Meeting;
import com.artus.mareu.repository.MareuRepository;
import org.threeten.bp.LocalDate;
import java.util.List;

public class MeetingsViewModel extends ViewModel {

    private MareuRepository mMareuRepository;
    private String room;
    private LocalDate date;
    private MutableLiveData<List<Meeting>> liveListMeeting = new MutableLiveData<List<Meeting>>();

    public MeetingsViewModel(MareuRepository mareuRepository) {
        mMareuRepository = mareuRepository;
    }

    public MutableLiveData<List<Meeting>> getLiveListMeeting() {

            loadLiveListMeeting(room, date);

        return liveListMeeting;
    }

    public void loadLiveListMeeting(String room, LocalDate date) {
            List<Meeting> listMeeting = mMareuRepository.getMeetings(room, date);
            liveListMeeting.setValue(listMeeting);
    }

    /**
     *
     * @param meeting
     */
    public void deleteThisMeeting(Meeting meeting){
       mMareuRepository.deleteMeeting(meeting);
       loadLiveListMeeting(room, date);
    }

}
