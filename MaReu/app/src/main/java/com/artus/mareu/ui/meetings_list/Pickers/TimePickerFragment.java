package com.artus.mareus.ui.meetings_list.Pickers;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.threeten.bp.LocalTime;

public class TimePickerFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LocalTime timeNow = LocalTime.now();
        int hours = timeNow.getHour();
        int minutes = timeNow.getMinute();

        return new TimePickerDialog(getActivity(), (TimePickerDialog.OnTimeSetListener) getParentFragment(), hours, minutes,true);
    }
}
