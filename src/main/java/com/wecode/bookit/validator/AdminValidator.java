package com.wecode.bookit.validator;

import com.wecode.bookit.dto.CreateAmenityDto;
import com.wecode.bookit.dto.CreateMeetingRoomDto;
import com.wecode.bookit.dto.UpdateMeetingRoomDto;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Validator for Admin operations
 * Validates room creation and amenity operations
 */
@Component
public class AdminValidator {

    public ValidationResult validateCreateRoom(CreateMeetingRoomDto dto) {
        if (dto == null) {
            return new ValidationResult(false, "Room data is required");
        }

        if (dto.getUserId() == null) {
            return new ValidationResult(false, "User ID is required");
        }

        if (dto.getRoomName() == null || dto.getRoomName().trim().isEmpty()) {
            return new ValidationResult(false, "Room name is required");
        }

        if (dto.getRoomName().length() < 2 || dto.getRoomName().length() > 100) {
            return new ValidationResult(false, "Room name must be 2-100 characters");
        }

        if (dto.getSeatingCapacity() == null || dto.getSeatingCapacity() <= 0) {
            return new ValidationResult(false, "Seating capacity must be greater than 0");
        }

        if (dto.getSeatingCapacity() > 1000) {
            return new ValidationResult(false, "Seating capacity cannot exceed 1000");
        }

        if (dto.getPerHourCost() == null || dto.getPerHourCost() < 0) {
            return new ValidationResult(false, "Per hour cost cannot be negative");
        }

        if (dto.getPerHourCost() > 10000) {
            return new ValidationResult(false, "Per hour cost is too high (max 10000)");
        }

        return new ValidationResult(true, "Validation successful");
    }


    public ValidationResult validateUpdateRoom(UpdateMeetingRoomDto dto) {
        if (dto == null) {
            return new ValidationResult(false, "Room data is required");
        }

        if (dto.getRoomId() == null) {
            return new ValidationResult(false, "Room ID is required");
        }

        if (dto.getUserId() == null) {
            return new ValidationResult(false, "User ID is required");
        }

        if (dto.getRoomName() != null && !dto.getRoomName().isEmpty()) {
            if (dto.getRoomName().length() < 2 || dto.getRoomName().length() > 100) {
                return new ValidationResult(false, "Room name must be 2-100 characters");
            }
        }

        if (dto.getSeatingCapacity() != null) {
            if (dto.getSeatingCapacity() <= 0 || dto.getSeatingCapacity() > 1000) {
                return new ValidationResult(false, "Seating capacity must be 1-1000");
            }
        }

        if (dto.getPerHourCost() != null) {
            if (dto.getPerHourCost() < 0 || dto.getPerHourCost() > 10000) {
                return new ValidationResult(false, "Per hour cost must be 0-10000");
            }
        }

        return new ValidationResult(true, "Validation successful");
    }

    public ValidationResult validateCreateAmenity(CreateAmenityDto dto) {
        if (dto == null) {
            return new ValidationResult(false, "Amenity data is required");
        }

        if (dto.getAmenityName() == null || dto.getAmenityName().trim().isEmpty()) {
            return new ValidationResult(false, "Amenity name is required");
        }

        if (dto.getAmenityName().length() < 2 || dto.getAmenityName().length() > 50) {
            return new ValidationResult(false, "Amenity name must be 2-50 characters");
        }

        if (dto.getCreditCost() == null || dto.getCreditCost() < 0) {
            return new ValidationResult(false, "Credit cost cannot be negative");
        }

        if (dto.getCreditCost() > 5000) {
            return new ValidationResult(false, "Credit cost is too high (max 5000)");
        }

        return new ValidationResult(true, "Validation successful");
    }

    public ValidationResult validateRoomId(UUID roomId) {
        if (roomId == null) {
            return new ValidationResult(false, "Room ID is required");
        }
        return new ValidationResult(true, "Validation successful");
    }

    public ValidationResult validateAmenityId(UUID amenityId) {
        if (amenityId == null) {
            return new ValidationResult(false, "Amenity ID is required");
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

