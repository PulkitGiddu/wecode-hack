package com.wecode.bookit;

import com.wecode.bookit.dao.MeetingDAO;
import com.wecode.bookit.dao.RoomDAO;
import com.wecode.bookit.dao.UserDAO;
import com.wecode.bookit.model.Meetings;
import com.wecode.bookit.model.Users;
import com.wecode.bookit.exceptions.NotEnoughCreditsException;
import com.wecode.bookit.exceptions.SameDateTimeException;
import com.wecode.bookit.services.serviceImpl.AmenityServiceImpl;
import com.wecode.bookit.services.serviceImpl.MeetingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MeetingServiceImplTest {

    private UserDAO userDAOMock;
    private MeetingDAO meetingDAOMock;
    private AmenityServiceImpl amenityServiceMock;
    private RoomDAO roomDAOMock;
    private MeetingServiceImpl meetingService;
    private Users authenticatedUser;

    @BeforeEach
    void setUp() {
        userDAOMock = Mockito.mock(UserDAO.class);
        meetingDAOMock = Mockito.mock(MeetingDAO.class);
        amenityServiceMock = Mockito.mock(AmenityServiceImpl.class);
        roomDAOMock = Mockito.mock(RoomDAO.class);

        authenticatedUser = new Users("1", "janesmith", "password456", "Jane Smith", "jane.smith@example.com", "9876543210", "Manager", 50);

        meetingService = new MeetingServiceImpl(authenticatedUser, userDAOMock, meetingDAOMock, amenityServiceMock, roomDAOMock);
    }

    @Test
    void testBookMeetingWithDefaultRoom_Success() throws SameDateTimeException {
        Timestamp startTime = Timestamp.valueOf("2024-08-23 10:00:00");
        Timestamp endTime = Timestamp.valueOf("2024-08-23 11:00:00");
        MeetingServiceImpl.DefaultRoom defaultRoom = MeetingServiceImpl.DefaultRoom.CLASSROOM_TRAINING;

        // Call the method under test
        meetingService.bookMeetingWithDefaultRoom(5, 1, startTime, endTime, defaultRoom);

        // Verify that addMeeting was called with the correct arguments
        verify(meetingDAOMock).addMeeting(argThat(meeting ->
                meeting.getRoomId() == 1 &&
                        meeting.getManagerId().equals(authenticatedUser.getId()) &&
                        meeting.getStartTime().equals(startTime) &&
                        meeting.getEndTime().equals(endTime)
        ));

    }

    @Test
    void testBookMeetingWithDefaultRoom_NotEnoughCredits() {
        authenticatedUser.setCredits(10);

        Timestamp startTime = Timestamp.valueOf("2024-08-23 10:00:00");
        Timestamp endTime = Timestamp.valueOf("2024-08-23 11:00:00");
        MeetingServiceImpl.DefaultRoom defaultRoom = MeetingServiceImpl.DefaultRoom.CONFERENCE_CALL;

        NotEnoughCreditsException thrown = assertThrows(NotEnoughCreditsException.class, () -> {
            meetingService.bookMeetingWithDefaultRoom(5, 1, startTime, endTime, defaultRoom);
        });

        assertEquals("You do not have enough credits!", thrown.getMessage());

        verify(meetingDAOMock, never()).addMeeting(any(Meetings.class));
        verify(userDAOMock, never()).updateUserCredits(any(Users.class), anyInt());
    }

    @Test
    void testBookMeetingWithCustomRoom_Success() throws SameDateTimeException {
        Timestamp startTime = Timestamp.valueOf("2024-08-23 10:00:00");
        Timestamp endTime = Timestamp.valueOf("2024-08-23 11:00:00");
        List<String> selectedAmenities = Arrays.asList("Projector", "Whiteboard");

        when(amenityServiceMock.chooseAmenitiesAndCalculateCredits(selectedAmenities)).thenReturn(15);

        meetingService.bookMeetingWithCustomRoom(6, 1, startTime, endTime, selectedAmenities, 8);

        verify(meetingDAOMock).addMeeting(argThat(meeting ->
                meeting.getRoomId() == 1 &&
                        meeting.getManagerId().equals(authenticatedUser.getId()) &&
                        meeting.getStartTime().equals(startTime) &&
                        meeting.getEndTime().equals(endTime)
        ));
        verify(userDAOMock).updateUserCredits(authenticatedUser, 50 - (15 + 10));
    }

    @Test
    void testDeleteMeeting_Success() {
        meetingService.deleteMeeting("5");

        verify(meetingDAOMock).removeMeeting("5");
    }

    @Test
    void testViewMeetings() {
        List<Meetings> mockMeetings = Arrays.asList(
                new Meetings(1, 1, "1", Timestamp.valueOf("2024-08-23 10:00:00"), Timestamp.valueOf("2024-08-23 11:00:00"), "Scheduled")
        );
        when(meetingDAOMock.viewAllMeetings()).thenReturn(mockMeetings);

        List<Meetings> meetings = meetingService.viewMeetings();

        assertNotNull(meetings);
        assertEquals(1, meetings.size());
        assertEquals(1, meetings.get(0).getId());
    }
}
