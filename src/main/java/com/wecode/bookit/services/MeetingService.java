package com.wecode.bookit.services;

import com.wecode.bookit.model.Meetings;
import com.wecode.bookit.model.Rooms;
import com.wecode.bookit.exceptions.SameDateTimeException;
import com.wecode.bookit.services.serviceImpl.MeetingServiceImpl;

import java.sql.Timestamp;
import java.util.List;

public interface MeetingService {
    public List<Rooms> getDefaultRoomOptions();
    public void bookMeetingWithDefaultRoom(int id, int roomId, Timestamp startTime, Timestamp endTime, MeetingServiceImpl.DefaultRoom roomOption) throws SameDateTimeException;
    public void bookMeetingWithCustomRoom(int id, int roomId, Timestamp startTime, Timestamp endTime, List<String> selectedAmenities, int seatingCapacity) throws SameDateTimeException;
    public void deleteMeeting(String id);
    public List<Meetings> viewMeetings();



}
