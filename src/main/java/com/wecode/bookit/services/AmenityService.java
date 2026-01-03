package com.wecode.bookit.services;

import com.wecode.bookit.dto.CreateAmenityDto;
import com.wecode.bookit.dto.UpdateAmenityDto;
import com.wecode.bookit.entity.Amenity;

import java.util.List;
import java.util.UUID;

public interface AmenityService {
    Amenity createAmenity(CreateAmenityDto createAmenityDto);
    Amenity updateAmenity(UpdateAmenityDto updateAmenityDto);
    Amenity getAmenityById(UUID amenityId);
    List<Amenity> getAllAmenities();
    void deleteAmenity(UUID amenityId);
}

