package com.artus.mareu.repository;

import com.artus.mareu.model.Meeting;
import com.artus.mareu.DataSource.MeetingApiService;

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
        Optional<String> theRoom = Optional.ofNullable(room);
        Optional<LocalDate> theDate = Optional.ofNullable(date);
        if (theRoom.isPresent()) {

            mMeetings = StreamSupport.stream(apiService.getMeetings()).filter(p -> p.getLocation().equals(theRoom.toString())).collect(Collectors.toList());

        } else if (theDate.isPresent()) {

            mMeetings = StreamSupport.stream(apiService.getMeetings()).filter(p -> p.getDateTimeMeeting().toLocalDate().equals(theDate)).collect(Collectors.toList());

        } else { mMeetings = apiService.getMeetings();}

        return mMeetings;

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
