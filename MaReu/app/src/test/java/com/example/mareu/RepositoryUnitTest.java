package com.example.mareu;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.threeten.bp.LocalDate;

import static org.junit.Assert.*;

import com.artus.mareu.DataSource.MareuApiService;
import com.artus.mareu.DataSource.MeetingApiService;
import com.artus.mareu.DataSource.MeetingGenerator;
import com.artus.mareu.di.MareuInjection;
import com.artus.mareu.model.Meeting;
import com.artus.mareu.repository.MareuRepository;

import java.util.List;

import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class RepositoryUnitTest {

    private MareuRepository mRepository;
    private LocalDate date;
    private String room;

    @Before
    public void setup(){ mRepository = MareuInjection.createMareuRepository();}


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
        assertEquals(expectedFilteredMeetings.size(), roomMeetings.size());
        //getting the list of meetings filtered by date (2023/7/12)
        date = LocalDate.of(2023,7,12);

    }



}