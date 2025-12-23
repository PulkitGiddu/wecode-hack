package com.wecode.bookit.dao;

import com.wecode.bookit.model.Rooms;
import com.wecode.bookit.exceptions.DuplicateEntryException;
import com.wecode.bookit.util.ConManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoomDAOImpl implements RoomDAO {

    // Assume we have a utility class for DB connection
    private Connection conn;

    public RoomDAOImpl() {
        conn = ConManager.getConnection(); // Utility to handle DB connection
    }
    // ****************************************************************** //
    //add a new room to the db
    @Override
    public void addRoom(Rooms room) {
        String query = "INSERT INTO Rooms (id,name, seating_capacity) VALUES (?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, room.getId());
            stmt.setString(2, room.getName());
            stmt.setInt(3, room.getSeatingCapacity());
            stmt.executeUpdate();
        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                throw new DuplicateEntryException("A room with ID " + room.getId() + " already exists.");
            } else {
                e.printStackTrace(); // Handle other SQL exceptions
            }
        }
    }
    // ****************************************************************** //
    //update details of an existing room
    @Override
    public void updateRoom(int roomId, String roomName, int seatingCapacity) {
        String query = "UPDATE Rooms SET name = ?, seating_capacity = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, roomName);
            stmt.setInt(2, seatingCapacity);
            stmt.setInt(3, roomId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // ****************************************************************** //
    //delete an existing room
    @Override
    public void deleteRoom(int roomId) {
        String query = "DELETE FROM Rooms WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, roomId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // ****************************************************************** //
    //view all the rooms currently available for meetings
    @Override
    public List<Rooms> getAllRooms() {
        String query = "SELECT * FROM Rooms";
        List<Rooms> roomsList = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Rooms room = new Rooms(rs.getInt("id"),rs.getString("name"), rs.getInt("seating_capacity"));
                room.setId(rs.getInt("id"));
                roomsList.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roomsList;
    }

    // ****************************************************************** //
    // Method to fetch room by name Default
    @Override
    public List<Rooms> getDefaultRooms() {
        // These are the predefined default rooms
        return Arrays.asList(
                new Rooms(3,"Classroom Training", 10),  // Whiteboard, Projector
                new Rooms(4,"Online Training", 10),     // Wifi, Projector
                new Rooms(5,"Conference Call", 5),      // Conference Call
                new Rooms(6,"Business Call", 5));         // Projector}
    }
}
