package com.wecode.bookit.repository;

import com.wecode.bookit.entity.BookingLock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookingLockRepository extends JpaRepository<BookingLock, UUID> {

    /**
     * Find active locks for a specific room and time slot
     * Used to check if a room is already locked for the requested time
     */
    @Query("SELECT bl FROM BookingLock bl WHERE bl.roomId = :roomId " +
            "AND bl.status = 'ACTIVE' " +
            "AND bl.meetingDate = :meetingDate " +
            "AND bl.startTime < :endTime " +
            "AND bl.endTime > :startTime")
    List<BookingLock> findConflictingLocks(
            @Param("roomId") UUID roomId,
            @Param("meetingDate") LocalDate meetingDate,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );

    /**
     * Find lock by lock ID
     */
    Optional<BookingLock> findByLockId(UUID lockId);

    /**
     * Find all active locks for a specific user
     */
    @Query("SELECT bl FROM BookingLock bl WHERE bl.userId = :userId AND bl.status = 'ACTIVE'")
    List<BookingLock> findActiveLocksForUser(@Param("userId") UUID userId);

    /**
     * Find all expired locks
     * Used by scheduled task to clean up expired locks
     */
    @Query("SELECT bl FROM BookingLock bl WHERE bl.status = 'ACTIVE' AND bl.expiresAt < :now")
    List<BookingLock> findExpiredLocks(@Param("now") LocalDateTime now);

    /**
     * Find active lock for a specific lock ID
     */
    @Query("SELECT bl FROM BookingLock bl WHERE bl.lockId = :lockId AND bl.status = 'ACTIVE'")
    Optional<BookingLock> findActiveLockById(@Param("lockId") UUID lockId);
}

