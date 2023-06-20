package com.artus.mareu.repository;

import com.artus.mareu.model.Meeting;
import com.artus.mareu.service.MeetingApiService;

import java.util.List;

public class MareuRepository {

    private final MeetingApiService apiService;

    public MareuRepository(MeetingApiService apiService) {
        this.apiService = apiService;
    }

    public List<Meeting> getMeetings(){
        return apiService.getMeetings();
    }

    public List<String> getMeetingRooms() {
        return apiService.getMeetingRooms();
    }

    public void deleteMeeting(Meeting meeting) {
        apiService.deleteMeeting(meeting);
    }

    public void createMeeting(Meeting meeting) {
        apiService.createMeeting(meeting);
    }
}
