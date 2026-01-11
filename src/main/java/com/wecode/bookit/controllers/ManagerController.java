package com.wecode.bookit.controllers;

import com.wecode.bookit.dto.BookingRequestDto;
import com.wecode.bookit.dto.BookingResponseDto;
import com.wecode.bookit.dto.CheckInResponseDto;
import com.wecode.bookit.dto.TodayBookingsDto;
import com.wecode.bookit.dto.MeetingRoomDto;
import com.wecode.bookit.entity.Role;
import com.wecode.bookit.entity.User;
import com.wecode.bookit.handler.BookingHandler;
import com.wecode.bookit.repository.UserRepository;
import com.wecode.bookit.services.BookingService;
import com.wecode.bookit.validator.ManagerValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth/manager")
@CrossOrigin(origins = "*")
public class ManagerController {

    private final BookingService bookingService;
    private final UserRepository userRepository;
    private final ManagerValidator managerValidator;
    private final BookingHandler bookingHandler;

    public ManagerController(BookingService bookingService, UserRepository userRepository,
                           ManagerValidator managerValidator, BookingHandler bookingHandler) {
        this.bookingService = bookingService;
        this.userRepository = userRepository;
        this.managerValidator = managerValidator;
        this.bookingHandler = bookingHandler;
    }

    private void verifyManagerRole(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getRole() != Role.MANAGER) {
            throw new RuntimeException("Access denied. Only managers can access this endpoint");
        }
    }

    @GetMapping("/viewAvailableMeetingRoom")
    public ResponseEntity<List<MeetingRoomDto>> viewAvailableMeetingRooms(@RequestParam UUID userId) {
        verifyManagerRole(userId);
        List<MeetingRoomDto> rooms = bookingService.getAvailableMeetingRooms();
        return ResponseEntity.ok(rooms);
    }

    @PostMapping("/bookRoom")
    public ResponseEntity<BookingResponseDto> bookRoom(@RequestBody BookingRequestDto bookingRequestDto) {
        ManagerValidator.ValidationResult validationResult = managerValidator.validateBookingRequest(bookingRequestDto);
        if (!validationResult.isValid()) {
            return bookingHandler.handleValidationError(validationResult.getMessage());
        }
        verifyManagerRole(bookingRequestDto.getUserId());
        try {
            BookingResponseDto booking = bookingService.bookRoom(bookingRequestDto.getUserId(), bookingRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(bookingHandler.buildSuccessResponse(booking));
        } catch (RuntimeException e) {
            return bookingHandler.handleBookingException(e);
        }
    }

    @GetMapping("/myBookings")
    public ResponseEntity<List<BookingResponseDto>> getMyBookings(@RequestParam UUID userId) {
        verifyManagerRole(userId);
        List<BookingResponseDto> bookings = bookingService.getBookingsByManager(userId);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<BookingResponseDto> getBookingById(
            @RequestParam UUID userId,
            @PathVariable UUID bookingId) {
        verifyManagerRole(userId);
        BookingResponseDto booking = bookingService.getBookingById(bookingId);
        return ResponseEntity.ok(booking);
    }

    @DeleteMapping("/booking/{bookingId}")
    public ResponseEntity<String> cancelBooking(
            @RequestParam UUID userId,
            @PathVariable UUID bookingId) {
        verifyManagerRole(userId);
        bookingService.cancelBooking(bookingId);
        return ResponseEntity.ok("Booking cancelled successfully");
    }

    @GetMapping("/profile")
    public ResponseEntity<Map<String, Object>> getManagerProfile(@RequestParam UUID userId) {
        verifyManagerRole(userId);
        User manager = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Manager not found"));
        Map<String, Object> profile = new HashMap<>();
        profile.put("userId", manager.getUserId());
        profile.put("name", manager.getName());
        profile.put("email", manager.getEmail());
        profile.put("role", manager.getRole().toString());
        profile.put("availableCredits", manager.getCredits());
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/check-in/today-bookings")
    public ResponseEntity<List<TodayBookingsDto>> getTodayBookings(
            @RequestParam UUID userId,
            @RequestParam LocalDate date) {
        verifyManagerRole(userId);
        List<TodayBookingsDto> bookings = bookingService.getTodayBookingsByManager(userId, date);
        return ResponseEntity.ok(bookings);
    }

    @PostMapping("/check-in/{bookingId}")
    public ResponseEntity<CheckInResponseDto> checkIn(
            @RequestParam UUID userId,
            @PathVariable UUID bookingId) {
        ManagerValidator.ValidationResult userValidation = managerValidator.validateUserId(userId);
        if (!userValidation.isValid()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        ManagerValidator.ValidationResult bookingValidation = managerValidator.validateBookingId(bookingId);
        if (!bookingValidation.isValid()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        verifyManagerRole(userId);
        CheckInResponseDto checkInResponse = bookingService.checkIn(userId, bookingId);
        return ResponseEntity.ok(checkInResponse);
    }
}

