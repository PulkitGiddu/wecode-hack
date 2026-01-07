package com.wecode.bookit.services.serviceImpl;

import com.wecode.bookit.dto.CreateAmenityDto;
import com.wecode.bookit.dto.UpdateAmenityDto;
import com.wecode.bookit.entity.Amenity;
import com.wecode.bookit.repository.AmenityRepository;
import com.wecode.bookit.services.AmenityService;
import com.wecode.bookit.services.CacheService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AmenityServiceImpl implements AmenityService {

    private final AmenityRepository amenityRepository;
    private final CacheService cacheService;

    public AmenityServiceImpl(AmenityRepository amenityRepository, CacheService cacheService) {
        this.amenityRepository = amenityRepository;
        this.cacheService = cacheService;
    }

    @Override
    public Amenity createAmenity(CreateAmenityDto createAmenityDto) {
        if (amenityRepository.existsByAmenityName(createAmenityDto.getAmenityName())) {
            throw new RuntimeException("Amenity name already exists");
        }

        Amenity amenity = new Amenity();
        amenity.setAmenityId(UUID.randomUUID());
        amenity.setAmenityName(createAmenityDto.getAmenityName());
        amenity.setCreditCost(createAmenityDto.getCreditCost());
        amenity.setIsActive(true);

        Amenity saved = amenityRepository.save(amenity);
        cacheService.invalidateAmenityCaches();
        return saved;
    }

    @Override
    public Amenity updateAmenity(UpdateAmenityDto updateAmenityDto) {
        Amenity amenity = amenityRepository.findById(updateAmenityDto.getAmenityId())
                .orElseThrow(() -> new RuntimeException("Amenity not found"));

        if (updateAmenityDto.getAmenityName() != null && !updateAmenityDto.getAmenityName().isEmpty()
                && !updateAmenityDto.getAmenityName().equals(amenity.getAmenityName())) {
            if (amenityRepository.existsByAmenityName(updateAmenityDto.getAmenityName())) {
                throw new RuntimeException("Amenity name already exists");
            }
            amenity.setAmenityName(updateAmenityDto.getAmenityName());
        }

        if (updateAmenityDto.getCreditCost() != null) {
            amenity.setCreditCost(updateAmenityDto.getCreditCost());
        }

        if (updateAmenityDto.getIsActive() != null) {
            amenity.setIsActive(updateAmenityDto.getIsActive());
        }

        Amenity updated = amenityRepository.save(amenity);
        cacheService.invalidateAmenityCaches();
        cacheService.invalidateRoomCache(null);
        return updated;
    }

    @Override
    public Amenity getAmenityById(UUID amenityId) {
        return amenityRepository.findById(amenityId)
                .orElseThrow(() -> new RuntimeException("Amenity not found"));
    }

    @Override
    public List<Amenity> getAllAmenities() {
        return cacheService.getAllAmenities();
    }

    @Override
    public void deleteAmenity(UUID amenityId) {
        Amenity amenity = amenityRepository.findById(amenityId)
                .orElseThrow(() -> new RuntimeException("Amenity not found"));
        amenity.setIsActive(false);
        amenityRepository.save(amenity);
        cacheService.invalidateAmenityCaches();
        cacheService.invalidateRoomCache(null);
    }
}

