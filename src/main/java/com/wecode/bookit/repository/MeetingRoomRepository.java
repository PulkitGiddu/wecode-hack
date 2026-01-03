package com.wecode.bookit.repository;

import com.wecode.bookit.entity.MeetingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MeetingRoomRepository extends JpaRepository<MeetingRoom, UUID> {
    Optional<MeetingRoom> findByRoomName(String roomName);

    boolean existsByRoomName(String roomName);

}