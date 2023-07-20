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
    private MutableLiveData<Boolean> visible =new MutableLiveData<>();

    public CreateMeetingViewModel(MareuRepository mareuRepository) {
        mMareuRepository = mareuRepository;
    }

    public MutableLiveData<Boolean> getVisible(){
        displayRoomSpinner(display);
        return visible;
    };

    /**
     * changing the value of our boolean live data
     * @return
     */
    public void displayRoomSpinner(boolean display){
        visible.setValue(!display);
            Log.d(TAG, "displayRoomSpinner: is running ");
    }



    public List<Meeting> fetchMeetings(String room, LocalDate date){
        mMeetings = mMareuRepository.getMeetings(room,date);
        return mMeetings;
    }

    public List<String> fetchFilteredRooms(LocalDateTime mDateTime, List<Meeting> meetings){
        availableRooms = mMareuRepository.getAvailableRooms(mDateTime,meetings);
        displayRoomSpinner(false);
        return availableRooms;
    }

}