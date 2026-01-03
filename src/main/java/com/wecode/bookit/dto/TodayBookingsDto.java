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
public class TodayBookingsDto {
    private UUID bookingId;
    private String roomName;
    private String meetingTitle;
    private LocalDate meetingDate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String meetingType;
    private Integer totalCredits;
    private String checkInStatus;
}

