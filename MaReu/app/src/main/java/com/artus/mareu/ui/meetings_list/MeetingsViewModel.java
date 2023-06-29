package com.artus.mareu.ui.meetings_list;


import android.app.Application;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.artus.mareu.di.RepositoryInjection;
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



    // TODO: Implement the ViewModel

}
