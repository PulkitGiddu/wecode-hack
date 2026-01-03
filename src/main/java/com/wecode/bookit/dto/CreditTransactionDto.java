package com.wecode.bookit.dto;

import com.wecode.bookit.entity.CreditTransaction.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditTransactionDto {

    private UUID transactionId;
    private UserDto user;
    private BookingDto booking;
    private Integer amount;
    private TransactionType transactionType;
    private String description;
    private LocalDateTime createdAt;
}

