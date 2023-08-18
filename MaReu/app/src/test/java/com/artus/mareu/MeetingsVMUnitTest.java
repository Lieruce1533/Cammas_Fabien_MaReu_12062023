package com.artus.mareu;

import static org.junit.Assert.assertEquals;

import androidx.lifecycle.MutableLiveData;

import com.artus.mareu.DataSource.MeetingGenerator;
import com.artus.mareu.model.Meeting;
import com.artus.mareu.repository.MareuRepository;
import com.artus.mareu.ui.meetings_list.ViewModels.MeetingsViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.threeten.bp.LocalDate;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.threeten.bp.LocalDateTime;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MeetingsVMUnitTest {

    private LocalDate date;
    private String room;
    private MutableLiveData<List<Meeting>> mLiveListMeeting;
    private List<Meeting> expectedMeetings;
    private static final int LIVE_ITEMS_COUNT = 8;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private MareuRepository mRepository;
    private MeetingsViewModel mMeetingsViewModel;



    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        //mRepository = MareuRepository.getNewInstance();
        mMeetingsViewModel = new MeetingsViewModel(mRepository);
    }
    @Test
    public void getLiveDataWithSuccess(){
        expectedMeetings = MeetingGenerator.PLANIFIED_MEETINGS;
        //using mock
        when(mRepository.getMeetings(room, date)).thenReturn(expectedMeetings);
        //I'll should get the full list of meetings in my live date
        mLiveListMeeting = mMeetingsViewModel.getLiveListMeeting();
        List<Meeting> actualMeetings = mLiveListMeeting.getValue();
        // Check if the actual and expected lists match
        assertEquals(expectedMeetings, actualMeetings);

    }

    @Test
    public void deleteThisMeeting_repositoryCalledAndLiveDataUpdated() {
        Meeting meetingToDelete =  new Meeting(2, "le pattern du pissenlit",
                LocalDateTime.of(2023, 7,12, 11, 0),"Donut",new ArrayList<>(Arrays.asList("patrick@caramail.fr", "gontrand@lycos.fr","francois@lycos.fr")));

        mMeetingsViewModel.deleteThisMeeting(meetingToDelete);

        // Verify that the repository's deleteMeeting method is called with the correct parameter
        verify(mRepository).deleteMeeting(meetingToDelete);

        // Verify that the repository's getMeetings method is called to refresh the data
        verify(mRepository).getMeetings(null, null);

    }

}
