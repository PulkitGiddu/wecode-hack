package com.wecode.bookit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO for manager profile response
 * Returns manager details with credit information
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ManagerProfileDto {
    private UUID userId;
    private String name;
    private String email;
    private String role;
    private Integer availableCredits;
    private Integer creditsUsed;
    private Integer penalty;
    private String message;
    private Integer statusCode;
}

