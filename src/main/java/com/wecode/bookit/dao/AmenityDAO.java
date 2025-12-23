package com.wecode.bookit.dao;

import com.wecode.bookit.model.Amenities;

import java.util.List;

public interface AmenityDAO {
    void addAmenity(Amenities amenity);
    void updateAmenity(int amenityId, String name, int cost);
    void deleteAmenity(int amenityId);
    List<Amenities> getAllAmenities();
    int selectAmenitiesAndCalculateCredits(List<String> selectedAmenities);
}
