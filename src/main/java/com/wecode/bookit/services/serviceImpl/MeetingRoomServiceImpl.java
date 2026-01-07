package com.wecode.bookit.services.serviceImpl;

import com.wecode.bookit.dto.CreateMeetingRoomDto;
import com.wecode.bookit.dto.MeetingRoomDto;
import com.wecode.bookit.dto.UpdateMeetingRoomDto;
import com.wecode.bookit.entity.Amenity;
import com.wecode.bookit.entity.MeetingRoom;
import com.wecode.bookit.entity.SeatingCapacityCredits;
import com.wecode.bookit.exceptions.RoomAlreadyExistsException;
import com.wecode.bookit.exceptions.RoomNotFound;
import com.wecode.bookit.repository.AmenityRepository;
import com.wecode.bookit.repository.MeetingRoomRepository;
import com.wecode.bookit.repository.SeatingCapacityCreditsRepository;
import com.wecode.bookit.services.MeetingRoomService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MeetingRoomServiceImpl implements MeetingRoomService {

    private final MeetingRoomRepository meetingRoomRepository;
    private final AmenityRepository amenityRepository;
    private final SeatingCapacityCreditsRepository seatingCapacityCreditsRepository;

    public MeetingRoomServiceImpl(MeetingRoomRepository meetingRoomRepository,
                                  AmenityRepository amenityRepository,
                                  SeatingCapacityCreditsRepository seatingCapacityCreditsRepository) {
        this.meetingRoomRepository = meetingRoomRepository;
        this.amenityRepository = amenityRepository;
        this.seatingCapacityCreditsRepository = seatingCapacityCreditsRepository;
    }

    /**
     * Create a new meeting room
     */
    @Override
    public MeetingRoomDto createRoom(CreateMeetingRoomDto createRoomDto) {

        if (meetingRoomRepository.existsByRoomName(createRoomDto.getRoomName())) {
            throw new RoomAlreadyExistsException("Room with name '" + createRoomDto.getRoomName() + "' already exists");
        }

        Integer seatingCapacityCredits = getSeatingCapacityCredits(createRoomDto.getSeatingCapacity());

        Set<Amenity> amenities = new HashSet<>();
        Integer amenityCredits = 0;

        if (createRoomDto.getAmenities() != null && !createRoomDto.getAmenities().isEmpty()) {
            for (String amenityName : createRoomDto.getAmenities()) {
                Optional<Amenity> amenity = amenityRepository.findByAmenityName(amenityName);
                if (amenity.isEmpty() || !amenity.get().getIsActive()) {
                    throw new RuntimeException("Amenity not found or inactive: " + amenityName);
                }
                amenities.add(amenity.get());
                amenityCredits += amenity.get().getCreditCost();
            }
        }

        Integer totalRoomCost = seatingCapacityCredits + amenityCredits + createRoomDto.getPerHourCost();

        MeetingRoom room = new MeetingRoom();
        room.setRoomId(UUID.randomUUID());
        room.setRoomName(createRoomDto.getRoomName());
        room.setRoomType(createRoomDto.getRoomType());
        room.setSeatingCapacity(createRoomDto.getSeatingCapacity());
        room.setPerHourCost(createRoomDto.getPerHourCost());
        room.setAmenities(amenities);
        room.setRoomCost(totalRoomCost);
        room.setIsActive(true);

        MeetingRoom savedRoom = meetingRoomRepository.save(room);
        return convertToDto(savedRoom);
    }

    @Override
    public MeetingRoomDto updateRoom(UpdateMeetingRoomDto updateRoomDto) {

        MeetingRoom room = meetingRoomRepository.findByRoomName(updateRoomDto.getRoomName())
                .orElseThrow(() -> new RoomNotFound("Room not found"));

        if (updateRoomDto.getRoomName() != null && !updateRoomDto.getRoomName().isEmpty()
                && !updateRoomDto.getRoomName().equals(room.getRoomName())) {
            if (meetingRoomRepository.existsByRoomName(updateRoomDto.getRoomName())) {
                throw new RuntimeException("Room name already exists");
            }
            room.setRoomName(updateRoomDto.getRoomName());
        }

        if (updateRoomDto.getRoomType() != null && !updateRoomDto.getRoomType().isEmpty()) {
            room.setRoomType(updateRoomDto.getRoomType());
        }

        Integer seatingCapacityCredits;
        if (updateRoomDto.getSeatingCapacity() != null) {
            room.setSeatingCapacity(updateRoomDto.getSeatingCapacity());
            seatingCapacityCredits = getSeatingCapacityCredits(updateRoomDto.getSeatingCapacity());
        } else {
            seatingCapacityCredits = getSeatingCapacityCredits(room.getSeatingCapacity());
        }

        Integer perHourCost = updateRoomDto.getPerHourCost() != null ?
                updateRoomDto.getPerHourCost() : room.getPerHourCost();
        room.setPerHourCost(perHourCost);


        Integer amenityCredits = 0;
        if (updateRoomDto.getAmenities() != null && !updateRoomDto.getAmenities().isEmpty()) {
            Set<Amenity> amenities = new HashSet<>();
            for (String amenityName : updateRoomDto.getAmenities()) {
                Optional<Amenity> amenity = amenityRepository.findByAmenityName(amenityName);
                if (amenity.isEmpty() || !amenity.get().getIsActive()) {
                    throw new RuntimeException("Amenity not found or inactive: " + amenityName);
                }
                amenities.add(amenity.get());
                amenityCredits += amenity.get().getCreditCost();
            }
            room.setAmenities(amenities);
        } else {
            amenityCredits = room.getAmenities().stream()
                    .mapToInt(Amenity::getCreditCost)
                    .sum();
        }

        Integer totalRoomCost = seatingCapacityCredits + amenityCredits + perHourCost;
        room.setRoomCost(totalRoomCost);

        MeetingRoom updatedRoom = meetingRoomRepository.save(room);
        return convertToDto(updatedRoom);
    }

    @Override
    public MeetingRoomDto getRoomById(UUID roomId) {
        MeetingRoom room = meetingRoomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        return convertToDto(room);
    }

    @Override
    public List<MeetingRoomDto> getAllRooms() {
        return meetingRoomRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteRoom(UUID roomId) {
        MeetingRoom room = meetingRoomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        room.setIsActive(false);
        meetingRoomRepository.save(room);
    }

    /**
     * Get seating capacity credits based on capacity range
     */
    private Integer getSeatingCapacityCredits(Integer seatingCapacity) {
        Optional<SeatingCapacityCredits> credits = seatingCapacityCreditsRepository
                .findByCapacityRange(seatingCapacity);
        return credits.map(SeatingCapacityCredits::getCreditCost)
                .orElseThrow(() -> new RuntimeException("No credit mapping found for capacity: " + seatingCapacity));
    }

    /**
     * Convert MeetingRoom entity to DTO
     */
    private MeetingRoomDto convertToDto(MeetingRoom room) {
        Set<String> amenityNames = room.getAmenities().stream()
                .map(Amenity::getAmenityName)
                .collect(Collectors.toSet());

        return MeetingRoomDto.builder()
                .roomId(room.getRoomId())
                .roomName(room.getRoomName())
                .roomType(room.getRoomType())
                .seatingCapacity(room.getSeatingCapacity())
                .perHourCost(room.getPerHourCost())
                .amenities(amenityNames)
                .roomCost(room.getRoomCost())
                .isActive(room.getIsActive())
                .build();
    }
}

