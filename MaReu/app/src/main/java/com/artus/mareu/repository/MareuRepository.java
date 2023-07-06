package com.artus.mareu.repository;

import static org.greenrobot.eventbus.EventBus.TAG;

import android.util.Log;

import com.artus.mareu.model.Meeting;
import com.artus.mareu.DataSource.MeetingApiService;

import org.jetbrains.annotations.Nullable;
import org.threeten.bp.LocalDate;

import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;
import java.util.List;
import java8.util.Optional;


public class MareuRepository {

    private final MeetingApiService apiService;
    private List<Meeting> mMeetings;

    public MareuRepository(MeetingApiService apiService) {
        this.apiService = apiService;
    }

    public List<Meeting> getMeetings(String room, LocalDate date){

        if (room !=null) {
            mMeetings = StreamSupport.stream(apiService.getMeetings()).filter(p -> p.getLocation().equals(room)).collect(Collectors.toList());
            Log.d(TAG, "Filtering by room start in repository");
        } else if (date != null) {
            mMeetings = StreamSupport.stream(apiService.getMeetings()).filter(p -> p.getDateTimeMeeting().toLocalDate().equals(date)).collect(Collectors.toList());
            Log.d(TAG, "Filtering by date start in repository");
        } else { mMeetings = apiService.getMeetings();}

        Log.d(TAG, "getMeetings: is triggered in repository");

        return mMeetings;

    }

    public List<String> getMeetingRooms() {
        return apiService.getMeetingRooms();
    }

    public void deleteMeeting(Meeting meeting) { apiService.deleteMeeting(meeting);
    }

    public void createMeeting(Meeting meeting) {
        apiService.createMeeting(meeting);
    }
}
