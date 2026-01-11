package com.wecode.bookit.controllers;

import com.wecode.bookit.dto.CreateAmenityDto;
import com.wecode.bookit.dto.CreateMeetingRoomDto;
import com.wecode.bookit.dto.MeetingRoomDto;
import com.wecode.bookit.dto.UpdateAmenityDto;
import com.wecode.bookit.dto.UpdateMeetingRoomDto;
import com.wecode.bookit.entity.Amenity;
import com.wecode.bookit.entity.Role;
import com.wecode.bookit.entity.User;
import com.wecode.bookit.handler.RoomHandler;
import com.wecode.bookit.repository.UserRepository;
import com.wecode.bookit.services.AmenityService;
import com.wecode.bookit.services.MeetingRoomService;
import com.wecode.bookit.validator.AdminValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    private final MeetingRoomService meetingRoomService;
    private final AmenityService amenityService;
    private final UserRepository userRepository;
    private final AdminValidator adminValidator;
    private final RoomHandler roomHandler;

    public AdminController(MeetingRoomService meetingRoomService, AmenityService amenityService,
                         UserRepository userRepository, AdminValidator adminValidator, RoomHandler roomHandler) {
        this.meetingRoomService = meetingRoomService;
        this.amenityService = amenityService;
        this.userRepository = userRepository;
        this.adminValidator = adminValidator;
        this.roomHandler = roomHandler;
    }

    private void verifyAdminRole(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getRole() != Role.ADMIN) {
            throw new RuntimeException("Access denied. Only admins can access this endpoint");
        }
    }

    @PostMapping("/createRoom")
    public ResponseEntity<MeetingRoomDto> createRoom(@RequestBody CreateMeetingRoomDto createRoomDto) {
        AdminValidator.ValidationResult validationResult = adminValidator.validateCreateRoom(createRoomDto);
        if (!validationResult.isValid()) {
            return roomHandler.handleValidationError(validationResult.getMessage());
        }
        verifyAdminRole(createRoomDto.getUserId());
        try {
            MeetingRoomDto room = meetingRoomService.createRoom(createRoomDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(roomHandler.buildCreateSuccessResponse(room));
        } catch (Exception e) {
            return roomHandler.handleRoomException(e);
        }
    }

    @PutMapping("/updateRoom")
    public ResponseEntity<MeetingRoomDto> updateRoom(@RequestBody UpdateMeetingRoomDto updateRoomDto) {
        AdminValidator.ValidationResult validationResult = adminValidator.validateUpdateRoom(updateRoomDto);
        if (!validationResult.isValid()) {
            return roomHandler.handleValidationError(validationResult.getMessage());
        }
        verifyAdminRole(updateRoomDto.getUserId());
        try {
            MeetingRoomDto room = meetingRoomService.updateRoom(updateRoomDto);
            return ResponseEntity.ok(roomHandler.buildUpdateSuccessResponse(room));
        } catch (Exception e) {
            return roomHandler.handleRoomException(e);
        }
    }

    @GetMapping("/getRoomById/{roomId}")
    public ResponseEntity<MeetingRoomDto> getRoomById(@PathVariable UUID roomId) {
        MeetingRoomDto room = meetingRoomService.getRoomById(roomId);
        return ResponseEntity.ok(room);
    }

    @GetMapping("/getAllRoom")
    public ResponseEntity<List<MeetingRoomDto>> getAllRooms() {
        List<MeetingRoomDto> rooms = meetingRoomService.getAllRooms();
        return ResponseEntity.ok(rooms);
    }

    @DeleteMapping("/rooms/{roomId}")
    public ResponseEntity<String> deleteRoom(@PathVariable UUID roomId) {
        meetingRoomService.deleteRoom(roomId);
        return ResponseEntity.ok("Room deleted successfully");
    }

    @PostMapping("/addAmenitie")
    public ResponseEntity<Amenity> createAmenity(@RequestBody CreateAmenityDto createAmenityDto) {
        AdminValidator.ValidationResult validationResult = adminValidator.validateCreateAmenity(createAmenityDto);
        if (!validationResult.isValid()) {
            return ResponseEntity.badRequest().build();
        }
        try {
            Amenity amenity = amenityService.createAmenity(createAmenityDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(amenity);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/updateAmenitie")
    public ResponseEntity<Amenity> updateAmenity(@RequestBody UpdateAmenityDto updateAmenityDto) {
        Amenity amenity = amenityService.updateAmenity(updateAmenityDto);
        return ResponseEntity.ok(amenity);
    }

    @GetMapping("/getAmenitieById/{amenityId}")
    public ResponseEntity<Amenity> getAmenityById(@PathVariable UUID amenityId) {
        Amenity amenity = amenityService.getAmenityById(amenityId);
        return ResponseEntity.ok(amenity);
    }

    @GetMapping("/getAllAmenities")
    public ResponseEntity<List<Amenity>> getAllAmenities() {
        List<Amenity> amenities = amenityService.getAllAmenities();
        return ResponseEntity.ok(amenities);
    }

    @DeleteMapping("/amenities/{amenityId}")
    public ResponseEntity<String> deleteAmenity(@PathVariable UUID amenityId) {
        amenityService.deleteAmenity(amenityId);
        return ResponseEntity.ok("Amenity deleted successfully");
    }
}
