package com.wecode.bookit.repository;

import com.wecode.bookit.entity.Booking;
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
public interface BookingRepository extends JpaRepository<Booking, UUID> {
    List<Booking> findByUserId(UUID userId);
    List<Booking> findByRoomId(UUID roomId);
    Optional<Booking> findByBookingId(UUID bookingId);
    List<Booking> findByUserIdAndStatus(UUID userId, String status);
    List<Booking> findByUserIdAndMeetingDate(UUID userId, LocalDate meetingDate);
    List<Booking> findByUserIdAndMeetingDateAndStatus(UUID userId, LocalDate meetingDate, String status);

    @Query("SELECT b FROM Booking b JOIN User u ON b.userId = u.userId WHERE u.name = :managerName AND b.meetingDate = :meetingDate")
    List<Booking> findByManagerNameAndDate(@Param("managerName") String managerName, @Param("meetingDate") LocalDate meetingDate);

    @Query("SELECT b FROM Booking b WHERE b.status = :status AND b.meetingDate < :date")
    List<Booking> findByStatusAndMeetingDateBefore(@Param("status") String status, @Param("date") LocalDate date);

    @Query("SELECT b FROM Booking b WHERE b.checkInStatus = 'PENDING' AND b.meetingDate < CURRENT_DATE")
    List<Booking> findPendingCheckInsForPenalty();

    /**
     * Find bookings that conflict with the requested time slot make sure to avoid double booking
     */
    @Query("SELECT b FROM Booking b WHERE b.roomId = :roomId " +
            "AND b.meetingDate = :meetingDate " +
            "AND b.status = :status " +
            "AND b.startTime < :endTime " +
            "AND b.endTime > :startTime")
    List<Booking> findConflictingBookings(
            @Param("roomId") UUID roomId,
            @Param("meetingDate") LocalDate meetingDate,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            @Param("status") String status);
}
