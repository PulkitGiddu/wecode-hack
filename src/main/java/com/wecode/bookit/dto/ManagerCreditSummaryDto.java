package com.wecode.bookit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for manager credit summary response
 * Returns complete credit information for a manager
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ManagerCreditSummaryDto {
    private UUID userId;
    private String managerName;
    private String email;
    private Integer totalCredits;
    private Integer creditsUsed;
    private Integer penalty;
    private LocalDateTime lastResetAt;
    private LocalDateTime updatedAt;
    private String message;
    private Integer statusCode;
}

