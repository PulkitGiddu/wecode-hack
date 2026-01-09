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
public class BookingRequestDto {
    private UUID userId;
    private UUID roomId;
    private String meetingTitle;
    private LocalDate meetingDate;
    private String meetingType;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}

