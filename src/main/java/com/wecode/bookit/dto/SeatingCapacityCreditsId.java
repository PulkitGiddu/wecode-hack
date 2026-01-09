package com.wecode.bookit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatingCapacityCreditsId implements Serializable {
    private Integer minCapacity;
    private Integer maxCapacity;
}

