package com.wecode.bookit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeetingRoomDto {
    private UUID roomId;
    private String roomName;
    private Integer seatingCapacity;
    private Boolean hasProjector;
    private Boolean hasWifi;
    private Boolean hasConferenceCall;
    private Boolean hasWhiteboard;
    private Boolean hasTv;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

