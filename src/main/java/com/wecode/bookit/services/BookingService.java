package com.wecode.bookit.services;

import com.wecode.bookit.dto.BookingRequestDto;
import com.wecode.bookit.dto.BookingResponseDto;
import com.wecode.bookit.dto.CheckInResponseDto;
import com.wecode.bookit.dto.TodayBookingsDto;
import com.wecode.bookit.dto.MeetingRoomDto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface BookingService {
    List<MeetingRoomDto> getAvailableMeetingRooms();
    BookingResponseDto bookRoom(UUID managerId, BookingRequestDto bookingRequestDto);
    List<BookingResponseDto> getBookingsByManager(UUID managerId);
    BookingResponseDto getBookingById(UUID bookingId);
    void cancelBooking(UUID bookingId);
    List<TodayBookingsDto> getTodayBookingsByManager(UUID managerId, LocalDate date);
    CheckInResponseDto checkIn(UUID managerId, UUID bookingId);
    List<BookingResponseDto> getBookingsByManagerNameAndDate(String managerName, LocalDate meetingDate);
}

