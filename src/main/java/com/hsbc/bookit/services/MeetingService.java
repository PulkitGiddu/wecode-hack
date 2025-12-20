package com.hsbc.bookit.services;

import com.hsbc.bookit.domain.Meetings;
import com.hsbc.bookit.domain.Rooms;
import com.hsbc.bookit.exceptions.SameDateTimeException;

import java.sql.Timestamp;
import java.util.List;

public interface MeetingService {
    public List<Rooms> getDefaultRoomOptions();
    public void bookMeetingWithDefaultRoom(int id, int roomId, Timestamp startTime, Timestamp endTime, MeetingServiceImpl.DefaultRoom roomOption) throws SameDateTimeException;
    public void bookMeetingWithCustomRoom(int id, int roomId, Timestamp startTime, Timestamp endTime, List<String> selectedAmenities, int seatingCapacity) throws SameDateTimeException;
    public void deleteMeeting(String id);
    public List<Meetings> viewMeetings();



}
