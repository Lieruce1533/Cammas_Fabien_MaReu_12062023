package com.artus.mareu;

import org.junit.Before;
import org.junit.Test;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;

import static org.junit.Assert.*;

import com.artus.mareu.DataSource.MeetingGenerator;
import com.artus.mareu.model.Meeting;
import com.artus.mareu.repository.MareuRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;


public class RepositoryUnitTest {

    private MareuRepository mRepository;
    private LocalDate date;
    private String room;
    private int defaultListSize= 8;

    @Before
    public void setup(){ mRepository = MareuRepository.getNewInstance();}



    @Test
    public void getMeetingsWithSuccess(){
        //getting the full list of meeting without filtering
        List<Meeting> meetings = mRepository.getMeetings(null, null);
        List<Meeting> expectedMeetings = MeetingGenerator.PLANIFIED_MEETINGS;
        assertEquals(expectedMeetings, meetings);
        //getting the list of meetings filtered by room (Donut)
        room = "Donut";
        List<Meeting> roomMeetings = mRepository.getMeetings(room, null);
        List<Meeting> expectedFilteredMeetings = StreamSupport.stream(MeetingGenerator.PLANIFIED_MEETINGS).filter(p -> p.getLocation().equals(room)).collect(Collectors.toList());
        assertEquals(expectedFilteredMeetings, roomMeetings);
        //getting the list of meetings filtered by date (2023/7/12)
        date = LocalDate.of(2023,7,12);
        List<Meeting> dateMeetings = mRepository.getMeetings(null, date);
        List<Meeting> expectedFilteredDateMeetings = StreamSupport.stream(MeetingGenerator.PLANIFIED_MEETINGS).filter(p -> p.getDateTimeMeeting().toLocalDate().equals(date)).collect(Collectors.toList());
        assertEquals(expectedFilteredDateMeetings, dateMeetings);
    }
    @Test
    public void gettingMeetingRoomsWithSuccess(){
        List<String> meetingRooms = mRepository.getMeetingRooms();
        List<String> expectedMeetingRooms = MeetingGenerator.DROIDNA_MEETING_ROOMS;
        assertEquals(expectedMeetingRooms,meetingRooms);
    }

    @Test
    public void deletingMeetingWithSuccess(){
        List<Meeting> meetings = mRepository.getMeetings(null, null);
        Meeting meetingToDelete = meetings.get(1);
        assertEquals(defaultListSize, meetings.size());
        mRepository.deleteMeeting(meetingToDelete);
        assertEquals(defaultListSize -1, meetings.size());
        assertFalse(meetings.contains(meetingToDelete));
    }

    @Test
    public void createMeetingWithSuccess(){
        Meeting meetingToAdd = new Meeting(9, "le maillon de la blockChain",
                LocalDateTime.of(2023, 7,18, 10, 0),"Donut",new ArrayList<>(Arrays.asList("edouard@caramail.fr", "gontrand@lycos.fr","charles-kévin@lycos.fr")));
        List<Meeting> meetings = mRepository.getMeetings(null, null);
        assertEquals(defaultListSize, meetings.size());
        assertFalse(meetings.contains(meetingToAdd));
        mRepository.createMeeting(meetingToAdd);
        assertEquals(defaultListSize +1, meetings.size());
        assertTrue(meetings.contains(meetingToAdd));
    }

    @Test

    public void getAvailableRoomWithSuccess(){
        List<Meeting> meetings = mRepository.getMeetings(null, null);
        LocalDateTime localDateTime = LocalDateTime.of(2023,7,12,10,30);
        List<String> availableRoom = mRepository.getAvailableRooms(localDateTime, meetings);
        List<String> expectedAvailableRoom = Arrays.asList("Eclair", "Froyo","Ice Cream Sandwich", "Jelly Bean", "Kit Kat", "Lollipop");
        assertEquals(expectedAvailableRoom, availableRoom);
    }



}