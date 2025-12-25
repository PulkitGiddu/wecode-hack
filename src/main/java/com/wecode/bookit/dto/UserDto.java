package com.wecode.bookit.dto;

import com.wecode.bookit.entity.User.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private UUID userId;
    private String name;
    private String email;
    private UserRole role;
    private Integer credits;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

