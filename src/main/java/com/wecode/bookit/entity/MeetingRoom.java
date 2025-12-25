package com.wecode.bookit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "meeting_rooms")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeetingRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "room_id", columnDefinition = "UUID")
    private UUID roomId;

    @Column(name = "room_name", nullable = false, unique = true, length = 100)
    private String roomName;

    @Column(name = "seating_capacity", nullable = false)
    private Integer seatingCapacity;

    @Column(name = "has_projector")
    private Boolean hasProjector = false;

    @Column(name = "has_wifi")
    private Boolean hasWifi = false;

    @Column(name = "has_conference_call")
    private Boolean hasConferenceCall = false;

    @Column(name = "has_whiteboard")
    private Boolean hasWhiteboard = false;

    @Column(name = "has_tv")
    private Boolean hasTv = false;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
