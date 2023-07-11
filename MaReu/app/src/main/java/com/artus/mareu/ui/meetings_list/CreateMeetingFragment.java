package com.artus.mareu.ui.meetings_list;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.artus.mareu.R;
import com.artus.mareu.databinding.FragmentCreateMeetingBinding;
import com.artus.mareu.databinding.FragmentMeetingsBinding;
import com.artus.mareu.di.MareuInjection;
import com.artus.mareu.repository.MareuRepository;
import com.artus.mareu.utils.MareuViewModelFactory;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;

import java.util.List;

public class CreateMeetingFragment extends Fragment implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private CreateMeetingViewModel mViewModel;
    private FragmentCreateMeetingBinding binding;
    private Spinner mSpinner;
    private MareuViewModelFactory mMaReuViewModelFactory;
    private List<String> mRoom;
    private MareuRepository mMareuRepository;
    private LocalDateTime mDateTime;
    private LocalDate mDate;
    private LocalTime mTime;
    private EditText mEditDate;
    private EditText mEditTime;
    private ImageView mClock;
    private ImageView mCalendar;



    public static CreateMeetingFragment newInstance() {
        return new CreateMeetingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateMeetingBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        mSpinner = binding.spinnerRoom;
        /**
         * for the purpose of testing I populate with the full list of meeting Rooms.
         */
        mMareuRepository = MareuInjection.createMareuRepository();
        mRoom = mMareuRepository.getMeetingRooms();
        mSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter(requireContext(), R.layout.spinner_list, mRoom);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_list);
        mSpinner.setAdapter(spinnerAdapter);
        setDatePicker();
        setTimePicker();

        return view;
    }
    public void setDatePicker(){
        mEditDate = binding.editTextDate;
        mCalendar = binding.imageDate;
        mCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new datePickerFragment();
                datePicker.show(getChildFragmentManager(), " create date picker");
            }
        });
    }
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        mDate = LocalDate.of(year, month+1, dayOfMonth);
        mEditDate.setText(mDate.toString());
    }
    public void setTimePicker(){
        mEditTime = binding.editTextTime;
        mClock = binding.imageTime;
        mClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getChildFragmentManager(),"create time picker");
            }
        });
    }
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        mTime = LocalTime.of(hourOfDay,minute);
        mEditTime.setText(mTime.toString());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}