package com.hsbc.bookit.dao;

import com.hsbc.bookit.domain.Meetings;
import com.hsbc.bookit.exceptions.MeetingAlreadyExistsException;
import com.hsbc.bookit.util.ConManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MeetingDAOImpl implements MeetingDAO{
    private Connection conn;

    public MeetingDAOImpl() {
        conn = ConManager.getConnection(); // Utility to handle DB connection
    }

    // ****************************************************************** //
    //add a meeting into the database
    @Override
    public void addMeeting(Meetings meeting) {
        String query = "INSERT INTO Meetings (id,room_id, manager_id, start_time, end_time, status) VALUES (?,?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, meeting.getId());
            stmt.setInt(2, meeting.getRoomId());
            stmt.setString(3, meeting.getManagerId());
            stmt.setTimestamp(4, new java.sql.Timestamp(meeting.getStartTime().getTime()));
            stmt.setTimestamp(5, new java.sql.Timestamp(meeting.getEndTime().getTime()));
            stmt.setString(6, meeting.getStatus());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new MeetingAlreadyExistsException("A Meeting with this id already exists!");
        }
    }

    // ****************************************************************** //
    //remove a meeting from the database
    @Override
    public void removeMeeting(String id) {
        String query = "DELETE FROM Meetings WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, Integer.parseInt(id)); // Set the ID of the meeting to be removed
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Meeting removed successfully.");
            } else {
                System.out.println("No meeting found with the provided ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ****************************************************************** //
    //view all the meetings currently scheduled
    @Override
    public List<Meetings> viewAllMeetings() {
        String query = "SELECT * FROM Meetings";
        List<Meetings> meetings = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            // Iterate through the result set to populate the meetings list
            while (rs.next()) {
                int id = rs.getInt("id");
                int roomId = rs.getInt("room_id");
                String managerId = rs.getString("manager_id");
                Date startTime = rs.getTimestamp("start_time");
                Date endTime = rs.getTimestamp("end_time");
                String status = rs.getString("status");

                // Create a Meeting object and add it to the list
                Meetings meeting = new Meetings(id,roomId,managerId,startTime,endTime,status);
                meetings.add(meeting);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return meetings;
    }
}
