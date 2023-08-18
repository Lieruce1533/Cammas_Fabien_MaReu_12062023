package com.artus.mareu.repository;

import com.artus.mareu.DataSource.MareuApiService;
import com.artus.mareu.model.Meeting;
import com.artus.mareu.DataSource.MeetingApiService;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;

import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;

import java.util.ArrayList;
import java.util.List;


public class MareuRepository {

    private static MareuRepository instance;
    private final MeetingApiService apiService;
    private List<Meeting> mMeetings;
    private List<String> roomFree;

    public MareuRepository(MeetingApiService apiService) {
        this.apiService = apiService;
    }

    /**
     * Implementation Of singleton pattern for the repository and the Apiservice.
     * @return an instance of them.
     */
    public static synchronized MareuRepository getInstance(){
        if (instance == null ){
            instance = new MareuRepository(new MareuApiService());
        }
        return instance;
    }

    /**
     * For test purposes, get a new instance each time. We are sure to work on a clean load of data.
     * @return a new instance
     */
    public static MareuRepository getNewInstance(){
        return new MareuRepository(new MareuApiService());
    }

    /**
     * to get our list of meeting with the possibility to filter by room or date
     * @param room
     * @param date
     * @return a list of meetings
     */
    public List<Meeting> getMeetings(String room, LocalDate date){

        if (room !=null) {
            mMeetings = StreamSupport.stream(apiService.getMeetings()).filter(p -> p.getLocation().equals(room)).collect(Collectors.toList());

        } else if (date != null) {
            mMeetings = StreamSupport.stream(apiService.getMeetings()).filter(p -> p.getDateTimeMeeting().toLocalDate().equals(date)).collect(Collectors.toList());

        } else { mMeetings = apiService.getMeetings();}



        return mMeetings;

    }

    /**
     * to get the list of meetings rooms
     * @return a list
     */
    public List<String> getMeetingRooms() {
        return apiService.getMeetingRooms();
    }

    /**
     * to
     * @param meeting
     */
    public void deleteMeeting(Meeting meeting) { apiService.deleteMeeting(meeting);
    }

    public void createMeeting(Meeting meeting) {
        apiService.createMeeting(meeting);
    }

    /**
     * returning a list of meeting rooms free for given localDateTime
     */
    public List<String> getAvailableRooms(LocalDateTime dateTime, List<Meeting> mMeetings ){
        List<String> fullRoomList = getMeetingRooms();
        List<String> occupiedRooms = new ArrayList<>();
        for (Meeting meeting : mMeetings) {
            LocalDateTime startDateTime = meeting.getDateTimeMeeting();
            LocalDateTime endDateTime = startDateTime.plusHours(1);
            // Assuming each meeting lasts for 1 hour and addition a gap of 5 min between 2 meetings
            // Check if the provided dateTime overlaps with the existing meeting
            if (dateTime.isBefore(endDateTime.plusMinutes(5)) && dateTime.isAfter(startDateTime.minusMinutes(65))){
                occupiedRooms.add(meeting.getLocation());
            }
        }
        fullRoomList.removeAll(occupiedRooms);


        return fullRoomList;
    };
}
