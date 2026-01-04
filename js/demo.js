// API Configuration
const API_BASE_URL = 'http://localhost:8081/api';

// API Endpoints
const API_ENDPOINTS = {
    AUTH: {
        SIGNUP: '/auth/signUp',
        LOGIN: '/auth/login',
        LOGOUT: '/auth/logout'
    },
    ROOMS: {
        GET_ALL: '/rooms',
        GET_BY_ID: '/rooms/{id}',
        CREATE: '/rooms',
        UPDATE: '/rooms/{id}',
        DELETE: '/rooms/{id}',
        GET_AVAILABLE: '/rooms/available'
    },
    BOOKINGS: {
        GET_ALL: '/bookings',
        GET_BY_ID: '/bookings/{id}',
        CREATE: '/bookings',
        UPDATE: '/bookings/{id}',
        DELETE: '/bookings/{id}',
        GET_BY_USER: '/bookings/user/{userId}',
        GET_BY_ROOM: '/bookings/room/{roomId}'
    },
    AMENITIES: {
        GET_ALL: '/amenities',
        GET_BY_ID: '/amenities/{id}',
        CREATE: '/amenities',
        UPDATE: '/amenities/{id}',
        DELETE: '/amenities/{id}'
    },
    USERS: {
        GET_ALL: '/users',
        GET_BY_ID: '/users/{id}',
        UPDATE: '/users/{id}',
        DELETE: '/users/{id}',
        GET_PROFILE: '/users/profile'
    },
    CREDITS: {
        GET_USER_CREDITS: '/credits/user/{userId}',
        RESET_MANAGER_CREDITS: '/credits/reset'
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

    const config = { ...defaultOptions, ...options };

    try {
        const response = await fetch(endpoint, config);
        const data = await response.text();
        
        if (!response.ok) {
            throw new Error(data || `HTTP error! status: ${response.status}`);
        }
        
        return { success: true, data: data ? JSON.parse(data) : null };
    } catch (error) {
        console.error('API call failed:', error);
        return { success: false, error: error.message };
    }
}

// Authentication API calls
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

// Room API calls
const RoomAPI = {
    getAll: async () => {
        const url = buildUrl(API_ENDPOINTS.ROOMS.GET_ALL);
        return await apiCall(url);
    },

    getById: async (roomId) => {
        const url = buildUrl(API_ENDPOINTS.ROOMS.GET_BY_ID, { id: roomId });
        return await apiCall(url);
    },

    create: async (roomData) => {
        const url = buildUrl(API_ENDPOINTS.ROOMS.CREATE);
        return await apiCall(url, {
            method: 'POST',
            body: JSON.stringify(roomData)
        });
    },

    update: async (roomId, roomData) => {
        const url = buildUrl(API_ENDPOINTS.ROOMS.UPDATE, { id: roomId });
        return await apiCall(url, {
            method: 'PUT',
            body: JSON.stringify(roomData)
        });
    },

    delete: async (roomId) => {
        const url = buildUrl(API_ENDPOINTS.ROOMS.DELETE, { id: roomId });
        return await apiCall(url, { method: 'DELETE' });
    },

    getAvailable: async (filters = {}) => {
        const url = buildUrl(API_ENDPOINTS.ROOMS.GET_AVAILABLE);
        const queryParams = new URLSearchParams(filters).toString();
        return await apiCall(`${url}?${queryParams}`);
    }
};

// Booking API calls
const BookingAPI = {
    getAll: async () => {
        const url = buildUrl(API_ENDPOINTS.BOOKINGS.GET_ALL);
        return await apiCall(url);
    },

    getById: async (bookingId) => {
        const url = buildUrl(API_ENDPOINTS.BOOKINGS.GET_BY_ID, { id: bookingId });
        return await apiCall(url);
    },

    create: async (bookingData) => {
        const url = buildUrl(API_ENDPOINTS.BOOKINGS.CREATE);
        return await apiCall(url, {
            method: 'POST',
            body: JSON.stringify(bookingData)
        });
    },

    update: async (bookingId, bookingData) => {
        const url = buildUrl(API_ENDPOINTS.BOOKINGS.UPDATE, { id: bookingId });
        return await apiCall(url, {
            method: 'PUT',
            body: JSON.stringify(bookingData)
        });
    },

    delete: async (bookingId) => {
        const url = buildUrl(API_ENDPOINTS.BOOKINGS.DELETE, { id: bookingId });
        return await apiCall(url, { method: 'DELETE' });
    },

    getByUser: async (userId) => {
        const url = buildUrl(API_ENDPOINTS.BOOKINGS.GET_BY_USER, { userId });
        return await apiCall(url);
    },

    getByRoom: async (roomId) => {
        const url = buildUrl(API_ENDPOINTS.BOOKINGS.GET_BY_ROOM, { roomId });
        return await apiCall(url);
    }
};

// Amenity API calls
const AmenityAPI = {
    getAll: async () => {
        const url = buildUrl(API_ENDPOINTS.AMENITIES.GET_ALL);
        return await apiCall(url);
    },

    getById: async (amenityId) => {
        const url = buildUrl(API_ENDPOINTS.AMENITIES.GET_BY_ID, { id: amenityId });
        return await apiCall(url);
    },

    create: async (amenityData) => {
        const url = buildUrl(API_ENDPOINTS.AMENITIES.CREATE);
        return await apiCall(url, {
            method: 'POST',
            body: JSON.stringify(amenityData)
        });
    },

    update: async (amenityId, amenityData) => {
        const url = buildUrl(API_ENDPOINTS.AMENITIES.UPDATE, { id: amenityId });
        return await apiCall(url, {
            method: 'PUT',
            body: JSON.stringify(amenityData)
        });
    },

    delete: async (amenityId) => {
        const url = buildUrl(API_ENDPOINTS.AMENITIES.DELETE, { id: amenityId });
        return await apiCall(url, { method: 'DELETE' });
    }
};

// User API calls
const UserAPI = {
    getAll: async () => {
        const url = buildUrl(API_ENDPOINTS.USERS.GET_ALL);
        return await apiCall(url);
    },

    getById: async (userId) => {
        const url = buildUrl(API_ENDPOINTS.USERS.GET_BY_ID, { id: userId });
        return await apiCall(url);
    },

    update: async (userId, userData) => {
        const url = buildUrl(API_ENDPOINTS.USERS.UPDATE, { id: userId });
        return await apiCall(url, {
            method: 'PUT',
            body: JSON.stringify(userData)
        });
    },

    delete: async (userId) => {
        const url = buildUrl(API_ENDPOINTS.USERS.DELETE, { id: userId });
        return await apiCall(url, { method: 'DELETE' });
    },

    getProfile: async () => {
        const url = buildUrl(API_ENDPOINTS.USERS.GET_PROFILE);
        return await apiCall(url);
    }
};

// Credits API calls
const CreditsAPI = {
    getUserCredits: async (userId) => {
        const url = buildUrl(API_ENDPOINTS.CREDITS.GET_USER_CREDITS, { userId });
        return await apiCall(url);
    },

    resetManagerCredits: async () => {
        const url = buildUrl(API_ENDPOINTS.CREDITS.RESET_MANAGER_CREDITS);
        return await apiCall(url, { method: 'POST' });
    }
};

// Export all API modules
const API = {
    Auth: AuthAPI,
    Room: RoomAPI,
    Booking: BookingAPI,
    Amenity: AmenityAPI,
    User: UserAPI,
    Credits: CreditsAPI
};