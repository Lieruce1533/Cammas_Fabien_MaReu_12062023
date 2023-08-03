package com.artus.mareu.events;


import com.artus.mareu.model.Meeting;

/**
 * Event fired when a user deletes a Meeting
 */
public class DeleteMeetingEvent {

    /**
     * Meeting to delete
     */
    public Meeting mMeeting;

    /**
     * Constructor.
     * @param meeting
     */
    public DeleteMeetingEvent(Meeting meeting) {
        mMeeting = meeting;
    }
}

