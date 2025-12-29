package com.wecode.bookit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckInResponseDto {
    private UUID bookingId;
    private String roomName;
    private String meetingTitle;
    private LocalDate meetingDate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String checkInStatus;
    private Boolean penaltyApplied;
    private Integer penaltyAmount;
    private Integer remainingCredits;
    private String message;
}

