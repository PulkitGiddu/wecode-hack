package com.wecode.bookit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "booking_locks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingLock {

    @Id
    private UUID lockId;

    @Column(nullable = false)
    private UUID roomId;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private LocalDate meetingDate;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Column(nullable = false)
    private LocalDateTime lockedAt;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LockStatus status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public BookingLock(UUID lockId, UUID roomId, UUID userId, LocalDate meetingDate,
                      LocalDateTime startTime, LocalDateTime endTime, LocalDateTime expiresAt) {
        this.lockId = lockId;
        this.roomId = roomId;
        this.userId = userId;
        this.meetingDate = meetingDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.expiresAt = expiresAt;
        this.lockedAt = LocalDateTime.now();
        this.status = LockStatus.ACTIVE;
        this.createdAt = LocalDateTime.now();
    }

    public enum LockStatus {
        ACTIVE, RELEASED, EXPIRED
    }
    }
