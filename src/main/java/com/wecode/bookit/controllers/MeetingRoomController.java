//package com.wecode.bookit.controllers;
//
//import com.wecode.bookit.dto.ApiResponse;
//import com.wecode.bookit.dto.MeetingRoomDTO;
//import com.wecode.bookit.service.MeetingRoomService;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/rooms")
//@CrossOrigin(origins = "*")
//@RequiredArgsConstructor
//public class MeetingRoomController {
//
//    private final MeetingRoomService roomService;
//
//    @GetMapping
//    public ResponseEntity<ApiResponse<List<MeetingRoomDTO>>> getAllRooms() {
//        try {
//            List<MeetingRoomDTO> rooms = roomService.getAllActiveRooms();
//            return ResponseEntity.ok(ApiResponse.success(rooms, "Rooms retrieved successfully"));
//        } catch (Exception e) {
//            return ResponseEntity.badRequest()
//                    .body(ApiResponse.error(e.getMessage()));
//        }
//    }
//
//    @GetMapping("/{roomId}")
//    public ResponseEntity<ApiResponse<MeetingRoomDTO>> getRoomById(@PathVariable Integer roomId) {
//        try {
//            MeetingRoomDTO room = roomService.getRoomById(roomId);
//            return ResponseEntity.ok(ApiResponse.success(room, "Room retrieved successfully"));
//        } catch (Exception e) {
//            return ResponseEntity.badRequest()
//                    .body(ApiResponse.error(e.getMessage()));
//        }
//    }
//
//    @PostMapping
//    public ResponseEntity<ApiResponse<MeetingRoomDTO>> createRoom(
//            @Valid @RequestBody MeetingRoomDTO request
//    ) {
//        try {
//            MeetingRoomDTO room = roomService.createRoom(request);
//            return ResponseEntity.status(HttpStatus.CREATED)
//                    .body(ApiResponse.success(room, "Room created successfully"));
//        } catch (Exception e) {
//            return ResponseEntity.badRequest()
//                    .body(ApiResponse.error(e.getMessage()));
//        }
//    }
//}
