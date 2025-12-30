package com.wecode.bookit.controllers;

import com.wecode.bookit.dto.CreateAmenityDto;
import com.wecode.bookit.dto.CreateMeetingRoomDto;
import com.wecode.bookit.dto.MeetingRoomDto;
import com.wecode.bookit.dto.UpdateAmenityDto;
import com.wecode.bookit.dto.UpdateMeetingRoomDto;
import com.wecode.bookit.entity.Amenity;
import com.wecode.bookit.services.AmenityService;
import com.wecode.bookit.services.MeetingRoomService;
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

    public AdminController(MeetingRoomService meetingRoomService, AmenityService amenityService) {
        this.meetingRoomService = meetingRoomService;
        this.amenityService = amenityService;
    }

    @PostMapping("/createRoom")
    public ResponseEntity<MeetingRoomDto> createRoom(@RequestBody CreateMeetingRoomDto createRoomDto) {
        try{
            MeetingRoomDto room = meetingRoomService.createRoom(createRoomDto);
            room.setStatusCode(HttpStatus.CREATED.value());
            room.setMessage("Room created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(room);
        }
        catch (Exception e){
        MeetingRoomDto errorResponse = new MeetingRoomDto();
        errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    }

    @PutMapping("/updateRoom")
    public ResponseEntity<MeetingRoomDto> updateRoom(@RequestBody UpdateMeetingRoomDto updateRoomDto) {
        try{
            MeetingRoomDto room = meetingRoomService.updateRoom(updateRoomDto);
            room.setStatusCode(HttpStatus.CREATED.value());
            room.setMessage("Room updated successfully");
        return ResponseEntity.ok(room);
        }
        catch (Exception e){
        MeetingRoomDto errorResponse = new MeetingRoomDto();
        errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    }

    @GetMapping("/getRoomById/{roomId}")
    public ResponseEntity<MeetingRoomDto> getRoomById(@PathVariable UUID roomId) {
        try {
            MeetingRoomDto room = meetingRoomService.getRoomById(roomId);
            room.setStatusCode(HttpStatus.OK.value());
            room.setMessage("Room fetched successfully");
            return ResponseEntity.ok(room);
        }
        catch (Exception e)
        {
            MeetingRoomDto errorResponse = new MeetingRoomDto();
            errorResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
            errorResponse.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
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
