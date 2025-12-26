package com.wecode.bookit.services.serviceImpl;

import com.wecode.bookit.dao.AmenityDAO;
import com.wecode.bookit.dao.AmenityDAOImpl;
import com.wecode.bookit.model.Amenities;
import com.wecode.bookit.model.Users;
import com.wecode.bookit.exceptions.AmenitiesNotFoundException;
import com.wecode.bookit.services.AmenityService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AmenityServiceImpl implements AmenityService {


    private final AmenityDAO amenityDAO = new AmenityDAOImpl();
    private Users authenticatedUser;

    //add an amenity
    public void addAmenity(String name, int cost) {
        Amenities amenity = new Amenities(name, cost);
        amenityDAO.addAmenity(amenity);
        System.out.println("Amenity added: " + name);
    }

    //update amenity
    public void updateAmenity(int amenityId, String name, int cost) {
        amenityDAO.updateAmenity(amenityId, name, cost);
        System.out.println("Amenity updated: " + name);
    }

    //delete an amenity based on its id
    public void deleteAmenity(int amenityId) {
        amenityDAO.deleteAmenity(amenityId);
        System.out.println("Amenity deleted: " + amenityId);
    }

    //view all amenities
    public List<Amenities> viewAllAmenities() {
        return amenityDAO.getAllAmenities();
    }

    //uses selected amenities to calculate the cost of the meeting and display it to the user
    public int chooseAmenitiesAndCalculateCredits(List<String> selectedAmenities) {
        // Get available amenities
        List<Amenities> availableAmenities = amenityDAO.getAllAmenities();
        System.out.println("Available amenities:");
        // Create a set of available amenity names for quick lookup
        Set<String> availableAmenityNames = new HashSet<>();
        for (Amenities amenity : availableAmenities) {
            availableAmenityNames.add(amenity.getName());
            System.out.println(amenity.getName() + " - " + amenity.getCost() + " credits");
        }

        // Validate selected amenities
        for (String selectedAmenity : selectedAmenities) {
            if (!availableAmenityNames.contains(selectedAmenity)) {
                throw new AmenitiesNotFoundException("Amenity not found: " + selectedAmenity);
            }
        }

        // Calculate total credits
        int totalCredits = amenityDAO.selectAmenitiesAndCalculateCredits(selectedAmenities);
        System.out.println("Total credits for selected amenities: " + totalCredits);
        return totalCredits;
    }
}
