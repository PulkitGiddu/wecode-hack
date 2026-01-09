// API Configuration
const API_BASE_URL = 'http://localhost:8081/api';

// API Endpoints
const API_ENDPOINTS = {
    AUTH: {
        SIGNUP: '/auth/signUp',
        LOGIN: '/auth/login',
        LOGOUT: '/auth/logout'
    },
    ADMIN: {
        // Rooms
        GET_ALL_ROOMS: '/admin/getAllRoom',
        GET_ROOM_BY_ID: '/admin/getRoomById/{roomId}',
        CREATE_ROOM: '/admin/createRoom',
        UPDATE_ROOM: '/admin/updateRoom',
        DELETE_ROOM: '/admin/rooms/{roomId}',
        // Amenities
        GET_ALL_AMENITIES: '/admin/getAllAmenities',
        GET_AMENITY_BY_ID: '/admin/getAmenitieById/{amenityId}',
        CREATE_AMENITY: '/admin/addAmenitie',
        UPDATE_AMENITY: '/admin/updateAmenitie',
        DELETE_AMENITY: '/admin/amenities/{amenityId}'
    },
    MANAGER: {
        GET_PROFILE: '/auth/manager/profile',
        VIEW_AVAILABLE_MEETINGS: '/auth/manager/viewAvailableMeetingRoom',
        BOOK_ROOM: '/auth/manager/bookRoom',
        GET_MY_BOOKINGS: '/auth/manager/myBookings',
        CANCEL_BOOKING: '/auth/manager/booking/{bookingId}',
        GET_TODAY_BOOKING: '/auth/manager/check-in/today-bookings',
        GET_CREDIT_SUMMARY: '/auth/manager/credit-summary',
        GET_ALL_ROOMS: '/auth/manager/viewAvailableMeetingRoom'
    },
    MEMBER: {
        GET_MANAGER_MEETINGS: '/member/manager-meetings'
    }
};

// Helper function to build full URL
function buildUrl(endpoint, params = {}) {
    let url = API_BASE_URL + endpoint;
    Object.keys(params).forEach(key => {
        url = url.replace(`{${key}}`, params[key]);
    });
    return url;
}

// Generic API call function
async function apiCall(endpoint, options = {}) {
    const defaultOptions = {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    };

    // Add auth token if available
    const token = localStorage.getItem('userToken');
    if (token) {
        defaultOptions.headers['Authorization'] = `Bearer ${token}`;
    }
    
    // Add userId to query params for manager endpoints if available
    const userId = localStorage.getItem('userId');
    if (userId && endpoint.includes('/auth/manager/')) {
        const separator = endpoint.includes('?') ? '&' : '?';
        endpoint = `${endpoint}${separator}userId=${userId}`;
    }

    const config = { ...defaultOptions, ...options };

    try {
        const response = await fetch(endpoint, config);
        
        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText || `HTTP error! status: ${response.status}`);
        }
        
        const contentType = response.headers.get('content-type');
        if (contentType && contentType.includes('application/json')) {
            const data = await response.json();
            return { success: true, data };
        } else {
            const text = await response.text();
            return { success: true, data: text };
        }
    } catch (error) {
        console.error('API call failed:', error);
        return { success: false, error: error.message };
    }
}

// Authentication API
const AuthAPI = {
    signUp: async (userData) => {
        const url = buildUrl(API_ENDPOINTS.AUTH.SIGNUP);
        return await apiCall(url, {
            method: 'POST',
            body: JSON.stringify(userData)
        });
    },

    login: async (credentials) => {
        const url = buildUrl(API_ENDPOINTS.AUTH.LOGIN);
        return await apiCall(url, {
            method: 'POST',
            body: JSON.stringify(credentials)
        });
    },

    logout: async () => {
        const url = buildUrl(API_ENDPOINTS.AUTH.LOGOUT);
        return await apiCall(url, { method: 'POST' });
    }
};

// Admin Room API
const AdminRoomAPI = {
    getAll: async () => {
        const url = buildUrl(API_ENDPOINTS.ADMIN.GET_ALL_ROOMS);
        return await apiCall(url);
    },

    getById: async (roomId) => {
        const url = buildUrl(API_ENDPOINTS.ADMIN.GET_ROOM_BY_ID, { roomId });
        return await apiCall(url);
    },

    create: async (roomData) => {
        const url = buildUrl(API_ENDPOINTS.ADMIN.CREATE_ROOM);
        return await apiCall(url, {
            method: 'POST',
            body: JSON.stringify(roomData)
        });
    },

    update: async (roomData) => {
        const url = buildUrl(API_ENDPOINTS.ADMIN.UPDATE_ROOM);
        return await apiCall(url, {
            method: 'PUT',
            body: JSON.stringify(roomData)
        });
    },

    delete: async (roomId) => {
        const url = buildUrl(API_ENDPOINTS.ADMIN.DELETE_ROOM, { roomId });
        return await apiCall(url, { method: 'DELETE' });
    }
};

