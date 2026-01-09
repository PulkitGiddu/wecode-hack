package com.wecode.bookit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "amenities")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Amenity {

    @Id
    @UuidGenerator
    @Column(name = "amenity_id")
    private UUID amenityId;

    @Column(name = "amenity_name", nullable = false, unique = true, length = 50)
    private String amenityName;

    @Column(name = "credit_cost", nullable = false)
    private Integer creditCost;

    @Column(name = "is_active")
    private Boolean isActive = true;
}

