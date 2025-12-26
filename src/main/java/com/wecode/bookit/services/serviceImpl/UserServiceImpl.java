package com.wecode.bookit.services.serviceImpl;

import com.wecode.bookit.dto.UserDto;
import com.wecode.bookit.entity.Role;
import com.wecode.bookit.entity.User;
import com.wecode.bookit.repository.UserRepository;
import com.wecode.bookit.services.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User signUp(UserDto userDto) {

        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setUserId(UUID.randomUUID());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());

        user.setPasswordHash(passwordEncoder.encode(userDto.getPassword()));

        Role role = null;
        if (userDto.getRole() != null && !userDto.getRole().isEmpty()) {
            role = Role.valueOf(userDto.getRole().toUpperCase());
        }
        user.setRole(role);

        if (role == Role.MANAGER) {
            user.setCredits(2000);
        } else {
            user.setCredits(0);
        }

        return userRepository.save(user);
    }
}