// Admin Amenity API
const AdminAmenityAPI = {
    getAll: async () => {
        const url = buildUrl(API_ENDPOINTS.ADMIN.GET_ALL_AMENITIES);
        return await apiCall(url);
    },

    getById: async (amenityId) => {
        const url = buildUrl(API_ENDPOINTS.ADMIN.GET_AMENITY_BY_ID, { amenityId });
        return await apiCall(url);
    },

    create: async (amenityData) => {
        const url = buildUrl(API_ENDPOINTS.ADMIN.CREATE_AMENITY);
        return await apiCall(url, {
            method: 'POST',
            body: JSON.stringify(amenityData)
        });
    },

    update: async (amenityData) => {
        const url = buildUrl(API_ENDPOINTS.ADMIN.UPDATE_AMENITY);
        return await apiCall(url, {
            method: 'PUT',
            body: JSON.stringify(amenityData)
        });
    },

    delete: async (amenityId) => {
        const url = buildUrl(API_ENDPOINTS.ADMIN.DELETE_AMENITY, { amenityId });
        return await apiCall(url, { method: 'DELETE' });
    }
};

// Manager API
const ManagerAPI = {
    getProfile: async () => {
        const url = buildUrl(API_ENDPOINTS.MANAGER.GET_PROFILE);
        return await apiCall(url);
    },
    
    getCreditSummary: async () => {
        const url = buildUrl(API_ENDPOINTS.MANAGER.GET_CREDIT_SUMMARY);
        return await apiCall(url);
    },
    
    viewAvailableMeetings: async () => {
        const url = buildUrl(API_ENDPOINTS.MANAGER.VIEW_AVAILABLE_MEETINGS);
        return await apiCall(url);
    },
    
    bookRoom: async (bookingData) => {
        const url = buildUrl(API_ENDPOINTS.MANAGER.BOOK_ROOM);
        return await apiCall(url, {
            method: 'POST',
            body: JSON.stringify(bookingData)
        });
    },
    
    getMyBookings: async () => {
        const url = buildUrl(API_ENDPOINTS.MANAGER.GET_MY_BOOKINGS);
        return await apiCall(url);
    },
    
    cancelBooking: async (bookingId) => {
        const url = buildUrl(API_ENDPOINTS.MANAGER.CANCEL_BOOKING, { bookingId });
        return await apiCall(url, { method: 'DELETE' });
    },
    
    getTodayBookings: async (date) => {
        // If no date provided, use today's date
        if (!date) {
            date = new Date().toISOString().split('T')[0]; // Format: YYYY-MM-DD
        }
        const url = buildUrl(API_ENDPOINTS.MANAGER.GET_TODAY_BOOKING);
        const fullUrl = `${url}&date=${date}`;
        return await apiCall(fullUrl);
    },
    
    getAllRooms: async () => {
        const url = buildUrl(API_ENDPOINTS.MANAGER.GET_ALL_ROOMS);
        return await apiCall(url);
    }
};

// Member API
const MemberAPI = {
    getManagerMeetings: async (managerName = '', meetingDate = '') => {
        const params = new URLSearchParams();
        if (managerName) params.append('managerName', managerName);
        if (meetingDate) params.append('meetingDate', meetingDate);
        
        const url = buildUrl(API_ENDPOINTS.MEMBER.GET_MANAGER_MEETINGS);
        const fullUrl = params.toString() ? `${url}?${params.toString()}` : url;
        return await apiCall(fullUrl);
    }
};

// Export all API modules
const API = {
    Auth: AuthAPI,
    AdminRoom: AdminRoomAPI,
    AdminAmenity: AdminAmenityAPI,
    Manager: ManagerAPI,
    Member: MemberAPI
};


// ============================================
// BACKWARD COMPATIBILITY FUNCTIONS
// Keep old function names for existing code
// ============================================

