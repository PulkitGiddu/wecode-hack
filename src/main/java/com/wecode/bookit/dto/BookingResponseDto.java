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
public class BookingResponseDto {
    private Integer statusCode;
    private String message;
    private UUID bookingId;
    private UUID roomId;
    private String roomName;
    private String meetingTitle;
    private LocalDate meetingDate;
    private String meetingType;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer totalCredits;
    private String status;
    private LocalDateTime createdAt;
}

