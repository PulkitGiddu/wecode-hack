package com.wecode.bookit.dto;

import com.wecode.bookit.entity.Booking.MeetingType;
import com.wecode.bookit.entity.Booking.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {
    private UUID bookingId;
    private MeetingRoomDto room;
    private UserDto user;
    private String meetingTitle;
    private MeetingType meetingType;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer totalCredits;
    private BookingStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime cancelledAt;
}

