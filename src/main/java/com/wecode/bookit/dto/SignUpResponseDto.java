package com.wecode.bookit.dto;

import com.wecode.bookit.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpResponseDto {
    private int statusCode;
    private String message;
    private String name;
    private Role role;
}

