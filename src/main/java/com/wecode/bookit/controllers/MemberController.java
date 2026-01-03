package com.wecode.bookit.controllers;

import com.wecode.bookit.dto.BookingResponseDto;
import com.wecode.bookit.entity.Role;
import com.wecode.bookit.entity.User;
import com.wecode.bookit.repository.UserRepository;
import com.wecode.bookit.services.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth/member")
@CrossOrigin(origins = "*")
public class MemberController {

    private final BookingService bookingService;
    private final UserRepository userRepository;

    public MemberController(BookingService bookingService, UserRepository userRepository) {
        this.bookingService = bookingService;
        this.userRepository = userRepository;
    }

    @GetMapping("/manager-meetings")
    public ResponseEntity<List<BookingResponseDto>> getMeetingsByManagerNameAndDate(
            //@RequestHeader("userId") UUID memberId,
            @RequestParam String managerName,
            @RequestParam LocalDate meetingDate) {

//        User member = userRepository.findById(memberId)
//                .orElseThrow(() -> new RuntimeException("Member not found"));

//        if (member.getRole() != Role.MEMBER) {
//            throw new RuntimeException("Access denied");
//        }

        User manager = userRepository.findByName(managerName)
                .orElseThrow(() -> new RuntimeException("Manager not found"));

        if (manager.getRole() != Role.MANAGER) {
            throw new RuntimeException("User is not a manager");
        }

        List<BookingResponseDto> meetings = bookingService.getBookingsByManagerNameAndDate(managerName, meetingDate);
        return ResponseEntity.ok(meetings);
    }
}
