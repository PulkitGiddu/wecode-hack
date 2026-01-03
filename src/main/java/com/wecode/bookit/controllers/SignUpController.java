package com.wecode.bookit.controllers;

import com.wecode.bookit.dto.LoginResponseDto;
import com.wecode.bookit.dto.SignUpResponseDto;
import com.wecode.bookit.dto.UserDto;
import com.wecode.bookit.entity.User;
import com.wecode.bookit.services.UserService;
import com.wecode.bookit.validator.UserValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class SignUpController {

    private final UserService userService;
    private final UserValidator userValidator;

    public SignUpController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @PostMapping("/signUp")
    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody UserDto userDto) {
        UserValidator.ValidationResult validationResult = userValidator.validateSignUpRequest(userDto);
        if (!validationResult.isValid()) {
            return ResponseEntity.badRequest().body(
                SignUpResponseDto.builder()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .message(validationResult.getMessage())
                    .build()
            );
        }

        try {
            User user = userService.signUp(userDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                SignUpResponseDto.builder()
                    .statusCode(HttpStatus.CREATED.value())
                    .message("User registered successfully")
                    .name(user.getName())
                    .role(user.getRole().toString())
                    .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                SignUpResponseDto.builder()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .message(e.getMessage())
                    .build()
            );
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody UserDto userDto) {
        UserValidator.ValidationResult validationResult = userValidator.validateLoginRequest(userDto);
        if (!validationResult.isValid()) {
            return ResponseEntity.badRequest().body(
                LoginResponseDto.builder()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .error(validationResult.getMessage())
                    .build()
            );
        }

        try {
            User user = userService.login(userDto);
            return ResponseEntity.ok(
                LoginResponseDto.builder()
                    .statusCode(HttpStatus.OK.value())
                    .message("Login successful")
                    .email(user.getEmail())
                    .role(user.getRole().toString())
                    .build()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                LoginResponseDto.builder()
                    .statusCode(HttpStatus.UNAUTHORIZED.value())
                    .error(e.getMessage())
                    .build()
            );
        }
    }
}

