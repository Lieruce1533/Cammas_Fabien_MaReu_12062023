package com.artus.mareu.ui.meetings_list;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.artus.mareu.di.RepositoryInjection;
import com.artus.mareu.model.Meeting;
import com.artus.mareu.repository.MareuRepository;
import com.artus.mareu.utils.MaReuApplication;

import org.threeten.bp.LocalDate;

import java.util.List;

public class MeetingsViewModel extends ViewModel {

    private MareuRepository mMareuRepository;
    private String room;
    private LocalDate date;
    private MutableLiveData<List<Meeting>> liveListMeeting;

    public MutableLiveData<List<Meeting>> getLiveListMeeting() {
        if (liveListMeeting == null){
            liveListMeeting = new MutableLiveData<List<Meeting>>();
            loadLiveListMeeting();
        }
        return liveListMeeting;
    }

    private void loadLiveListMeeting() {
        if(liveListMeeting != null){
            return;
        }
        mMareuRepository = RepositoryInjection.createMareuRepository();
       List<Meeting> listMeeting = mMareuRepository.getMeetings(room, date);
       liveListMeeting.setValue(listMeeting);

    }

    // TODO: Implement the ViewModel
}