package com.wecode.bookit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for API error responses
 * Used consistently across all error scenarios
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponseDto {
    private int statusCode;
    private String message;
    private String errorType;
    private String details;
    private LocalDateTime timestamp;
    private String path;
}

