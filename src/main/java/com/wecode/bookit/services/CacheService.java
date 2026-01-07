package com.wecode.bookit.services;

import com.wecode.bookit.entity.Amenity;
import com.wecode.bookit.entity.MeetingRoom;
import com.wecode.bookit.entity.SeatingCapacityCredits;
import com.wecode.bookit.repository.AmenityRepository;
import com.wecode.bookit.repository.MeetingRoomRepository;
import com.wecode.bookit.repository.SeatingCapacityCreditsRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class CacheService {

    private static final Logger logger = LoggerFactory.getLogger(CacheService.class);
    private static final long ROOM_CACHE_TTL_MINUTES = 10;

    private final AmenityRepository amenityRepository;
    private final SeatingCapacityCreditsRepository seatingCapacityRepository;
    private final MeetingRoomRepository meetingRoomRepository;

    private final Map<UUID, Amenity> amenityByIdCache = new ConcurrentHashMap<>();
    private final Map<String, Amenity> amenityByNameCache = new ConcurrentHashMap<>();
    private final List<Amenity> allAmenitiesCache = Collections.synchronizedList(new ArrayList<>());

    private final Map<String, SeatingCapacityCredits> seatingCapacityCache = new ConcurrentHashMap<>();
    private final List<SeatingCapacityCredits> allSeatingCapacityCache = Collections.synchronizedList(new ArrayList<>());

    private final Map<UUID, MeetingRoom> roomByIdCache = new ConcurrentHashMap<>();
    private final List<MeetingRoom> allRoomsCache = Collections.synchronizedList(new ArrayList<>());
    private final List<MeetingRoom> allActiveRoomsCache = Collections.synchronizedList(new ArrayList<>());
    private volatile LocalDateTime roomsCacheLastUpdated;

    public CacheService(AmenityRepository amenityRepository,
                       SeatingCapacityCreditsRepository seatingCapacityRepository,
                       MeetingRoomRepository meetingRoomRepository) {
        this.amenityRepository = amenityRepository;
        this.seatingCapacityRepository = seatingCapacityRepository;
        this.meetingRoomRepository = meetingRoomRepository;
    }

    /**
     * Load all caches on application startup
     */
    @PostConstruct
    public void initializeCaches() {
        logger.info("Initializing all caches on application startup...");
        loadAmenitiesCaches();
        loadSeatingCapacityCaches();
        loadRoomsCaches();
        logger.info("All caches initialized successfully!");
    }

    /**
     * Load all amenities into cache from database
     */
    private void loadAmenitiesCaches() {
        try {
            logger.debug("Loading amenities cache from database...");
            List<Amenity> amenities = amenityRepository.findAll();

            amenityByIdCache.clear();
            amenityByNameCache.clear();
            allAmenitiesCache.clear();

            for (Amenity amenity : amenities) {
                amenityByIdCache.put(amenity.getAmenityId(), amenity);
                amenityByNameCache.put(amenity.getAmenityName(), amenity);
                allAmenitiesCache.add(amenity);
            }

            logger.info("Loaded {} amenities into cache", amenities.size());
        } catch (Exception e) {
            logger.error("Error loading amenities cache", e);
        }
    }

    /**
     * Get amenity by ID from cache
     */
    public Amenity getAmenityById(UUID amenityId) {
        return amenityByIdCache.get(amenityId);
    }

    /**
     * Get amenity by name from cache
     */
    public Amenity getAmenityByName(String amenityName) {
        return amenityByNameCache.get(amenityName);
    }

    /**
     * Get all amenities from cache
     */
    public List<Amenity> getAllAmenities() {
        return new ArrayList<>(allAmenitiesCache);
    }

    /**
     * Invalidate amenities cache and reload from database
     */
    public void invalidateAmenityCaches() {
        logger.info("Invalidating amenities cache...");
        loadAmenitiesCaches();
    }

    /**
     * Load all seating capacity credits into cache from database
     */
    private void loadSeatingCapacityCaches() {
        try {
            logger.debug("Loading seating capacity cache from database...");
            List<SeatingCapacityCredits> credits = seatingCapacityRepository.findAll();

            seatingCapacityCache.clear();
            allSeatingCapacityCache.clear();

            for (SeatingCapacityCredits credit : credits) {
                String key = credit.getMinCapacity() + "-" + credit.getMaxCapacity();
                seatingCapacityCache.put(key, credit);
                allSeatingCapacityCache.add(credit);
            }

            logger.info("Loaded {} seating capacity mappings into cache", credits.size());
        } catch (Exception e) {
            logger.error("Error loading seating capacity cache", e);
        }
    }

    /**
     * Get seating capacity credits for given capacity from cache
     */
    public SeatingCapacityCredits getSeatingCapacityCredits(Integer capacity) {
        if (capacity == null) {
            return null;
        }

        return allSeatingCapacityCache.stream()
                .filter(c -> capacity >= c.getMinCapacity() && capacity <= c.getMaxCapacity())
                .findFirst()
                .orElse(null);
    }

    /**
     * Get all seating capacity credits from cache
     */
    public List<SeatingCapacityCredits> getAllSeatingCapacityCredits() {
        return new ArrayList<>(allSeatingCapacityCache);
    }

    /**
     * Invalidate seating capacity cache and reload from database
     */
    public void invalidateSeatingCapacityCaches() {
        logger.info("Invalidating seating capacity cache...");
        loadSeatingCapacityCaches();
    }

    /**
     * Load all meeting rooms into cache from database
     */
    private void loadRoomsCaches() {
        try {
            logger.debug("Loading meeting rooms cache from database...");
            List<MeetingRoom> rooms = meetingRoomRepository.findAll();

            roomByIdCache.clear();
            allRoomsCache.clear();
            allActiveRoomsCache.clear();

            for (MeetingRoom room : rooms) {
                roomByIdCache.put(room.getRoomId(), room);
                allRoomsCache.add(room);

                if (room.getIsActive()) {
                    allActiveRoomsCache.add(room);
                }
            }

            roomsCacheLastUpdated = LocalDateTime.now();
            logger.info("Loaded {} meeting rooms into cache ({} active)", rooms.size(), allActiveRoomsCache.size());
        } catch (Exception e) {
            logger.error("Error loading meeting rooms cache", e);
        }
    }

    /**
     * Get room by ID from cache (with automatic refresh if TTL expired)
     */
    public MeetingRoom getRoomById(UUID roomId) {
        refreshRoomsCacheIfExpired();
        return roomByIdCache.get(roomId);
    }

    /**
     * Get all rooms from cache (with automatic refresh if TTL expired)
     */
    public List<MeetingRoom> getAllRooms() {
        refreshRoomsCacheIfExpired();
        return new ArrayList<>(allRoomsCache);
    }

    /**
     * Get all active rooms from cache (with automatic refresh if TTL expired)
     */
    public List<MeetingRoom> getAllActiveRooms() {
        refreshRoomsCacheIfExpired();
        return new ArrayList<>(allActiveRoomsCache);
    }

    /**
     * Invalidate room cache for specific room (and reload all if null)
     */
    public void invalidateRoomCache(UUID roomId) {
        if (roomId != null) {
            logger.info("Invalidating cache for room: {}", roomId);
            roomByIdCache.remove(roomId);
        }
        loadRoomsCaches();
    }

    /**
     * Check if room cache has expired and refresh if needed
     * TTL: 10 minutes
     */
    private void refreshRoomsCacheIfExpired() {
        if (roomsCacheLastUpdated == null) {
            loadRoomsCaches();
            return;
        }

        LocalDateTime expiryTime = roomsCacheLastUpdated.plusMinutes(ROOM_CACHE_TTL_MINUTES);
        if (LocalDateTime.now().isAfter(expiryTime)) {
            logger.debug("Room cache TTL expired, refreshing from database...");
            loadRoomsCaches();
        }
    }

    /**
     * cache statistics for monitoring
     */
    public Map<String, Object> getCacheStatistics() {
        Map<String, Object> stats = new LinkedHashMap<>();

        stats.put("amenities", Map.of(
            "count", allAmenitiesCache.size(),
            "status", "PERMANENT (no TTL)"
        ));

        stats.put("seatingCapacity", Map.of(
            "count", allSeatingCapacityCache.size(),
            "status", "PERMANENT (no TTL)"
        ));

        stats.put("rooms", Map.of(
            "totalCount", allRoomsCache.size(),
            "activeCount", allActiveRoomsCache.size(),
            "ttlMinutes", ROOM_CACHE_TTL_MINUTES,
            "lastUpdated", roomsCacheLastUpdated,
            "status", isRoomsCacheExpired() ? "EXPIRED" : "VALID"
        ));

        return stats;
    }

    /**
     * Check if room cache is expired
     */
    private boolean isRoomsCacheExpired() {
        if (roomsCacheLastUpdated == null) {
            return true;
        }
        LocalDateTime expiryTime = roomsCacheLastUpdated.plusMinutes(ROOM_CACHE_TTL_MINUTES);
        return LocalDateTime.now().isAfter(expiryTime);
    }
}

