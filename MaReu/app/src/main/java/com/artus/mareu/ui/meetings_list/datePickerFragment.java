package com.artus.mareu.ui.meetings_list;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.threeten.bp.LocalDate;

public class datePickerFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LocalDate dateNow = LocalDate.now();
        int year = dateNow.getYear();
        int month = dateNow.getMonthValue()-1;
        int day = dateNow.getDayOfMonth();
        return new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener) getActivity(), year, month, day);


    }
}
