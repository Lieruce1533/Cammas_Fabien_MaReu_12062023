package com.artus.mareu.ui.meetings_list;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.artus.mareu.events.FilterByRoomEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class RoomPickerFragment extends DialogFragment {

    private final String LIST_KEY="meetings room";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        assert getArguments() != null;
        //List<String> listMeetingRooms = getArguments().getStringArrayList(LIST_KEY);
        String [] meetingRooms = getArguments().getStringArrayList(LIST_KEY).toArray(new String[0]);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle( "Choose a Meeting room");
        builder.setItems(meetingRooms, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EventBus.getDefault().post(new FilterByRoomEvent(meetingRooms[which]));
            }
        });


        return builder.create();
    }
}
