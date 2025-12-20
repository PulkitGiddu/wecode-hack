package com.hsbc.bookit.dao;

import com.hsbc.bookit.domain.Meetings;

import java.util.List;

public interface MeetingDAO {

    void addMeeting(Meetings meeting);
    void removeMeeting(String id);
    List<Meetings> viewAllMeetings();
}
