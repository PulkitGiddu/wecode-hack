package com.wecode.bookit.services.serviceImpl;

import com.wecode.bookit.entity.BookingLock;
import com.wecode.bookit.repository.BookingLockRepository;
import com.wecode.bookit.services.LockingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class LockingServiceImpl implements LockingService {

    private static final long LOCK_DURATION_MINUTES = 5;

    private final BookingLockRepository bookingLockRepository;

    public LockingServiceImpl(BookingLockRepository bookingLockRepository) {
        this.bookingLockRepository = bookingLockRepository;
    }

    /**
     * Acquire a lock on a room for the specified time slot
     * First-come-first-serve: If room is already locked, throws 409 Conflict exception
     */
    @Override
    public UUID acquireLock(UUID roomId, UUID userId, LocalDate meetingDate,
                           LocalDateTime startTime, LocalDateTime endTime) {

        List<BookingLock> conflictingLocks = bookingLockRepository.findConflictingLocks(
                roomId, meetingDate, startTime, endTime
        );

        if (!conflictingLocks.isEmpty()) {
            BookingLock existingLock = conflictingLocks.get(0);
            long minutesRemaining = ChronoUnit.MINUTES.between(
                    LocalDateTime.now(),
                    existingLock.getExpiresAt()
            );
            throw new RuntimeException(
                    "Room is currently being booked by another manager. " +
                    "Lock expires in " + minutesRemaining + " minutes. Please try again later."
            );
        }

        UUID lockId = UUID.randomUUID();
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(LOCK_DURATION_MINUTES);

        BookingLock lock = new BookingLock(
                lockId,
                roomId,
                userId,
                meetingDate,
                startTime,
                endTime,
                expiresAt
        );

        bookingLockRepository.save(lock);

        System.out.println("Lock acquired: " + lockId + " for room: " + roomId +
                          " by user: " + userId + " | Expires at: " + expiresAt);

        return lockId;
    }

    /**
     * Release a lock after successful booking
     */
    @Override
    public void releaseLock(UUID lockId, UUID userId) {
        BookingLock lock = bookingLockRepository.findByLockId(lockId)
                .orElseThrow(() -> new RuntimeException("Lock not found: " + lockId));

        if (!lock.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized: Lock does not belong to this user");
        }

        lock.setStatus(BookingLock.LockStatus.RELEASED);
        bookingLockRepository.save(lock);

        System.out.println("Lock released: " + lockId);
    }

    /**
     * Check if a lock is still valid (not expired or released)
     */
    @Override
    @Transactional(readOnly = true)
    public boolean isLockValid(UUID lockId) {
        return bookingLockRepository.findActiveLockById(lockId).isPresent();
    }

    /**
     * Check if a room is locked for a specific time slot
     */
    @Override
    @Transactional(readOnly = true)
    public boolean isRoomLocked(UUID roomId, LocalDate meetingDate,
                                LocalDateTime startTime, LocalDateTime endTime) {
        List<BookingLock> locks = bookingLockRepository.findConflictingLocks(
                roomId, meetingDate, startTime, endTime
        );
        return !locks.isEmpty();
    }

    /**
     * Get time remaining for a lock before expiry (in minutes)
     */
    @Override
    @Transactional(readOnly = true)
    public long getTimeRemainingMinutes(UUID lockId) {
        BookingLock lock = bookingLockRepository.findActiveLockById(lockId)
                .orElseThrow(() -> new RuntimeException("Lock not found or expired: " + lockId));

        return ChronoUnit.MINUTES.between(LocalDateTime.now(), lock.getExpiresAt());
    }

    /**
     * Clean up expired locks (called by scheduled task)
     */
    @Override
    public void cleanupExpiredLocks() {
        LocalDateTime now = LocalDateTime.now();
        List<BookingLock> expiredLocks = bookingLockRepository.findExpiredLocks(now);

        for (BookingLock lock : expiredLocks) {
            lock.setStatus(BookingLock.LockStatus.EXPIRED);
            bookingLockRepository.save(lock);
            System.out.println("Lock expired and cleaned up: " + lock.getLockId());
        }

        if (!expiredLocks.isEmpty()) {
            System.out.println("Cleaned up " + expiredLocks.size() + " expired locks");
        }
    }
}

