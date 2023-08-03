package com.artus.mareus.DataSource;

import com.artus.mareus.model.Meeting;

import java.util.List;

public class MareuApiService implements MeetingApiService {

    List<Meeting> meetings = MeetingGenerator.generateMeetings();
    List<String> meetingRooms = MeetingGenerator.generateMeetingRooms();

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Meeting> getMeetings() {
        return meetings;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getMeetingRooms() {
        return meetingRooms;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteMeeting(Meeting meeting) { meetings.remove(meeting);}

    /**
     * {@inheritDoc}
     */
    @Override
    public void createMeeting(Meeting meeting) {
        meetings.add(meeting);

    }
}
