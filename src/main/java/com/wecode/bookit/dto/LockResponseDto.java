package com.wecode.bookit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for lock response and lock status information
 * Used when returning lock details to client
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LockResponseDto {
    private UUID lockId;
    private UUID roomId;
    private UUID userId;
    private LocalDateTime expiresAt;
    private String status; // ACTIVE, RELEASED, EXPIRED
    private long timeRemainingMinutes;
    private String message;
    private Integer statusCode;
}

