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
public class BookingDto {
    private UUID bookingId;
    private UUID roomId;
    private UUID userId;
    private String meetingTitle;
    private LocalDate meetingDate;
    private String meetingType;
    private String roomName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer totalCredits;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime cancelledAt;
}

