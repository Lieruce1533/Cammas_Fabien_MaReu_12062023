package com.artus.mareu.model;


import org.threeten.bp.LocalDateTime;

import java.util.List;
import java.util.Objects;

public class Meeting {

    /** Identifier */
    private long id;

    /** Title */
    private String title;

    /** Date*/
    private LocalDateTime dateTimeMeeting;

    /** Location*/
    private String location;

    /** Participants of the Meeting*/
    private List<String> participantsList;

    /**
     * Constructor
     * @param id
     * @param title
     * @param dateTimeMeeting
     * @param location
     * @param participantsList
     */

    public Meeting(long id, String title, LocalDateTime dateTimeMeeting, String location, List<String> participantsList) {
        this.id = id;
        this.title = title;
        this.dateTimeMeeting = dateTimeMeeting;
        this.location = location;
        this.participantsList = participantsList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getDateTimeMeeting() {
        return dateTimeMeeting;
    }

    public void setDateTimeMeeting(LocalDateTime dateTimeMeeting) {
        this.dateTimeMeeting = dateTimeMeeting;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getParticipantsList() {
        return participantsList;
    }

    public void setParticipantsList(List<String> participantsList) {
        this.participantsList = participantsList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meeting meeting = (Meeting) o;
        return id == meeting.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
