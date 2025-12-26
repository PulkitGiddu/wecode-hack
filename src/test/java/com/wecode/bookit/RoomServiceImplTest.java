package com.wecode.bookit;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.wecode.bookit.dao.RoomDAO;
import com.wecode.bookit.model.Rooms;
import com.wecode.bookit.model.Users;
import com.wecode.bookit.services.serviceImpl.RoomServiceImpl;
import com.wecode.bookit.services.serviceImpl.LoginServiceImpl;

public class RoomServiceImplTest {

    @Mock
    private RoomDAO roomDAOMock;

    @Mock
    private LoginServiceImpl loginServiceMock;

    @InjectMocks
    private RoomServiceImpl roomService;

    private Users adminUser;
    private Users nonAdminUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        adminUser = new Users("1", "adminuser", "adminpass", "Admin User", "admin@example.com", "1234567890", "Admin", 100);
        nonAdminUser = new Users("2", "nonadminuser", "nonadminpass", "Non Admin User", "nonadmin@example.com", "0987654321", "User", 50);
        roomService.authenticatedUser = adminUser; // Set default to admin
    }

    @Test
    void testAddRoom_AdminAccess() {
        // Setup
        int roomId = 1;
        String roomName = "Conference Room";
        int seatingCapacity = 10;

        // Act
        roomService.addRoom(roomId, roomName, seatingCapacity);

        // Assert
        verify(roomDAOMock).addRoom(new Rooms(roomId, roomName, seatingCapacity));
    }

    @Test
    void testUpdateRoom_AdminAccess() {
        // Setup
        int roomId = 1;
        String roomName = "Updated Conference Room";
        int seatingCapacity = 15;

        // Act
        roomService.updateRoom(roomId, roomName, seatingCapacity);

        // Assert
        verify(roomDAOMock).updateRoom(roomId, roomName, seatingCapacity);
    }

    @Test
    void testDeleteRoom_AdminAccess() {
        // Setup
        int roomId = 1;

        // Act
        roomService.deleteRoom(roomId);

        // Assert
        verify(roomDAOMock).deleteRoom(roomId);
    }
}
