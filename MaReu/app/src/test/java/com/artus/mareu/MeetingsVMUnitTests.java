package com.artus.mareu;

import androidx.lifecycle.MutableLiveData;

import com.artus.mareu.model.Meeting;
import com.artus.mareu.repository.MareuRepository;
import com.artus.mareu.ui.meetings_list.ViewModels.MeetingsViewModel;

import org.junit.Before;
import org.junit.Test;
import org.threeten.bp.LocalDate;

import java.util.List;

public class MeetingsVMUnitTests {

    private MareuRepository mRepository;
    private MeetingsViewModel mMeetingsViewModel;
    private LocalDate date;
    private String room;
    private MutableLiveData<List<Meeting>> mLiveListMeeting;


    @Before
    public void setup(){
        mRepository = MareuRepository.getInstance();
        mMeetingsViewModel= new MeetingsViewModel(mRepository);
        mLiveListMeeting = new MutableLiveData<>();
    }
    @Test

    public void getLiveDataWithSuccess(){


    }



}
