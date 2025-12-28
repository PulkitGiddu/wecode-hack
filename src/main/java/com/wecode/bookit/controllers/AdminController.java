package com.wecode.bookit.controllers;

import com.wecode.bookit.dto.CreateAmenityDto;
import com.wecode.bookit.dto.CreateMeetingRoomDto;
import com.wecode.bookit.dto.MeetingRoomDto;
import com.wecode.bookit.dto.UpdateAmenityDto;
import com.wecode.bookit.dto.UpdateMeetingRoomDto;
import com.wecode.bookit.entity.Amenity;
import com.wecode.bookit.services.AmenityService;
import com.wecode.bookit.services.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    private final RoomService roomService;
    private final AmenityService amenityService;

    public AdminController(RoomService roomService, AmenityService amenityService) {
        this.roomService = roomService;
        this.amenityService = amenityService;
    }

    // Meeting Room Endpoints

    @PostMapping("/createRoom")
    public ResponseEntity<MeetingRoomDto> createRoom(@RequestBody CreateMeetingRoomDto createRoomDto) {
        MeetingRoomDto room = roomService.createRoom(createRoomDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(room);
    }

    @PutMapping("/updateRoom")
    public ResponseEntity<MeetingRoomDto> updateRoom(@RequestBody UpdateMeetingRoomDto updateRoomDto) {
        MeetingRoomDto room = roomService.updateRoom(updateRoomDto);
        return ResponseEntity.ok(room);
    }

    @GetMapping("/getRoomById/{roomId}")
    public ResponseEntity<MeetingRoomDto> getRoomById(@PathVariable UUID roomId) {
        MeetingRoomDto room = roomService.getRoomById(roomId);
        return ResponseEntity.ok(room);
    }

    @GetMapping("/getAllRoom")
    public ResponseEntity<List<MeetingRoomDto>> getAllRooms() {
        List<MeetingRoomDto> rooms = roomService.getAllRooms();
        return ResponseEntity.ok(rooms);
    }

    @DeleteMapping("/rooms/{roomId}")
    public ResponseEntity<String> deleteRoom(@PathVariable UUID roomId) {
        roomService.deleteRoom(roomId);
        return ResponseEntity.ok("Room deleted successfully");
    }


    @PostMapping("/addAmenitie")
    public ResponseEntity<Amenity> createAmenity(@RequestBody CreateAmenityDto createAmenityDto) {
        Amenity amenity = amenityService.createAmenity(createAmenityDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(amenity);
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
