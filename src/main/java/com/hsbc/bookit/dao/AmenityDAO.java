package com.hsbc.bookit.dao;

import com.hsbc.bookit.domain.Amenities;

import java.util.List;

public interface AmenityDAO {
    void addAmenity(Amenities amenity);
    void updateAmenity(int amenityId, String name, int cost);
    void deleteAmenity(int amenityId);
    List<Amenities> getAllAmenities();
    int selectAmenitiesAndCalculateCredits(List<String> selectedAmenities);
}
