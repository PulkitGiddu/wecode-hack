package com.wecode.bookit.handler;

import com.wecode.bookit.dto.BookingResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * Handles all Booking-related operations and error responses
 * Centralizes booking logic away from controller
 */
@Component
public class BookingHandler {

    public ResponseEntity<BookingResponseDto> handleBookingError(String message) {
        BookingResponseDto errorResponse = new BookingResponseDto();
        errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(message);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    public ResponseEntity<BookingResponseDto> handleValidationError(String validationMessage) {
        return handleBookingError(validationMessage);
    }

    public BookingResponseDto buildSuccessResponse(BookingResponseDto booking) {
        booking.setStatusCode(HttpStatus.CREATED.value());
        booking.setMessage("Room booked successfully");
        return booking;
    }

    public ResponseEntity<BookingResponseDto> handleBookingException(Exception e) {
        return handleBookingError(e.getMessage());
    }
}

