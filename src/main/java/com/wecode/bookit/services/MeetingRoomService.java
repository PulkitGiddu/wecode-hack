package com.wecode.bookit.services;

import com.wecode.bookit.dto.CreateMeetingRoomDto;
import com.wecode.bookit.dto.MeetingRoomDto;
import com.wecode.bookit.dto.UpdateMeetingRoomDto;

import java.util.List;
import java.util.UUID;

public interface MeetingRoomService {
    MeetingRoomDto createRoom(CreateMeetingRoomDto createRoomDto);
    MeetingRoomDto updateRoom(UpdateMeetingRoomDto updateRoomDto);
    MeetingRoomDto getRoomById(UUID roomId);
    List<MeetingRoomDto> getAllRooms();
    void deleteRoom(UUID roomId);


}

