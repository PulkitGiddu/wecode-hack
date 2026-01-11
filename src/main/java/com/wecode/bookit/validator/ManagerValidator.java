package com.wecode.bookit.validator;

import com.wecode.bookit.dto.BookingRequestDto;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Validator for Manager operations
 * Validates booking requests and check-in operations
 */
@Component
public class ManagerValidator {

    public ValidationResult validateBookingRequest(BookingRequestDto dto) {
        if (dto == null) {
            return new ValidationResult(false, "Booking data is required");
        }

        if (dto.getUserId() == null) {
            return new ValidationResult(false, "User ID is required");
        }

        if (dto.getRoomId() == null) {
            return new ValidationResult(false, "Room ID is required");
        }

        if (dto.getMeetingTitle() == null || dto.getMeetingTitle().trim().isEmpty()) {
            return new ValidationResult(false, "Meeting title is required");
        }

        if (dto.getMeetingTitle().length() < 3 || dto.getMeetingTitle().length() > 200) {
            return new ValidationResult(false, "Meeting title must be 3-200 characters");
        }

        if (dto.getMeetingDate() == null) {
            return new ValidationResult(false, "Meeting date is required");
        }

        if (dto.getMeetingDate().isBefore(LocalDate.now())) {
            return new ValidationResult(false, "Meeting date cannot be in the past");
        }

        if (dto.getMeetingDate().isAfter(LocalDate.now().plusDays(90))) {
            return new ValidationResult(false, "Cannot book more than 90 days in advance");
        }

        if (dto.getStartTime() == null || dto.getEndTime() == null) {
            return new ValidationResult(false, "Start and end times are required");
        }

        if (dto.getEndTime().isBefore(dto.getStartTime()) || dto.getEndTime().isEqual(dto.getStartTime())) {
            return new ValidationResult(false, "End time must be after start time");
        }

        if (!dto.getStartTime().toLocalDate().equals(dto.getEndTime().toLocalDate())) {
            return new ValidationResult(false, "Start and end times must be on the same day");
        }

        if (!dto.getStartTime().toLocalDate().equals(dto.getMeetingDate())) {
            return new ValidationResult(false, "Meeting time must match meeting date");
        }


        long minutesDifference = java.time.temporal.ChronoUnit.MINUTES.between(
                dto.getStartTime(), dto.getEndTime()
        );

        if (minutesDifference < 15) {
            return new ValidationResult(false, "Meeting must be at least 15 minutes");
        }

        if (minutesDifference > 480) { // 8 hours
            return new ValidationResult(false, "Meeting cannot exceed 8 hours");
        }

        if (dto.getMeetingType() == null || dto.getMeetingType().trim().isEmpty()) {
            return new ValidationResult(false, "Meeting type is required");
        }

        if (dto.getMeetingType().length() > 50) {
            return new ValidationResult(false, "Meeting type must be max 50 characters");
        }

        return new ValidationResult(true, "Validation successful");
    }


    public ValidationResult validateBookingId(UUID bookingId) {
        if (bookingId == null) {
            return new ValidationResult(false, "Booking ID is required");
        }
        return new ValidationResult(true, "Validation successful");
    }

    public ValidationResult validateUserId(UUID userId) {
        if (userId == null) {
            return new ValidationResult(false, "User ID is required");
        }
        return new ValidationResult(true, "Validation successful");
    }

    public ValidationResult validateMeetingDate(LocalDate date) {
        if (date == null) {
            return new ValidationResult(false, "Meeting date is required");
        }

        if (date.isBefore(LocalDate.now())) {
            return new ValidationResult(false, "Meeting date cannot be in the past");
        }

        if (date.isAfter(LocalDate.now().plusDays(90))) {
            return new ValidationResult(false, "Cannot book more than 90 days in advance");
        }

        return new ValidationResult(true, "Validation successful");
    }

    public ValidationResult validateTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime == null || endTime == null) {
            return new ValidationResult(false, "Start and end times are required");
        }

        if (endTime.isBefore(startTime) || endTime.isEqual(startTime)) {
            return new ValidationResult(false, "End time must be after start time");
        }

        if (!startTime.toLocalDate().equals(endTime.toLocalDate())) {
            return new ValidationResult(false, "Start and end times must be on the same day");
        }

        return new ValidationResult(true, "Validation successful");
    }

    @Getter
    public static class ValidationResult {
        private final boolean valid;
        private final String message;

        public ValidationResult(boolean valid, String message) {
            this.valid = valid;
            this.message = message;
        }
    }
}

