package com.artus.mareu.ui.meetings_list;

import static org.greenrobot.eventbus.EventBus.TAG;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.artus.mareu.di.MareuInjection;
import com.artus.mareu.repository.MareuRepository;
import com.artus.mareu.ui.meetings_list.Pickers.TimePickerFragment;
import com.artus.mareu.ui.meetings_list.Pickers.datePickerFragment;
import com.artus.mareu.ui.meetings_list.ViewModels.CreateMeetingViewModel;
import com.artus.mareu.ui.meetings_list.ViewModels.MeetingsViewModel;
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
    private Toolbar toolbar;
    private ArrayAdapter<String> spinnerAdapter;



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
        mMaReuViewModelFactory = new MareuViewModelFactory(mMareuRepository);
        mViewModel = new ViewModelProvider(requireActivity(),mMaReuViewModelFactory).get(CreateMeetingViewModel.class);

        final Observer<Boolean> visibilityObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean ){
                    mSpinner.setVisibility(View.VISIBLE);
                    spinnerAdapter.clear();
                    spinnerAdapter.addAll(mRoom);
                    spinnerAdapter.notifyDataSetChanged();
                }else {
                    mSpinner.setVisibility(View.GONE);

                }
            }
        };
        mViewModel.getVisible().observe(getViewLifecycleOwner(), visibilityObserver);
        mSpinner.setOnItemSelectedListener(this);
        mRoom = mMareuRepository.getMeetingRooms();
        spinnerAdapter = new ArrayAdapter(requireContext(), R.layout.spinner_list, mRoom);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_list);
        mSpinner.setAdapter(spinnerAdapter);

        setDatePicker();
        setTimePicker();

        return view;
    }

    /**
     * management of the menu and the toolbar.
     */
    private void setMenuProvider() {
        MenuHost menuHost = requireActivity();
        menuHost.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.menu_toolbar_create, menu);
            }
            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.item_nav_back) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    Log.d(TAG, "onMenuItemSelected: fragment manager created");
                    fm.popBackStackImmediate();
                    Log.d(TAG, "onMenuItemSelected: fm pop fired");
                    return true;
                }
                return true;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.CREATED);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar = ((MainActivity) requireActivity()).getBinding().toolbar.getRoot();
        ((MainActivity) requireActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("New Meeting");
        setMenuProvider();
    }

    /**
     * management of the date Input
     */
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
            Log.d(TAG, "onDateSet: ");
        //checkFilledInputs();

    }
    /**
     * management of the time input
     */
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
        checkFilledInputs();
    }
    /**
     * control if a date and a time have been set by the user
     */

    public void checkFilledInputs() {
        if ((mEditDate.getText() != null) && (mEditTime.getText() != null)) {
            Log.d(TAG, "checkFilledInputs: launched");
            UpdateSpinnerList();
        }

    }

    /**
     * A way to combine the date input and the time input
     * @return a LocalDateTime object
     */
    public LocalDateTime fetchDateTime() {

        mDate = LocalDate.parse(mEditDate.getText().toString());
        mTime = LocalTime.parse(mEditTime.getText().toString());
        mDateTime = LocalDateTime.of(mDate, mTime);
        return mDateTime;
    }

    /**
     * updating the spinner list to propose only the rooms available at a specific LocalDateTime
     */
    public void UpdateSpinnerList(){
        mRoom = mViewModel.fetchFilteredRooms(fetchDateTime(),mViewModel.fetchMeetings(null, null));
        Log.d(TAG, "UpdateSpinnerList: is fired ");

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