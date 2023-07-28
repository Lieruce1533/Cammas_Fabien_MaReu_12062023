package com.artus.mareu.ui.meetings_list;

import static org.greenrobot.eventbus.EventBus.TAG;

import androidx.activity.OnBackPressedCallback;
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
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.artus.mareu.R;
import com.artus.mareu.databinding.FragmentCreateMeetingBinding;
import com.artus.mareu.events.DeleteParticipantEvent;
import com.artus.mareu.model.Meeting;
import com.artus.mareu.repository.MareuRepository;
import com.artus.mareu.ui.meetings_list.Pickers.TimePickerFragment;
import com.artus.mareu.ui.meetings_list.Pickers.datePickerFragment;
import com.artus.mareu.ui.meetings_list.ViewModels.CreateMeetingViewModel;
import com.artus.mareu.utils.MareuViewModelFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;

import java.util.ArrayList;
import java.util.List;

public class CreateMeetingFragment extends Fragment implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    private CreateMeetingViewModel mViewModel;
    private FragmentCreateMeetingBinding binding;
    public Spinner mSpinner;
    private MareuViewModelFactory mMaReuViewModelFactory;
    private List<String> mRoom;
    private MareuRepository mMareuRepository;
    private LocalDateTime mDateTime;
    private LocalDate mDate;
    private LocalTime mTime;
    private EditText mEditTitleMeeting;
    private EditText mEditDate;
    private EditText mEditTime;
    private ImageView mClock;
    private ImageView mCalendar;
    private Toolbar toolbar;
    private ArrayAdapter<String> spinnerAdapter;
    private String selectedRoom;
    private ImageView addParticipant;
    private Button saveButton;
    private List<String> participants = new ArrayList<>();
    private RecyclerView rViewParticipants;
    private ParticipantsAdapter adapter;



    public static CreateMeetingFragment newInstance() {
        return new CreateMeetingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateMeetingBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        mSpinner = binding.spinnerRoom;
        mMaReuViewModelFactory = new MareuViewModelFactory();
        mViewModel = new ViewModelProvider(requireActivity(), mMaReuViewModelFactory).get(CreateMeetingViewModel.class);

        final Observer<Boolean> visibilityObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean visible) {
                if (visible) {
                    mSpinner.setVisibility(View.VISIBLE);
                    Log.d(TAG, "onChanged: is running visible");
                } else
                    mSpinner.setVisibility(View.GONE);

            }
        };
        // This callback will only be called when MyFragment is at least Started.
        OnBackPressedCallback callback = new OnBackPressedCallback(true ) {
            @Override
            public void handleOnBackPressed() {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStackImmediate();
                Log.d(TAG, "onBackPressed: is fired");
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

        // The callback can be enabled or disabled here or in handleOnBackPressed()
        mViewModel.getVisible().observe(getViewLifecycleOwner(), visibilityObserver);
        mSpinner.setOnItemSelectedListener(this);
        saveButton = binding.buttonAdd;
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNewMeeting();
            }
        });
        setDatePicker();
        setTimePicker();

        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe
    /**
     * here my deletion of a participant and finishing with the adapter notification
     */
    public void onDeleteParticipant(DeleteParticipantEvent event) {
        participants.remove(event.participant);
        adapter.notifyDataSetChanged();
    }
    /**
     * Here the method to add a New Participant to a meeting
     */
    public void addNewParticipant() {
        String participantToAdd = binding.textInputParticipant.getText().toString();
        if(!TextUtils.isEmpty(participantToAdd)){
        participants.add(participantToAdd);
        binding.textInputParticipant.getText().clear();
            if (participants.size() == 1) {
                adapter = new ParticipantsAdapter(participants);
                rViewParticipants = binding.recyclerViewParticipants;
                rViewParticipants.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
        }else {
            Toast toast = Toast.makeText(getActivity(), "You must enter an email address", Toast.LENGTH_LONG);
            toast.show();
        }


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
                if (menuItem.getItemId() == android.R.id.home) {
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
        toolbar.setTitle("New Meeting");
/**
 * this a native back button, I can manage to make it appear but it doesn't trigger anything
 */
        ((MainActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setMenuProvider();

        addParticipant = binding.AddParticipant;
        addParticipant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewParticipant();
            }
        });
    }

    /**
     * management of the date Input
     */
    public void setDatePicker() {
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
        mDate = LocalDate.of(year, month + 1, dayOfMonth);
        mEditDate.setText(mDate.toString());
        Log.d(TAG, "onDateSet: ");
    }
    /**
     * management of the time input
     */
    public void setTimePicker() {
        mEditTime = binding.editTextTime;
        mClock = binding.imageTime;
        mClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getChildFragmentManager(), "create time picker");
            }
        });
    }
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        mTime = LocalTime.of(hourOfDay, minute);
        mEditTime.setText(mTime.toString());
        Log.d(TAG, "onTimeSet: ");
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
     *
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
    public void UpdateSpinnerList() {
        mRoom = mViewModel.fetchFilteredRooms(fetchDateTime(), mViewModel.fetchMeetings(null, null));
        spinnerAdapter = new ArrayAdapter<>(requireContext(), R.layout.spinner_list, mRoom);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_list);
        mSpinner.setAdapter(spinnerAdapter);
        Log.d(TAG, "UpdateSpinnerList: in fragment is fired ");
    }
    /**
     * checking if all the field are filled before creating a new meeting
     */
    public boolean inputsFilled(){
        mEditTitleMeeting= binding.editTextTitle;
        if((TextUtils.isEmpty(mEditTitleMeeting.getText())) || (mSpinner.getVisibility() == View.GONE) || (participants.size() < 1)){
            Toast toast = Toast.makeText(getActivity(), "You must fill all the fields", Toast.LENGTH_LONG);
            toast.show();
            return false;
        } else {
            return true;
        }
    }

    /**
     * creating and adding new meeting via view model
     */
    public void saveNewMeeting(){
        Meeting meetingToCreate;
        Long refId = mViewModel.fetchMeetings(null,null).get(mViewModel.fetchMeetings(null,null).size() -1).getId();
        if (inputsFilled()){
            meetingToCreate = new Meeting((refId +1), mEditTitleMeeting.getText().toString(),fetchDateTime(),selectedRoom,participants);
            mViewModel.addMeeting(meetingToCreate);
            Log.d(TAG, "saveNewMeeting: as run");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedRoom = parent.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}

