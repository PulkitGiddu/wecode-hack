package com.wecode.bookit.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


public interface LockingService {

    /**
     * @param roomId       UUID of the room to lock
     * @param userId       UUID of the manager requesting the lock
     * @param meetingDate  Date of the meeting
     * @param startTime    Start time of the booking
     * @param endTime      End time of the booking
     * @return UUID of the acquired lock
     * @throws RuntimeException if room is already locked for this time slot
     */

    UUID acquireLock(UUID roomId, UUID userId, LocalDate meetingDate,
                     LocalDateTime startTime, LocalDateTime endTime);


    void releaseLock(UUID lockId, UUID userId);

    boolean isLockValid(UUID lockId);

    boolean isRoomLocked(UUID roomId, LocalDate meetingDate,
                        LocalDateTime startTime, LocalDateTime endTime);


    long getTimeRemainingMinutes(UUID lockId);

    void cleanupExpiredLocks();
}

