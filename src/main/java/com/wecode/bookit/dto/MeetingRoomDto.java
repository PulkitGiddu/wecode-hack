package com.wecode.bookit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeetingRoomDto {
    private UUID roomId;
    private String roomName;
    private String roomType;
    private Integer seatingCapacity;
    private Integer perHourCost;
    private Set<String> amenities;
    private Integer roomCost;
    private Boolean isActive;
}

