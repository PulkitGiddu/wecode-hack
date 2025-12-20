package com.hsbc.bookit;

import com.hsbc.bookit.dao.UserDAO;
import com.hsbc.bookit.domain.Users;
import com.hsbc.bookit.exceptions.AccessDeniedException;
import com.hsbc.bookit.services.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    private UserDAO userDAOMock;
    private UserServiceImpl userService;
    private Users adminUser;
    private Users regularUser;

    @BeforeEach
    void setUp() {
        // Create mock UserDAO
        userDAOMock = Mockito.mock(UserDAO.class);

        // Create mock users
        adminUser = new Users("1", "admin", "password", "Admin User", "admin@example.com", "1234567890", "admin", 100);
        regularUser = new Users("2", "user", "password", "Regular User", "user@example.com", "0987654321", "user", 50);

        // Initialize UserServiceImpl with mocks
        userService = new UserServiceImpl(adminUser);
        injectMock("userdao", userDAOMock);
    }

    private void injectMock(String fieldName, Object mock) {
        try {
            var field = UserServiceImpl.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(userService, mock);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testAddUserdata_Success() {
        // Setup
        String id = "3";
        String username = "newuser";
        String password = "password";
        String name = "New User";
        String email = "newuser@example.com";
        String phone = "1111111111";
        String role = "user";
        int credits = 10;

        // Call the method
        userService.addUserdata(id, username, password, name, email, phone, role, credits);

        // Verify interactions
        verify(userDAOMock).addUsers(adminUser, new Users(id, username, password, name, email, phone, role, credits));
    }

    @Test
    void testDeleteUserdata_Success() {
        // Setup
        String username = "user";

        // Call the method
        userService.deleteUserdata(username);

        // Verify interactions
        verify(userDAOMock).deleteUsers(adminUser, username);
    }

    @Test
    void testGetAllUsers_Success() {
        // Setup
        List<Users> mockUsers = Arrays.asList(
                new Users("1", "user1", "password", "User One", "user1@example.com", "1234567890", "user", 50),
                new Users("2", "user2", "password", "User Two", "user2@example.com", "0987654321", "user", 30)
        );
        when(userDAOMock.findUsers()).thenReturn(mockUsers);

        // Call the method
        userService.getAllUsers();

        // Verify interactions
        verify(userDAOMock).findUsers();
    }

    @Test
    void testGetUsersByUsername_Success() {
        // Setup
        String username = "user";
        List<Users> mockUsers = Arrays.asList(
                new Users("2", "user", "password", "User Two", "user2@example.com", "0987654321", "user", 30)
        );
        when(userDAOMock.getUsersbyusername(username)).thenReturn(mockUsers);

        // Call the method
        userService.getUsersByUsername(username);

        // Verify interactions
        verify(userDAOMock).getUsersbyusername(username);
    }

    @Test
    void testAddUserdata_AccessDenied() {
        // Setup
        userService = new UserServiceImpl(regularUser); // Use a non-admin user

        // Call the method and expect an exception
        assertThrows(AccessDeniedException.class, () -> {
            userService.addUserdata("3", "newuser", "password", "New User", "newuser@example.com", "1111111111", "user", 10);
        });
    }

    @Test
    void testDeleteUserdata_AccessDenied() {
        // Setup
        userService = new UserServiceImpl(regularUser); // Use a non-admin user

        // Call the method and expect an exception
        assertThrows(AccessDeniedException.class, () -> {
            userService.deleteUserdata("user");
        });
    }

    @Test
    void testGetAllUsers_AccessDenied() {
        // Setup
        userService = new UserServiceImpl(regularUser); // Use a non-admin user

        // Call the method and expect an exception
        assertThrows(AccessDeniedException.class, () -> {
            userService.getAllUsers();
        });
    }

    @Test
    void testGetUsersByUsername_AccessDenied() {
        // Setup
        userService = new UserServiceImpl(regularUser); // Use a non-admin user

        // Call the method and expect an exception
        assertThrows(AccessDeniedException.class, () -> {
            userService.getUsersByUsername("user");
        });
    }
}
