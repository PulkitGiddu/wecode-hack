package com.wecode.bookit.repository;

import com.wecode.bookit.entity.MeetingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MeetingRoomRepository extends JpaRepository<MeetingRoom, Integer> {

    Optional<MeetingRoom> findByRoomName(String roomName);

    List<MeetingRoom> findByIsActiveTrue();

    List<MeetingRoom> findBySeatingCapacityGreaterThanEqual(Integer capacity);

    @Query("SELECT mr FROM MeetingRoom mr WHERE mr.isActive = true " +
            "AND (:hasProjector IS NULL OR mr.hasProjector = :hasProjector) " +
            "AND (:hasWifi IS NULL OR mr.hasWifi = :hasWifi) " +
            "AND (:hasConferenceCall IS NULL OR mr.hasConferenceCall = :hasConferenceCall)")
    List<MeetingRoom> findByAmenities(
            @Param("hasProjector") Boolean hasProjector,
            @Param("hasWifi") Boolean hasWifi,
            @Param("hasConferenceCall") Boolean hasConferenceCall
    );
}