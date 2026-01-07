package com.wecode.bookit.services.serviceImpl;

import com.wecode.bookit.dto.BookingRequestDto;
import com.wecode.bookit.dto.BookingResponseDto;
import com.wecode.bookit.dto.CheckInResponseDto;
import com.wecode.bookit.dto.TodayBookingsDto;
import com.wecode.bookit.dto.MeetingRoomDto;
import com.wecode.bookit.entity.Booking;
import com.wecode.bookit.entity.MeetingRoom;
import com.wecode.bookit.entity.ManagerCreditSummary;
import com.wecode.bookit.entity.User;
import com.wecode.bookit.repository.BookingRepository;
import com.wecode.bookit.repository.ManagerCreditSummaryRepository;
import com.wecode.bookit.repository.MeetingRoomRepository;
import com.wecode.bookit.repository.UserRepository;
import com.wecode.bookit.services.BookingService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    private static final Integer PENALTY_AMOUNT = 50;

    private final BookingRepository bookingRepository;
    private final MeetingRoomRepository meetingRoomRepository;
    private final UserRepository userRepository;
    private final ManagerCreditSummaryRepository managerCreditSummaryRepository;

    public BookingServiceImpl(BookingRepository bookingRepository,
                            MeetingRoomRepository meetingRoomRepository,
                            UserRepository userRepository,
                            ManagerCreditSummaryRepository managerCreditSummaryRepository) {
        this.bookingRepository = bookingRepository;
        this.meetingRoomRepository = meetingRoomRepository;
        this.userRepository = userRepository;
        this.managerCreditSummaryRepository = managerCreditSummaryRepository;
    }

    @Override
    public List<MeetingRoomDto> getAvailableMeetingRooms() {
        return meetingRoomRepository.findAll().stream()
                .filter(MeetingRoom::getIsActive)
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookingResponseDto bookRoom(UUID managerId, BookingRequestDto bookingRequestDto) {

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

        double durationInHours = Math.ceil(durationInMinutes / 60.0);
        Integer totalBookingCost = (int) (room.getRoomCost() * durationInHours);

        if (manager.getCredits() < totalBookingCost) {
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
        return convertToDto(savedBooking);
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
     * This should be called by a scheduled task for past bookings
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
     */
    private MeetingRoomDto convertToDto(MeetingRoom room) {
        return MeetingRoomDto.builder()
                .roomId(room.getRoomId())
                .roomName(room.getRoomName())
                .roomType(room.getRoomType())
                .seatingCapacity(room.getSeatingCapacity())
                .perHourCost(room.getPerHourCost())
                .amenities(room.getAmenities().stream()
                        .map(amenity -> amenity.getAmenityName())
                        .collect(Collectors.toSet()))
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

