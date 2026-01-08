package com.wecode.bookit.repository;

import com.wecode.bookit.entity.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AmenityRepository extends JpaRepository<Amenity, UUID> {
    Optional<Amenity> findByAmenityName(String amenityName);
    boolean existsByAmenityName(String amenityName);

    @Query(value = "SELECT a.* FROM amenities a " +
            "INNER JOIN room_amenities ra ON a.amenity_id = ra.amenity_id " +
            "WHERE ra.room_id = :roomId AND a.is_active = true",
            nativeQuery = true)
    List<Amenity> findByRoomId(@Param("roomId") UUID roomId);
}

