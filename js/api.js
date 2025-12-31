// API Configuration
const API_CONFIG = {
    BASE_URL: 'http://localhost:8081/api',
    ADMIN: '/admin',
    MANAGER: '/manager',
    MEMBER: '/member',
    USE_DUMMY_DATA: false // Set to true to use dummy data, false to use backend API
};

// ============================================
// ADMIN API - Rooms Management
// ============================================

/**
 * Get all rooms
 * @param {boolean} forceDummy - Force using dummy data
 * @returns {Promise<Array>} Array of room objects
 */
async function getAllRooms(forceDummy = false) {
    if (API_CONFIG.USE_DUMMY_DATA || forceDummy) {
        console.log('Using dummy data for rooms');
        return Promise.resolve(getDummyRooms());
    }
    
    try {
        const response = await fetch(`${API_CONFIG.BASE_URL}${API_CONFIG.ADMIN}/getAllRoom`);
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return await response.json();
    } catch (error) {
        console.error('Error fetching rooms, falling back to dummy data:', error);
        return getDummyRooms();
    }
}

/**
 * Get room by ID
 * @param {string} roomId - UUID of the room
 * @returns {Promise<Object>} Room object
 */
async function getRoomById(roomId) {
    try {
        const response = await fetch(`${API_CONFIG.BASE_URL}${API_CONFIG.ADMIN}/getRoomById/${roomId}`);
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return await response.json();
    } catch (error) {
        console.error('Error fetching room:', error);
        throw error;
    }
}

/**
 * Create a new room
 * @param {Object} roomData - Room data object
 * @param {string} roomData.name - Room name
 * @param {string} roomData.roomType - Type of room
 * @param {number} roomData.seatingCapacity - Seating capacity
 * @param {Array<string>} roomData.amenities - Array of amenity names
 * @param {number} roomData.perHourCost - Cost per hour
 * @returns {Promise<Object>} Created room object
 */
async function createRoom(roomData) {
    try {
        const response = await fetch(`${API_CONFIG.BASE_URL}${API_CONFIG.ADMIN}/createRoom`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(roomData)
        });
        
        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText || `HTTP error! status: ${response.status}`);
        }
        
        return await response.json();
    } catch (error) {
        console.error('Error creating room:', error);
        throw error;
    }
}

/**
 * Update an existing room
 * @param {Object} roomData - Room data object including roomId
 * @returns {Promise<Object>} Updated room object
 */
async function updateRoom(roomData) {
    try {
        const response = await fetch(`${API_CONFIG.BASE_URL}${API_CONFIG.ADMIN}/updateRoom`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(roomData)
        });
        
        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText || `HTTP error! status: ${response.status}`);
        }
        
        return await response.json();
    } catch (error) {
        console.error('Error updating room:', error);
        throw error;
    }
}

/**
 * Delete a room
 * @param {string} roomId - UUID of the room to delete
 * @returns {Promise<string>} Success message
 */
async function deleteRoom(roomId) {
    try {
        const response = await fetch(`${API_CONFIG.BASE_URL}${API_CONFIG.ADMIN}/rooms/${roomId}`, {
            method: 'DELETE'
        });
        
        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText || `HTTP error! status: ${response.status}`);
        }
        
        return await response.text();
    } catch (error) {
        console.error('Error deleting room:', error);
        throw error;
    }
}

// ============================================
// ADMIN API - Amenities Management
// ============================================

/**
 * Get all amenities
 * @param {boolean} forceDummy - Force using dummy data
 * @returns {Promise<Array>} Array of amenity objects
 */
async function getAllAmenities(forceDummy = false) {
    if (API_CONFIG.USE_DUMMY_DATA || forceDummy) {
        console.log('Using dummy data for amenities');
        return Promise.resolve(getDummyAmenities());
    }
    
    try {
        const response = await fetch(`${API_CONFIG.BASE_URL}${API_CONFIG.ADMIN}/getAllAmenities`);
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return await response.json();
    } catch (error) {
        console.error('Error fetching amenities, falling back to dummy data:', error);
        return getDummyAmenities();
    }
}

/**
 * Get amenity by ID
 * @param {string} amenityId - UUID of the amenity
 * @returns {Promise<Object>} Amenity object
 */
async function getAmenityById(amenityId) {
    try {
        const response = await fetch(`${API_CONFIG.BASE_URL}${API_CONFIG.ADMIN}/getAmenitieById/${amenityId}`);
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return await response.json();
    } catch (error) {
        console.error('Error fetching amenity:', error);
        throw error;
    }
}

