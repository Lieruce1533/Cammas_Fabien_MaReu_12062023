package com.artus.mareu.DataSource;

import com.artus.mareu.model.Meeting;

import java.util.List;

public interface MeetingApiService {

    /**
     * get all of the meetings
     * @return {@link List}
     */
    List<Meeting> getMeetings();

    /**
     * get all of the meeting rooms
     * @return {@link List}
     */
    List<String> getMeetingRooms();

    /**
     * Deletes a meeting
     * @param meeting
     */
    void deleteMeeting(Meeting meeting);

    /**
     * creates a meeting
     * @param meeting
     */
    void createMeeting(Meeting meeting);




}
