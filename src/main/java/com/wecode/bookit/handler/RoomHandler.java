package com.wecode.bookit.handler;

import com.wecode.bookit.dto.MeetingRoomDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * Handles all Room-related operations and error responses
 * Centralizes room logic away from controller
 */
@Component
public class RoomHandler {

    public ResponseEntity<MeetingRoomDto> handleRoomError(String message) {
        MeetingRoomDto errorResponse = new MeetingRoomDto();
        errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(message);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    public ResponseEntity<MeetingRoomDto> handleValidationError(String validationMessage) {
        return handleRoomError(validationMessage);
    }

    public MeetingRoomDto buildCreateSuccessResponse(MeetingRoomDto room) {
        room.setStatusCode(HttpStatus.CREATED.value());
        room.setMessage("Room created successfully");
        return room;
    }

    public MeetingRoomDto buildUpdateSuccessResponse(MeetingRoomDto room) {
        room.setStatusCode(HttpStatus.OK.value());
        room.setMessage("Room updated successfully");
        return room;
    }

    public ResponseEntity<MeetingRoomDto> handleRoomException(Exception e) {
        return handleRoomError(e.getMessage());
    }
}

