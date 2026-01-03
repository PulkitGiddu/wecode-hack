package com.wecode.bookit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateAmenityDto {
    private UUID amenityId;
    private String amenityName;
    private Integer creditCost;
    private Boolean isActive;
}