async function getAllRooms() {
    console.log('🔄 getAllRooms() called');
    
    // Try to get user role from localStorage
    const userRole = localStorage.getItem('userRole');
    const userId = localStorage.getItem('userId');
    
    console.log('👤 User Role:', userRole);
    console.log('🆔 User ID:', userId);
    
    // If user is a manager, use manager endpoint
    if (userRole === 'MANAGER') {
        console.log('📡 Fetching rooms from Manager API...');
        const result = await ManagerAPI.getAllRooms();
        console.log('✅ Manager API result:', result);
        
        if (!result.success) {
            console.error('❌ Manager API failed:', result.error);
        }
        
        return result.success ? result.data : [];
    }
    
    // Otherwise use admin endpoint
    console.log('📡 Fetching rooms from Admin API...');
    const result = await AdminRoomAPI.getAll();
    console.log('✅ Admin API result:', result);
    
    if (!result.success) {
        console.error('❌ Admin API failed:', result.error);
    }
    
    return result.success ? result.data : [];
}

async function getRoomById(roomId) {
    const result = await AdminRoomAPI.getById(roomId);
    if (!result.success) throw new Error(result.error);
    return result.data;
}

async function createRoom(roomData) {
    const result = await AdminRoomAPI.create(roomData);
    if (!result.success) throw new Error(result.error);
    return result.data;
}

async function updateRoom(roomData) {
    const result = await AdminRoomAPI.update(roomData);
    if (!result.success) throw new Error(result.error);
    return result.data;
}

async function deleteRoom(roomId) {
    const result = await AdminRoomAPI.delete(roomId);
    if (!result.success) throw new Error(result.error);
    return result.data;
}

async function getAllAmenities() {
    const result = await AdminAmenityAPI.getAll();
    return result.success ? result.data : [];
}

async function getAmenityById(amenityId) {
    const result = await AdminAmenityAPI.getById(amenityId);
    if (!result.success) throw new Error(result.error);
    return result.data;
}

async function createAmenity(amenityData) {
    const result = await AdminAmenityAPI.create(amenityData);
    if (!result.success) throw new Error(result.error);
    return result.data;
}

async function updateAmenity(amenityData) {
    const result = await AdminAmenityAPI.update(amenityData);
    if (!result.success) throw new Error(result.error);
    return result.data;
}

async function deleteAmenity(amenityId) {
    const result = await AdminAmenityAPI.delete(amenityId);
    if (!result.success) throw new Error(result.error);
    return result.data;
}

async function getManagerProfile() {
    const result = await ManagerAPI.getProfile();
    if (!result.success) {
        console.error('Failed to load manager profile:', result.error);
        throw new Error(result.error || 'Failed to load profile');
    }
    return result.data;
}

async function getManagerCreditSummary() {
    const result = await ManagerAPI.getCreditSummary();
    if (!result.success) {
        console.error('Failed to load credit summary:', result.error);
        // Return default values if API fails
        return {
            totalCredits: 2000,
            creditsUsed: 0,
            creditsRemaining: 2000,
            penalty: 0
        };
    }
    return result.data;
}

async function viewAvailableMeetings() {
    const result = await ManagerAPI.viewAvailableMeetings();
    return result.success ? result.data : [];
}

async function bookRoom(bookingData) {
    const result = await ManagerAPI.bookRoom(bookingData);
    if (!result.success) throw new Error(result.error);
    return result.data;
}

async function getMyBookings() {
    const result = await ManagerAPI.getMyBookings();
    return result.success ? result.data : [];
}

async function cancelBooking(bookingId) {
    const result = await ManagerAPI.cancelBooking(bookingId);
    if (!result.success) throw new Error(result.error);
    return result.data;
}

async function getTodayBookings() {
    const result = await ManagerAPI.getTodayBookings();
    return result.success ? result.data : [];
}

// ============================================
// UTILITY FUNCTIONS
// ============================================

/**
 * Format amenity name for display
 * @param {string} amenityName - Amenity name in uppercase with underscores
 * @returns {string} Formatted amenity name
 */
function formatAmenityName(amenityName) {
    if (!amenityName) return '';
    return amenityName
        .toLowerCase()
        .split('_')
        .map(word => word.charAt(0).toUpperCase() + word.slice(1))
        .join(' ');
}

/**
 * Calculate room cost including amenities
 * @param {number} perHourCost - Base cost per hour
 * @param {Array<Object>} amenities - Array of amenity objects with creditCost
 * @returns {number} Total cost including amenities
 */
function calculateRoomCost(perHourCost, amenities = []) {
    const amenityCosts = amenities.reduce((sum, a) => sum + (a.creditCost || 0), 0);
    return perHourCost + amenityCosts;
}