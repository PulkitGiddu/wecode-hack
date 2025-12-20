package com.hsbc.bookit.services;

import com.hsbc.bookit.domain.Amenities;

import java.util.List;

public interface AmenityService {
    public void addAmenity(String name, int cost);
    public void updateAmenity(int amenityId, String name, int cost);
    public void deleteAmenity(int amenityId);
    public List<Amenities> viewAllAmenities();
    public int chooseAmenitiesAndCalculateCredits(List<String> selectedAmenities);

}
