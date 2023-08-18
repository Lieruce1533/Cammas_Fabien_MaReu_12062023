package com.artus.mareu.ui.meetings_list.Pickers;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.threeten.bp.LocalDate;

/**
 * date picker dialog fragment to pick a date when it is needed, filtering or creating a new meeting
 */
public class datePickerFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LocalDate dateNow = LocalDate.now();
        int year = dateNow.getYear();
        int month = dateNow.getMonthValue()-1;
        int day = dateNow.getDayOfMonth();
        return new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener) getParentFragment(), year, month, day);


    }
}
