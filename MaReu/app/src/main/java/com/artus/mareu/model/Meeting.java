package com.artus.mareu.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Meeting {

    /** Identifier */
    private long id;

    /** Title */
    private String title;

    /** Date*/
    private String dateMeeting;

    /** Hour*/
    private String hourMeeting;

    /** Location*/
    private String location;

    /** Participants of the Meeting*/
    private List<String> participantsList = new ArrayList<>();

    /**
     * Constructor
     * @param id
     * @param title
     * @param dateMeeting
     * @param hourMeeting
     * @param location
     * @param participantsList
     */

    public Meeting(long id, String title, String dateMeeting, String hourMeeting, String location, List<String> participantsList) {
        this.id = id;
        this.title = title;
        this.dateMeeting = dateMeeting;
        this.hourMeeting = hourMeeting;
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

    public String getDateMeeting() {
        return dateMeeting;
    }

    public void setDateMeeting(String dateMeeting) {
        this.dateMeeting = dateMeeting;
    }

    public String getHourMeeting() {
        return hourMeeting;
    }

    public void setHourMeeting(String hourMeeting) {
        this.hourMeeting = hourMeeting;
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
