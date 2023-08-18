package com.artus.mareu.ui.meetings_list.ViewModels;

import static org.greenrobot.eventbus.EventBus.TAG;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.artus.mareu.model.Meeting;
import com.artus.mareu.repository.MareuRepository;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;

import java.util.List;

public class CreateMeetingViewModel extends ViewModel {

    MareuRepository mMareuRepository;
    private List<String> availableRooms;
    private List<Meeting> mMeetings;
    private boolean display;
    private MutableLiveData<Boolean> visible = new MutableLiveData<>();

    /**
     * View model for the createMeetingFragment,it will be created by the MareuViewModelFactory
     * @param mareuRepository
     */
    public CreateMeetingViewModel(MareuRepository mareuRepository) {
        mMareuRepository = mareuRepository;
    }

    /**
     * creation of our liveData
     */
    public MutableLiveData<Boolean> getVisible() {
        displayRoomSpinner(display);
        return visible;
    }

    /**
     * changing the value of our boolean live data     *
     * @return
     */
    public void displayRoomSpinner(boolean display) {
        visible.setValue(!display);
    }

    /**
     * to get the list of meetings
     * @param room
     * @param date
     * @return a list of meetings
     */
    public List<Meeting> fetchMeetings(String room, LocalDate date) {
        mMeetings = mMareuRepository.getMeetings(room, date);
        return mMeetings;
    }

    /**
     * to get a list of meeting rooms available depending on chosen date and time. used when creating a new meeting.
     * @param mDateTime
     * @param meetings
     * @return a list of available rooms
     */
    public List<String> fetchFilteredRooms(LocalDateTime mDateTime, List<Meeting> meetings) {
        availableRooms = mMareuRepository.getAvailableRooms(mDateTime, meetings);
        displayRoomSpinner(false);
        return availableRooms;
    }

    /**
     * to add a new meeting to our list of meetings.
     * @param meeting
     */
    public void addMeeting(Meeting meeting) {
        mMareuRepository.createMeeting(meeting);
    }
}