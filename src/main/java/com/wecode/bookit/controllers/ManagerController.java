package com.wecode.bookit.controllers;

import com.wecode.bookit.dto.BookingRequestDto;
import com.wecode.bookit.dto.BookingResponseDto;
import com.wecode.bookit.dto.CheckInResponseDto;
import com.wecode.bookit.dto.TodayBookingsDto;
import com.wecode.bookit.dto.MeetingRoomDto;
import com.wecode.bookit.entity.Role;
import com.wecode.bookit.entity.User;
import com.wecode.bookit.repository.UserRepository;
import com.wecode.bookit.services.BookingService;
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

    public ManagerController(BookingService bookingService, UserRepository userRepository) {
        this.bookingService = bookingService;
        this.userRepository = userRepository;
    }

    private void verifyManagerRole(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() != Role.MANAGER) {
            throw new RuntimeException("Access denied. Only managers can access this endpoint");
        }
    }

    @GetMapping("/viewAvailableMeetingRoom")
    public ResponseEntity<List<MeetingRoomDto>> viewAvailableMeetingRooms(@RequestHeader("userId") UUID userId) {
        verifyManagerRole(userId);
        List<MeetingRoomDto> rooms = bookingService.getAvailableMeetingRooms();
        return ResponseEntity.ok(rooms);
    }

    @PostMapping("/bookRoom")
    public ResponseEntity<BookingResponseDto> bookRoom(
            @RequestHeader("userId") UUID managerId,
            @RequestBody BookingRequestDto bookingRequestDto) {
        verifyManagerRole(managerId);

        try {
            BookingResponseDto booking = bookingService.bookRoom(managerId, bookingRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(booking);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/myBookings")
    public ResponseEntity<List<BookingResponseDto>> getMyBookings(@RequestHeader("userId") UUID managerId) {
        verifyManagerRole(managerId);
        List<BookingResponseDto> bookings = bookingService.getBookingsByManager(managerId);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<BookingResponseDto> getBookingById(
            @RequestHeader("userId") UUID managerId,
            @PathVariable UUID bookingId) {
        verifyManagerRole(managerId);
        BookingResponseDto booking = bookingService.getBookingById(bookingId);
        return ResponseEntity.ok(booking);
    }

    @DeleteMapping("/booking/{bookingId}")
    public ResponseEntity<Map<String, String>> cancelBooking(
            @RequestHeader("userId") UUID managerId,
            @PathVariable UUID bookingId) {
        verifyManagerRole(managerId);

        try {
            bookingService.cancelBooking(bookingId);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Booking cancelled successfully");
            response.put("statusCode", String.valueOf(HttpStatus.OK.value()));
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<Map<String, Object>> getManagerProfile(@RequestHeader("userId") UUID managerId) {
        verifyManagerRole(managerId);

        User manager = userRepository.findById(managerId)
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
            @RequestHeader("userId") UUID managerId,
            @RequestParam LocalDate date) {
        verifyManagerRole(managerId);
        List<TodayBookingsDto> bookings = bookingService.getTodayBookingsByManager(managerId, date);
        return ResponseEntity.ok(bookings);
    }

    @PostMapping("/check-in/{bookingId}")
    public ResponseEntity<CheckInResponseDto> checkIn(
            @RequestHeader("userId") UUID managerId,
            @PathVariable UUID bookingId) {
        verifyManagerRole(managerId);

        try {
            CheckInResponseDto checkInResponse = bookingService.checkIn(managerId, bookingId);
            return ResponseEntity.ok(checkInResponse);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}

