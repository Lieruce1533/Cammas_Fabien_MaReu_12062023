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

    private String room;
    private LocalDate date;

    private List<String> availableRooms;

    private List<Meeting> mMeetings;
    private MutableLiveData<Boolean> visible =new MutableLiveData<>();

    public CreateMeetingViewModel(MareuRepository mareuRepository) {
        mMareuRepository = mareuRepository;
    }

    public MutableLiveData<Boolean> getVisible(){
        displayRoomSpinner(false);
        return visible;
    };

    /**
     * changing the value of our boolean live data
     * @return
     */
    public void displayRoomSpinner(boolean display){
        if (display) {
            visible.setValue(true);
            Log.d(TAG, "displayRoomSpinner: is running ");
        } else {
            visible.setValue(false);
        }
    }



    public List<Meeting> fetchMeetings(String room, LocalDate date){
        mMeetings = mMareuRepository.getMeetings(room,date);
        return mMeetings;
    }

    public List<String> fetchFilteredRooms(LocalDateTime mDateTime, List<Meeting> meetings){
        availableRooms = mMareuRepository.getAvailableRooms(mDateTime,meetings);
        displayRoomSpinner(true);
        return availableRooms;
    }
    // TODO: Implement the ViewModel
}