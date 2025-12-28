package com.wecode.bookit.controllers;

import com.wecode.bookit.dto.LoginRequest;
import com.wecode.bookit.dto.UserDto;
import com.wecode.bookit.entity.User;
import com.wecode.bookit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private UserRepository userRepository; //Repository

    //Create the tool to check hashes
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDto userDto) {

        Optional<User> userOptional = userRepository.findByEmail(userDto.getEmail());

        if (userOptional.isPresent() && passwordEncoder.matches(userDto.getPassword(), userOptional.get().getPasswordHash())) {
            return ResponseEntity.ok("Login successful");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }

}