/**
 * Create a new amenity
 * @param {Object} amenityData - Amenity data object
 * @param {string} amenityData.name - Amenity name
 * @param {string} amenityData.description - Amenity description
 * @param {number} amenityData.creditsScore - Credits score
 * @returns {Promise<Object>} Created amenity object
 */
async function createAmenity(amenityData) {
    try {
        const response = await fetch(`${API_CONFIG.BASE_URL}${API_CONFIG.ADMIN}/addAmenitie`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(amenityData)
        });
        
        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText || `HTTP error! status: ${response.status}`);
        }
        
        return await response.json();
    } catch (error) {
        console.error('Error creating amenity:', error);
        throw error;
    }
}

/**
 * Update an existing amenity
 * @param {Object} amenityData - Amenity data object including amenityId
 * @returns {Promise<Object>} Updated amenity object
 */
async function updateAmenity(amenityData) {
    try {
        const response = await fetch(`${API_CONFIG.BASE_URL}${API_CONFIG.ADMIN}/updateAmenitie`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(amenityData)
        });
        
        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText || `HTTP error! status: ${response.status}`);
        }
        
        return await response.json();
    } catch (error) {
        console.error('Error updating amenity:', error);
        throw error;
    }
}

/**
 * Delete an amenity
 * @param {string} amenityId - UUID of the amenity to delete
 * @returns {Promise<string>} Success message
 */
async function deleteAmenity(amenityId) {
    try {
        const response = await fetch(`${API_CONFIG.BASE_URL}${API_CONFIG.ADMIN}/amenities/${amenityId}`, {
            method: 'DELETE'
        });
        
        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText || `HTTP error! status: ${response.status}`);
        }
        
        return await response.text();
    } catch (error) {
        console.error('Error deleting amenity:', error);
        throw error;
    }
}

// ============================================
// DUMMY DATA (for fallback when API is unavailable)
// ============================================
// Data structure matches the backend database schema

const DUMMY_DATA = {
    // Amenities matching the backend database
    amenities: [
        { 
            amenity_id: 'a1e4f8c2-1234-4567-89ab-cdef12345671',
            amenityName: 'PROJECTOR',
            creditCost: 5,
            is_active: true
        },
        { 
            amenity_id: 'a1e4f8c2-1234-4567-89ab-cdef12345672',
            amenityName: 'WIFI',
            creditCost: 10,
            is_active: true
        },
        { 
            amenity_id: 'a1e4f8c2-1234-4567-89ab-cdef12345673',
            amenityName: 'CONFERENCE_CALL',
            creditCost: 15,
            is_active: true
        },
        { 
            amenity_id: 'a1e4f8c2-1234-4567-89ab-cdef12345674',
            amenityName: 'WHITEBOARD',
            creditCost: 5,
            is_active: true
        },
        { 
            amenity_id: 'a1e4f8c2-1234-4567-89ab-cdef12345675',
            amenityName: 'WATER_DISPENSER',
            creditCost: 5,
            is_active: true
        },
        { 
            amenity_id: 'a1e4f8c2-1234-4567-89ab-cdef12345676',
            amenityName: 'TV',
            creditCost: 10,
            is_active: true
        },
        { 
            amenity_id: 'a1e4f8c2-1234-4567-89ab-cdef12345677',
            amenityName: 'COFFEE_MACHINE',
            creditCost: 10,
            is_active: true
        }
    ],
    
    // Sample rooms matching your provided JSON structure
    rooms: [
        {
            roomId: '649cc30f-622b-462d-8ff4-e3dafb2b9195',
            roomName: 'Bhimtal',
            roomType: 'Huddle',
            seatingCapacity: 20,
            perHourCost: 100,
            amenities: ['COFFEE_MACHINE', 'WIFI'],
            roomCost: 120, // perHourCost + sum of amenity costs (10 + 10)
            isActive: true
        },
        {
            roomId: '749cc30f-622b-462d-8ff4-e3dafb2b9196',
            roomName: 'Nainital',
            roomType: 'Conference',
            seatingCapacity: 50,
            perHourCost: 200,
            amenities: ['PROJECTOR', 'WIFI', 'WHITEBOARD', 'CONFERENCE_CALL'],
            roomCost: 235, // 200 + 5 + 10 + 5 + 15
            isActive: true
        },
        {
            roomId: '849cc30f-622b-462d-8ff4-e3dafb2b9197',
            roomName: 'Ranikhet',
            roomType: 'Meeting',
            seatingCapacity: 30,
            perHourCost: 150,
            amenities: ['TV', 'WIFI', 'WATER_DISPENSER'],
            roomCost: 175, // 150 + 10 + 10 + 5
            isActive: true
        },
        {
            roomId: '949cc30f-622b-462d-8ff4-e3dafb2b9198',
            roomName: 'Mussoorie',
            roomType: 'Board Room',
            seatingCapacity: 100,
            perHourCost: 300,
            amenities: ['PROJECTOR', 'CONFERENCE_CALL', 'TV', 'WIFI', 'COFFEE_MACHINE'],
            roomCost: 345, // 300 + 5 + 15 + 10 + 10 + 10
            isActive: true
        },
        {
            roomId: 'a49cc30f-622b-462d-8ff4-e3dafb2b9199',
            roomName: 'Dehradun',
            roomType: 'Huddle',
            seatingCapacity: 15,
            perHourCost: 80,
            amenities: ['WHITEBOARD', 'WIFI'],
            roomCost: 95, // 80 + 5 + 10
            isActive: true
        }
    ]
};

