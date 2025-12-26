package com.wecode.bookit;

import com.wecode.bookit.dao.AmenityDAO;
import com.wecode.bookit.model.Amenities;
import com.wecode.bookit.exceptions.AmenitiesNotFoundException;
import com.wecode.bookit.services.serviceImpl.AmenityServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AmenityServiceImplTest {

    private AmenityDAO amenityDAOMock;
    private AmenityServiceImpl amenityService;

    @BeforeEach
    void setUp() throws Exception {
        // Create mock AmenityDAO
        amenityDAOMock = Mockito.mock(AmenityDAO.class);

        // Create the AmenityServiceImpl instance
        amenityService = new AmenityServiceImpl();

        // Use reflection to inject the mock AmenityDAO into the service
        Field field = AmenityServiceImpl.class.getDeclaredField("amenityDAO");
        field.setAccessible(true);
        field.set(amenityService, amenityDAOMock);
    }

    @Test
    void testAddAmenity() {
        // Test data
        String name = "Projector";
        int cost = 10;

        // Call the method
        amenityService.addAmenity(name, cost);

        // Verify that the addAmenity method was called with the correct arguments
        verify(amenityDAOMock).addAmenity(any(Amenities.class));
    }

    @Test
    void testUpdateAmenity() {
        // Test data
        int amenityId = 1;
        String name = "Updated Projector";
        int cost = 15;

        // Call the method
        amenityService.updateAmenity(amenityId, name, cost);

        // Verify that the updateAmenity method was called with the correct arguments
        verify(amenityDAOMock).updateAmenity(amenityId, name, cost);
    }

    @Test
    void testDeleteAmenity() {
        // Test data
        int amenityId = 1;

        // Call the method
        amenityService.deleteAmenity(amenityId);

        // Verify that the deleteAmenity method was called with the correct arguments
        verify(amenityDAOMock).deleteAmenity(amenityId);
    }

    @Test
    void testViewAllAmenities() {
        // Prepare mock data
        List<Amenities> amenities = Arrays.asList(
                new Amenities("Projector", 10),
                new Amenities("Whiteboard", 5)
        );

        // Mocking the getAllAmenities method
        when(amenityDAOMock.getAllAmenities()).thenReturn(amenities);

        // Call the method
        List<Amenities> result = amenityService.viewAllAmenities();

        // Verify the result
        assertEquals(amenities, result);
        verify(amenityDAOMock).getAllAmenities();
    }

    @Test
    void testChooseAmenitiesAndCalculateCredits() {
        // Prepare mock data
        List<Amenities> amenities = Arrays.asList(
                new Amenities("Projector", 10),
                new Amenities("Whiteboard", 5)
        );

        // Mocking the getAllAmenities method
        when(amenityDAOMock.getAllAmenities()).thenReturn(amenities);

        // Mocking the selectAmenitiesAndCalculateCredits method
        when(amenityDAOMock.selectAmenitiesAndCalculateCredits(anyList())).thenReturn(15);

        // Test data
        List<String> selectedAmenities = Arrays.asList("Projector", "Whiteboard");

        // Call the method
        int totalCredits = amenityService.chooseAmenitiesAndCalculateCredits(selectedAmenities);

        // Verify the interactions and the result
        verify(amenityDAOMock).getAllAmenities();
        verify(amenityDAOMock).selectAmenitiesAndCalculateCredits(selectedAmenities);
        assertEquals(15, totalCredits);
    }

    @Test
    void testChooseAmenitiesAndCalculateCredits_AmenityNotFound() {
        // Prepare mock data
        List<Amenities> amenities = Arrays.asList(
                new Amenities("Projector", 10)
        );

        // Mocking the getAllAmenities method
        when(amenityDAOMock.getAllAmenities()).thenReturn(amenities);

        // Test data
        List<String> selectedAmenities = Arrays.asList("Whiteboard");

        // Call the method and expect an exception
        assertThrows(AmenitiesNotFoundException.class, () -> {
            amenityService.chooseAmenitiesAndCalculateCredits(selectedAmenities);
        });

        // Verify that the getAllAmenities method was called
        verify(amenityDAOMock).getAllAmenities();
    }
}
