package com.wecode.bookit.services.serviceImpl;

import com.wecode.bookit.dto.UserDto;
import com.wecode.bookit.entity.Role;
import com.wecode.bookit.entity.User;
import com.wecode.bookit.exceptions.BadCredentialsException;
import com.wecode.bookit.exceptions.UserAlreadyExistsException;
import com.wecode.bookit.repository.UserRepository;
import com.wecode.bookit.services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder=passwordEncoder;
    }

    @Override
    @Transactional
    public User signUp(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new UserAlreadyExistsException("Email already registered");
        }

        User user = new User();
        user.setUserId(UUID.randomUUID());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPasswordHash(passwordEncoder.encode(userDto.getPassword()));

        try {
            Role role = Role.valueOf(userDto.getRole().toUpperCase());
            user.setRole(role);
            user.setCredits(role == Role.MANAGER ? 2000 : 0);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role provided");
        }

        return userRepository.save(user);
    }

    @Override
    public User login(UserDto userDto) {
        User user = userRepository.findByEmail(userDto.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(userDto.getPassword(), user.getPasswordHash())) {
            throw new BadCredentialsException("Invalid email or password");
        }

        return user;
    }
}

