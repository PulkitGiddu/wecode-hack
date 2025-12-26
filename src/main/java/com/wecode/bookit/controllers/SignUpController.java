package com.wecode.bookit.controllers;

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
            SignUpResponseDto response = SignUpResponseDto.builder()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .message(validationResult.getMessage())
                    .build();
            return ResponseEntity.badRequest().body(response);
        }

        User user = userService.signUp(userDto);

        SignUpResponseDto response = SignUpResponseDto.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("User registered successfully")
                .name(user.getName())
                .role(user.getRole())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
