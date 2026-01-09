package com.wecode.bookit.services.serviceImpl;

import com.wecode.bookit.dto.BookingRequestDto;
import com.wecode.bookit.dto.BookingResponseDto;
import com.wecode.bookit.dto.CheckInResponseDto;
import com.wecode.bookit.dto.TodayBookingsDto;
import com.wecode.bookit.dto.MeetingRoomDto;
import com.wecode.bookit.entity.*;
import com.wecode.bookit.repository.AmenityRepository;
import com.wecode.bookit.repository.BookingRepository;
import com.wecode.bookit.repository.ManagerCreditSummaryRepository;
import com.wecode.bookit.repository.MeetingRoomRepository;
import com.wecode.bookit.repository.UserRepository;
import com.wecode.bookit.services.BookingService;
import com.wecode.bookit.services.CacheService;
import com.wecode.bookit.services.LockingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    private static final Integer PENALTY_AMOUNT = 50;

    private final BookingRepository bookingRepository;
    private final MeetingRoomRepository meetingRoomRepository;
    private final UserRepository userRepository;
    private final ManagerCreditSummaryRepository managerCreditSummaryRepository;
    private final CacheService cacheService;
    private final AmenityRepository amenityRepository;
    private final LockingService lockingService;

    public BookingServiceImpl(BookingRepository bookingRepository,
                            MeetingRoomRepository meetingRoomRepository,
                            UserRepository userRepository,
                            ManagerCreditSummaryRepository managerCreditSummaryRepository,
                            CacheService cacheService,
                            AmenityRepository amenityRepository,
                            LockingService lockingService) {
        this.bookingRepository = bookingRepository;
        this.meetingRoomRepository = meetingRoomRepository;
        this.userRepository = userRepository;
        this.managerCreditSummaryRepository = managerCreditSummaryRepository;
        this.cacheService = cacheService;
        this.amenityRepository = amenityRepository;
        this.lockingService = lockingService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MeetingRoomDto> getAvailableMeetingRooms() {
        return cacheService.getAllActiveRooms()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookingResponseDto bookRoom(UUID managerId, BookingRequestDto bookingRequestDto) {
        UUID lockId = null;

        try {
            User manager = userRepository.findById(managerId)
                    .orElseThrow(() -> new RuntimeException("Manager not found"));

            MeetingRoom room = meetingRoomRepository.findById(bookingRequestDto.getRoomId())
                    .orElseThrow(() -> new RuntimeException("Room not found"));

            if (!room.getIsActive()) {
                throw new RuntimeException("Room is not active");
            }

            long durationInMinutes = java.time.Duration.between(
                    bookingRequestDto.getStartTime(),
                    bookingRequestDto.getEndTime()
            ).toMinutes();

            if (durationInMinutes <= 0) {
                throw new RuntimeException("End time must be after start time");
            }

            try {
                lockId = lockingService.acquireLock(
                        bookingRequestDto.getRoomId(),
                        managerId,
                        bookingRequestDto.getMeetingDate(),
                        bookingRequestDto.getStartTime(),
                        bookingRequestDto.getEndTime()
                );
            } catch (RuntimeException e) {
                throw new RuntimeException("Cannot book room: " + e.getMessage());
            }

            // Check if there are existing bookings that conflict with this time slot
            List<Booking> conflictingBookings = bookingRepository.findConflictingBookings(
                    bookingRequestDto.getRoomId(),
                    bookingRequestDto.getMeetingDate(),
                    bookingRequestDto.getStartTime(),
                    bookingRequestDto.getEndTime(),
                    "ACTIVE"
            );

            if (!conflictingBookings.isEmpty()) {
                lockingService.releaseLock(lockId, managerId);
                Booking existingBooking = conflictingBookings.get(0);
                User bookedByManager = userRepository.findById(existingBooking.getUserId()).orElse(null);
                String bookedByName = (bookedByManager != null) ? bookedByManager.getName() : "Unknown Manager";
                throw new RuntimeException("Cannot book room: Room is already booked by " + bookedByName +
                        " from " + existingBooking.getStartTime() + " to " + existingBooking.getEndTime() +
                        ". This room will be free from " + existingBooking.getEndTime());
            }

            double durationInHours = Math.ceil(durationInMinutes / 60.0);
            Integer totalBookingCost = (int) (room.getRoomCost() * durationInHours);

            if (manager.getCredits() < totalBookingCost) {
                lockingService.releaseLock(lockId, managerId);
                throw new RuntimeException("Insufficient credits. Required: " + totalBookingCost + ", Available: " + manager.getCredits());
            }

            manager.setCredits(manager.getCredits() - totalBookingCost);
            userRepository.save(manager);

            ManagerCreditSummary creditSummary = managerCreditSummaryRepository.findByUserId(managerId)
                    .orElse(new ManagerCreditSummary());
            creditSummary.setUserId(managerId);
            creditSummary.setManagerName(manager.getName());
            creditSummary.setEmail(manager.getEmail());
            creditSummary.setTotalCredits(manager.getCredits());
            creditSummary.setCreditsUsed(creditSummary.getCreditsUsed() + totalBookingCost);
            managerCreditSummaryRepository.save(creditSummary);

            Booking booking = new Booking();
            booking.setBookingId(UUID.randomUUID());
            booking.setRoomId(bookingRequestDto.getRoomId());
            booking.setUserId(managerId);
            booking.setMeetingTitle(bookingRequestDto.getMeetingTitle());
            booking.setMeetingDate(bookingRequestDto.getMeetingDate());
            booking.setMeetingType(bookingRequestDto.getMeetingType());
            booking.setRoomName(room.getRoomName());
            booking.setStartTime(bookingRequestDto.getStartTime());
            booking.setEndTime(bookingRequestDto.getEndTime());
            booking.setTotalCredits(totalBookingCost);
            booking.setCheckInStatus("PENDING");
            booking.setStatus("ACTIVE");

            Booking savedBooking = bookingRepository.save(booking);

            lockingService.releaseLock(lockId, managerId);

            return convertToDto(savedBooking);

        } catch (RuntimeException e) {
            if (lockId != null) {
                try {
                    lockingService.releaseLock(lockId, managerId);
                } catch (Exception lockException) {
                    System.err.println("Error releasing lock: " + lockException.getMessage());
                }
            }
            throw e;
        }
    }

    @Override
    public List<BookingResponseDto> getBookingsByManager(UUID managerId) {
        return bookingRepository.findByUserId(managerId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookingResponseDto getBookingById(UUID bookingId) {
        Booking booking = bookingRepository.findByBookingId(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        return convertToDto(booking);
    }

    @Override
    public void cancelBooking(UUID bookingId) {
        Booking booking = bookingRepository.findByBookingId(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if ("CANCELLED".equals(booking.getStatus())) {
            throw new RuntimeException("Booking is already cancelled");
        }

        User manager = userRepository.findById(booking.getUserId())
                .orElseThrow(() -> new RuntimeException("Manager not found"));
        manager.setCredits(manager.getCredits() + booking.getTotalCredits());
        userRepository.save(manager);

        ManagerCreditSummary creditSummary = managerCreditSummaryRepository.findByUserId(booking.getUserId())
                .orElseThrow(() -> new RuntimeException("Credit summary not found"));
        creditSummary.setTotalCredits(manager.getCredits());
        creditSummary.setCreditsUsed(Math.max(0, creditSummary.getCreditsUsed() - booking.getTotalCredits()));
        managerCreditSummaryRepository.save(creditSummary);

        booking.setStatus("CANCELLED");
        booking.setCancelledAt(LocalDateTime.now());
        bookingRepository.save(booking);
    }

    @Override
    public List<TodayBookingsDto> getTodayBookingsByManager(UUID managerId, LocalDate date) {
        return bookingRepository.findByUserIdAndMeetingDateAndStatus(managerId, date, "ACTIVE").stream()
                .map(this::convertToTodayBookingDto)
                .collect(Collectors.toList());
    }

    @Override
    public CheckInResponseDto checkIn(UUID managerId, UUID bookingId) {
        User manager = userRepository.findById(managerId)
                .orElseThrow(() -> new RuntimeException("Manager not found"));

        Booking booking = bookingRepository.findByBookingId(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (!booking.getUserId().equals(managerId)) {
            throw new RuntimeException("Unauthorized: This booking does not belong to you");
        }

        if ("CHECKED_IN".equals(booking.getCheckInStatus())) {
            throw new RuntimeException("Already checked in for this booking");
        }

        booking.setCheckInStatus("CHECKED_IN");
        bookingRepository.save(booking);

        return CheckInResponseDto.builder()
                .bookingId(booking.getBookingId())
                .roomName(booking.getRoomName())
                .meetingTitle(booking.getMeetingTitle())
                .meetingDate(booking.getMeetingDate())
                .startTime(booking.getStartTime())
                .endTime(booking.getEndTime())
                .checkInStatus("CHECKED_IN")
                .penaltyApplied(false)
                .penaltyAmount(0)
                .remainingCredits(manager.getCredits())
                .message("Check-in successful")
                .build();
    }

    /**
     * Apply penalty for no-show (when end time has passed without check-in)
     */
    public void applyNoShowPenalty(UUID bookingId) {
        Booking booking = bookingRepository.findByBookingId(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));


        if ("PENDING".equals(booking.getCheckInStatus()) && LocalDateTime.now().isAfter(booking.getEndTime())) {
            User manager = userRepository.findById(booking.getUserId())
                    .orElseThrow(() -> new RuntimeException("Manager not found"));

            manager.setCredits(manager.getCredits() - PENALTY_AMOUNT);
            userRepository.save(manager);

            booking.setCheckInStatus("NO_SHOW");
            booking.setPenaltyApplied(true);
            booking.setPenaltyAmount(PENALTY_AMOUNT);
            bookingRepository.save(booking);

            ManagerCreditSummary creditSummary = managerCreditSummaryRepository.findByUserId(booking.getUserId())
                    .orElseThrow(() -> new RuntimeException("Credit summary not found"));
            creditSummary.setTotalCredits(manager.getCredits());
            creditSummary.setPenalty(creditSummary.getPenalty() + PENALTY_AMOUNT);
            creditSummary.setCreditsUsed(creditSummary.getCreditsUsed() + PENALTY_AMOUNT);
            managerCreditSummaryRepository.save(creditSummary);
        }
    }

    @Override
    public List<BookingResponseDto> getBookingsByManagerNameAndDate(String managerName, LocalDate meetingDate) {
        List<Booking> bookings = bookingRepository.findByManagerNameAndDate(managerName, meetingDate);
        return bookings.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Convert MeetingRoom entity to DTO
     * Fetches amenities directly from database instead of lazy loading
     */
    private MeetingRoomDto convertToDto(MeetingRoom room) {
        if (room == null) {
            return null;
        }

        Set<String> amenityNames = new HashSet<>();
        try {
            List<Amenity> amenities = amenityRepository.findByRoomId(room.getRoomId());
            if (amenities != null && !amenities.isEmpty()) {
                amenityNames = amenities.stream()
                        .map(Amenity::getAmenityName)
                        .collect(Collectors.toSet());
            }
        } catch (Exception e) {
            System.err.println("Warning: Could not load amenities for room: " + room.getRoomId());
            amenityNames = new HashSet<>();
        }

        return MeetingRoomDto.builder()
                .roomId(room.getRoomId())
                .roomName(room.getRoomName())
                .roomType(room.getRoomType())
                .seatingCapacity(room.getSeatingCapacity())
                .perHourCost(room.getPerHourCost())
                .amenities(amenityNames)
                .roomCost(room.getRoomCost())
                .isActive(room.getIsActive())
                .build();
    }

    /**
     * Convert Booking entity to DTO
     */
    private BookingResponseDto convertToDto(Booking booking) {
        return BookingResponseDto.builder()
                .bookingId(booking.getBookingId())
                .roomId(booking.getRoomId())
                .roomName(booking.getRoomName())
                .meetingTitle(booking.getMeetingTitle())
                .meetingDate(booking.getMeetingDate())
                .meetingType(booking.getMeetingType())
                .startTime(booking.getStartTime())
                .endTime(booking.getEndTime())
                .totalCredits(booking.getTotalCredits())
                .status(booking.getStatus())
                .createdAt(booking.getCreatedAt())
                .build();
    }

    /**
     * Convert Booking entity to TodayBookingsDto
     */
    private TodayBookingsDto convertToTodayBookingDto(Booking booking) {
        return TodayBookingsDto.builder()
                .bookingId(booking.getBookingId())
                .roomName(booking.getRoomName())
                .meetingTitle(booking.getMeetingTitle())
                .meetingDate(booking.getMeetingDate())
                .startTime(booking.getStartTime())
                .endTime(booking.getEndTime())
                .meetingType(booking.getMeetingType())
                .totalCredits(booking.getTotalCredits())
                .checkInStatus(booking.getCheckInStatus())
                .build();
    }
}

