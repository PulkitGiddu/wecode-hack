package com.wecode.bookit.validator;

import com.wecode.bookit.dto.UserDto;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    /**
     * Validates UserDto for sign up
     * @param userDto the user data transfer object
     * @return validation result
     */
    public ValidationResult validateSignUpRequest(UserDto userDto) {
        if (userDto == null) {
            return new ValidationResult(false, "User data is required");
        }

        if (userDto.getEmail() == null || userDto.getEmail().trim().isEmpty()) {
            return new ValidationResult(false, "Email is required");
        }

        if (!isValidEmail(userDto.getEmail())) {
            return new ValidationResult(false, "Email format is invalid");
        }

        if (userDto.getPassword() == null || userDto.getPassword().isEmpty()) {
            return new ValidationResult(false, "Password is required");
        }

        if (userDto.getPassword().length() < 6) {
            return new ValidationResult(false, "Password must be at least 6 characters long");
        }

        if (userDto.getName() == null || userDto.getName().trim().isEmpty()) {
            return new ValidationResult(false, "Name is required");
        }

        if (userDto.getRole() == null || userDto.getRole().trim().isEmpty()) {
            return new ValidationResult(false, "Role is required");
        }

        if (!isValidRole(userDto.getRole())) {
            return new ValidationResult(false, "Role must be one of: ADMIN, MANAGER, MEMBER");
        }

        return new ValidationResult(true, "Validation successful");
    }

    /**
     * Validates UserDto for login
     * @param userDto the user data transfer object
     * @return validation result
     */
    public ValidationResult validateLoginRequest(UserDto userDto) {
        if (userDto == null) {
            return new ValidationResult(false, "User data is required");
        }

        if (userDto.getEmail() == null || userDto.getEmail().trim().isEmpty()) {
            return new ValidationResult(false, "Email is required");
        }

        if (!isValidEmail(userDto.getEmail())) {
            return new ValidationResult(false, "Email format is invalid");
        }

        if (userDto.getPassword() == null || userDto.getPassword().isEmpty()) {
            return new ValidationResult(false, "Password is required");
        }

        return new ValidationResult(true, "Validation successful");
    }

    /**
     * Validates email format using a simple regex
     * @param email the email to validate
     * @return true if email is valid, false otherwise
     */
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex);
    }

    /**
     * Validates if the role is one of the allowed values
     * @param role the role to validate
     * @return true if role is valid, false otherwise
     */
    private boolean isValidRole(String role) {
        try {
            String upperRole = role.trim().toUpperCase();
            return upperRole.equals("ADMIN") || upperRole.equals("MANAGER") || upperRole.equals("MEMBER");
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Inner class to represent validation result
     */
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

