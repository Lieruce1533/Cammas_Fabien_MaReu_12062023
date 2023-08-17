package com.artus.mareu;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.artus.mareu.DataSource.MeetingGenerator;
import com.artus.mareu.model.Meeting;
import com.artus.mareu.repository.MareuRepository;
import com.artus.mareu.ui.meetings_list.ViewModels.CreateMeetingViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@RunWith(MockitoJUnitRunner.class)
public class CreateMeetingVMUnitTest {

        @Mock
        private MareuRepository mockRepository;

        private CreateMeetingViewModel viewModel;
        private Meeting meeting;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

        @Before
        public void setUp() {
            viewModel = new CreateMeetingViewModel(mockRepository);
        }

        @Test
        public void displayRoomSpinner_visibilityChanged() {
            // Given
            boolean initialDisplay = false;

            // When
            viewModel.displayRoomSpinner(initialDisplay);

            // Then
            MutableLiveData<Boolean> visibleLiveData = viewModel.getVisible();
            assertTrue(visibleLiveData.getValue());
        }

        @Test
        public void fetchMeetings_repositoryCalled() {
            // Given
            String room = null;
            LocalDate date = null;

            // When
            viewModel.fetchMeetings(room, date);

            // Then
            verify(mockRepository).getMeetings(room, date);
        }

        @Test
        public void fetchFilteredRooms_repositoryCalledAndSpinnerVisibilityChanged() {
            // Given
            LocalDateTime dateTime = LocalDateTime.of(2023,7,12,10,30);
            List<Meeting> meetings = MeetingGenerator.PLANIFIED_MEETINGS;

            // When
            viewModel.fetchFilteredRooms(dateTime, meetings);

            // Then
            verify(mockRepository).getAvailableRooms(dateTime, meetings);

            MutableLiveData<Boolean> visibleLiveData = viewModel.getVisible();
            assertTrue(visibleLiveData.getValue());
        }

        @Test
        public void addMeeting_repositoryCalled() {
            // Given
            meeting = new Meeting(9, "le maillon de la blockChain",
                    LocalDateTime.of(2023, 7,18, 10, 0),"Donut",
                    new ArrayList<>(Arrays.asList("edouard@caramail.fr", "gontrand@lycos.fr","charles-kévin@lycos.fr")));
            // When
            viewModel.addMeeting(meeting);

            // Then
            verify(mockRepository).createMeeting(meeting);
        }
    }
