package com.wecode.bookit.entity;

import com.wecode.bookit.dto.SeatingCapacityCreditsId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "seating_capacity_credits")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(SeatingCapacityCreditsId.class)
public class SeatingCapacityCredits {

    @Id
    @Column(name = "min_capacity")
    private Integer minCapacity;

    @Id
    @Column(name = "max_capacity")
    private Integer maxCapacity;

    @Column(name = "credit_cost", nullable = false)
    private Integer creditCost;
}