/**
 * Get dummy rooms data
 * @returns {Array} Array of dummy room objects
 */
function getDummyRooms() {
    return DUMMY_DATA.rooms;
}

/**
 * Get dummy amenities data
 * @returns {Array} Array of dummy amenity objects
 */
function getDummyAmenities() {
    return DUMMY_DATA.amenities;
}
// ============================================
// UTILITY FUNCTIONS FOR DATA MANAGEMENT
// ============================================

/**
 * Calculate room cost including amenities
 * @param {number} perHourCost - Base cost per hour
 * @param {Array<string>} amenityNames - Array of amenity names
 * @returns {number} Total cost including amenities
 */
function calculateRoomCost(perHourCost, amenityNames = []) {
    const amenityCosts = DUMMY_DATA.amenities
        .filter(a => amenityNames.includes(a.amenityName))
        .reduce((sum, a) => sum + a.creditCost, 0);
    
    return perHourCost + amenityCosts;
}

/**
 * Get amenity details by name
 * @param {string} amenityName - Name of the amenity
 * @returns {Object|null} Amenity object or null if not found
 */
function getAmenityByName(amenityName) {
    return DUMMY_DATA.amenities.find(a => a.amenityName === amenityName) || null;
}

/**
 * Format amenity name for display (e.g., 'COFFEE_MACHINE' -> 'Coffee Machine')
 * @param {string} amenityName - Amenity name in uppercase with underscores
 * @returns {string} Formatted amenity name
 */
function formatAmenityName(amenityName) {
    return amenityName
        .toLowerCase()
        .split('_')
        .map(word => word.charAt(0).toUpperCase() + word.slice(1))
        .join(' ');
}

/**
 * Toggle between dummy data and backend API
 * @param {boolean} useDummy - Whether to use dummy data
 */
function setDataSource(useDummy) {
    API_CONFIG.USE_DUMMY_DATA = useDummy;
    console.log(`Data source set to: ${useDummy ? 'Dummy Data' : 'Backend API'}`);
}

/**
 * Get current data source
 * @returns {string} Current data source ('dummy' or 'backend')
 */
function getDataSource() {
    return API_CONFIG.USE_DUMMY_DATA ? 'dummy' : 'backend';
}

// ============================================
// MANAGER API - Profile Management
// ============================================

/**
 * Get manager profile
 * @returns {Promise<Object>} Manager profile object with availableCredits, role, name, userId, email
 */
async function getManagerProfile() {
    const url = `${API_CONFIG.BASE_URL}${API_CONFIG.MANAGER}/profile`;
    console.log('🌐 Fetching manager profile from:', url);
    
    try {
        console.log('📡 Making fetch request...');
        const response = await fetch(url);
        
        console.log('📥 Response status:', response.status);
        console.log('📥 Response ok:', response.ok);
        
        if (!response.ok) {
            const errorText = await response.text();
            console.error('❌ HTTP error response:', errorText);
            throw new Error(`HTTP error! status: ${response.status}, message: ${errorText}`);
        }
        
        const data = await response.json();
        console.log('✅ Profile data received:', data);
        return data;
    } catch (error) {
        console.error('❌ Error fetching manager profile:', error);
        console.error('Error details:', {
            name: error.name,
            message: error.message,
            stack: error.stack
        });
        
        // Fallback to dummy data
        console.warn('⚠️ Using fallback dummy data');
        const fallbackData = {
            availableCredits: 10,
            role: 'MANAGER',
            name: 'John Doe',
            userId: '123e4567-e89b-12d3-a456-426614174000',
            email: 'manager@example.com'
        };
        console.log('📦 Fallback data:', fallbackData);
        return fallbackData;
    }
}