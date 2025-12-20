package com.hsbc.bookit;

import com.hsbc.bookit.dao.LoginDAO;
import com.hsbc.bookit.domain.Users;
import com.hsbc.bookit.services.LoginServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Calendar;

import static java.util.Calendar.MONDAY;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class LoginServiceImplTest {

    @Mock
    private LoginDAO loginDAO;

    @InjectMocks
    private LoginServiceImpl loginService;

    private Users mockUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockUser = new Users("1", "johndoe", "password123", "John Doe", "john.doe@example.com", "1234567890", "manager", 1000);
    }

    @Test
    void testLoginSuccessful() {
        // Arrange
        when(loginDAO.authenticate(anyString(), anyString())).thenReturn(mockUser);

        // Act
        Users user = loginService.login("johndoe", "password123");

        // Assert
        assertNotNull(user);
        assertEquals("johndoe", user.getUsername());
        assertEquals("John Doe", user.getName());
    }

}
