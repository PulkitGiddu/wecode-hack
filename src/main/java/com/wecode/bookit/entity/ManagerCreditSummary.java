package com.wecode.bookit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "manager_credit_summary")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManagerCreditSummary {

    @Id
    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "manager_name", nullable = false)
    private String managerName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "total_credits", nullable = false)
    private Integer totalCredits = 2000;

    @Column(name = "credits_used", nullable = false)
    private Integer creditsUsed = 0;

    @Column(name = "penalty", nullable = false)
    private Integer penalty = 0;

    @Column(name = "last_reset_at")
    private LocalDateTime lastResetAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}

