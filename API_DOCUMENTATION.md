# BookIt - Complete API Documentation

## Base URL
```
http://localhost:8081/api
```

## Authentication
All protected endpoints require:
- **userId** as query parameter
- Role-based access control (RBAC)
- User must be logged in with valid role

---

# 📋 Table of Contents

1. [Authentication APIs](#authentication-apis)
2. [Admin APIs](#admin-apis)
3. [Manager APIs](#manager-apis)
4. [Member APIs](#member-apis)

---

# Authentication APIs

## 1. Sign Up
**Endpoint:** `POST /auth/signUp`  
**Access:** Public  
**Description:** Register a new user

**Request Body:**
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password123",
  "role": "MEMBER"
}
```

**Response:**
```json
{
  "userId": "uuid",
  "name": "John Doe",
  "email": "john@example.com",
  "role": "MEMBER",
  "message": "User registered successfully"
}
```

---

## 2. Login
**Endpoint:** `POST /auth/login`  
**Access:** Public  
**Description:** Login and receive user credentials

**Request Body:**
```json
{
  "email": "john@example.com",
  "password": "password123"
}
```

**Response:**
```json
{
  "token": "jwt-token-here",
  "userId": "uuid",
  "name": "John Doe",
  "email": "john@example.com",
  "role": "MEMBER",
  "credits": 0
}
```

---

## 3. Logout
**Endpoint:** `POST /auth/logout`  
**Access:** Protected  
**Description:** Logout current user

**Response:**
```json
{
  "message": "Logged out successfully"
}
```

---

# Admin APIs

## Room Management

### 1. Get All Rooms
**Endpoint:** `GET /admin/getAllRoom`  
**Access:** ADMIN only  
**Description:** Retrieve all meeting rooms

**Response:**
```json
[
  {
    "roomId": "uuid",
    "roomName": "Conference Room A",
    "roomType": "CONFERENCE",
    "seatingCapacity": 10,
    "perHourCost": 50,
    "amenities": [
      {
        "amenityId": "uuid",
        "amenityName": "PROJECTOR",
        "creditCost": 10
      }
    ],
    "isAvailable": true
  }
]
```

---

### 2. Get Room by ID
**Endpoint:** `GET /admin/getRoomById/{roomId}`  
**Access:** ADMIN only  
**Description:** Get details of a specific room

**Path Parameters:**
- `roomId` (UUID) - ID of the room

**Response:**
```json
{
  "roomId": "uuid",
  "roomName": "Conference Room A",
  "roomType": "CONFERENCE",
  "seatingCapacity": 10,
  "perHourCost": 50,
  "amenities": [...],
  "isAvailable": true
}
```

---

### 3. Create Room
**Endpoint:** `POST /admin/createRoom`  
**Access:** ADMIN only  
**Description:** Create a new meeting room

**Request Body:**
```json
{
  "roomName": "Conference Room A",
  "roomType": "CONFERENCE",
  "seatingCapacity": 10,
  "perHourCost": 50,
  "amenityIds": ["uuid1", "uuid2"]
}
```

**Response:**
```json
{
  "roomId": "uuid",
  "roomName": "Conference Room A",
  "message": "Room created successfully"
}
```

---

### 4. Update Room
**Endpoint:** `PUT /admin/updateRoom`  
**Access:** ADMIN only  
**Description:** Update an existing room

**Request Body:**
```json
{
  "roomId": "uuid",
  "roomName": "Conference Room A - Updated",
  "roomType": "CONFERENCE",
  "seatingCapacity": 15,
  "perHourCost": 60,
  "amenityIds": ["uuid1", "uuid2", "uuid3"]
}
```

**Response:**
```json
{
  "roomId": "uuid",
  "roomName": "Conference Room A - Updated",
  "message": "Room updated successfully"
}
```

---

### 5. Delete Room
**Endpoint:** `DELETE /admin/rooms/{roomId}`  
**Access:** ADMIN only  
**Description:** Delete a meeting room

**Path Parameters:**
- `roomId` (UUID) - ID of the room to delete

**Response:**
```json
{
  "message": "Room deleted successfully"
}
```

---

## Amenity Management

### 1. Get All Amenities
**Endpoint:** `GET /admin/getAllAmenities`  
**Access:** ADMIN only  
**Description:** Retrieve all amenities

**Response:**
```json
[
  {
    "amenityId": "uuid",
    "amenityName": "PROJECTOR",
    "description": "HD Projector with HDMI",
    "creditCost": 10
  },
  {
    "amenityId": "uuid",
    "amenityName": "WHITEBOARD",
    "description": "Large whiteboard with markers",
    "creditCost": 5
  }
]
```

---

### 2. Get Amenity by ID
**Endpoint:** `GET /admin/getAmenitieById/{amenityId}`  
**Access:** ADMIN only  
**Description:** Get details of a specific amenity

**Path Parameters:**
- `amenityId` (UUID) - ID of the amenity

**Response:**
```json
{
  "amenityId": "uuid",
  "amenityName": "PROJECTOR",
  "description": "HD Projector with HDMI",
  "creditCost": 10
}
```

---

### 3. Create Amenity
**Endpoint:** `POST /admin/addAmenitie`  
**Access:** ADMIN only  
**Description:** Create a new amenity

**Request Body:**
```json
{
  "amenityName": "SMART_TV",
  "description": "55 inch Smart TV",
  "creditCost": 15
}
```

**Response:**
```json
{
  "amenityId": "uuid",
  "amenityName": "SMART_TV",
  "message": "Amenity created successfully"
}
```

---

### 4. Update Amenity
**Endpoint:** `PUT /admin/updateAmenitie`  
**Access:** ADMIN only  
**Description:** Update an existing amenity

**Request Body:**
```json
{
  "amenityId": "uuid",
  "amenityName": "SMART_TV",
  "description": "65 inch Smart TV - Updated",
  "creditCost": 20
}
```

**Response:**
```json
{
  "amenityId": "uuid",
  "amenityName": "SMART_TV",
  "message": "Amenity updated successfully"
}
```

---

### 5. Delete Amenity
**Endpoint:** `DELETE /admin/amenities/{amenityId}`  
**Access:** ADMIN only  
**Description:** Delete an amenity

**Path Parameters:**
- `amenityId` (UUID) - ID of the amenity to delete

**Response:**
```json
{
  "message": "Amenity deleted successfully"
}
```

---

# Manager APIs

## Profile & Credits

### 1. Get Manager Profile
**Endpoint:** `GET /auth/manager/profile?userId={userId}`  
**Access:** MANAGER only  
**Description:** Get manager profile information

**Query Parameters:**
- `userId` (UUID) - Manager's user ID

**Response:**
```json
{
  "userId": "uuid",
  "name": "John Manager",
  "email": "manager@example.com",
  "role": "MANAGER",
  "credits": 1500
}
```

---

### 2. Get Credit Summary
**Endpoint:** `GET /auth/manager/credit-summary?userId={userId}`  
**Access:** MANAGER only  
**Description:** Get detailed credit summary for manager

**Query Parameters:**
- `userId` (UUID) - Manager's user ID

**Response:**
```json
{
  "userId": "uuid",
  "managerName": "John Manager",
  "email": "manager@example.com",
  "totalCredits": 2000,
  "creditsUsed": 500,
  "creditsRemaining": 1450,
  "penalty": 50,
  "lastResetAt": "2026-01-01T00:00:00Z",
  "updatedAt": "2026-01-09T00:00:00Z"
}
```

**Business Logic:**
```
creditsRemaining = totalCredits - creditsUsed - penalty
```

---

## Room & Meeting Management

### 3. View Available Meeting Rooms
**Endpoint:** `GET /auth/manager/viewAvailableMeetingRoom?userId={userId}`  
**Access:** MANAGER only  
**Description:** View all available meeting rooms

**Query Parameters:**
- `userId` (UUID) - Manager's user ID

**Response:**
```json
[
  {
    "roomId": "uuid",
    "roomName": "Conference Room A",
    "roomType": "CONFERENCE",
    "seatingCapacity": 10,
    "perHourCost": 50,
    "amenities": [
      {
        "amenityId": "uuid",
        "amenityName": "PROJECTOR",
        "creditCost": 10
      }
    ],
    "isAvailable": true
  }
]
```

---

### 4. Book a Room
**Endpoint:** `POST /auth/manager/bookRoom?userId={userId}`  
**Access:** MANAGER only  
**Description:** Book a meeting room

**Query Parameters:**
- `userId` (UUID) - Manager's user ID

**Request Body:**
```json
{
  "userId": "uuid",
  "roomId": "uuid",
  "startTime": "2026-01-10T09:00:00Z",
  "endTime": "2026-01-10T11:00:00Z",
  "purpose": "Team Meeting",
  "amenityIds": ["uuid1", "uuid2"]
}
```

**Response:**
```json
{
  "bookingId": "uuid",
  "roomId": "uuid",
  "roomName": "Conference Room A",
  "userId": "uuid",
  "startTime": "2026-01-10T09:00:00Z",
  "endTime": "2026-01-10T11:00:00Z",
  "status": "CONFIRMED",
  "creditsCost": 120,
  "statusCode": 201,
  "message": "Room booked successfully by manager: uuid"
}
```

**Important:** 
- Manager's `creditsUsed` in `manager_credit_summary` table is automatically updated
- Credits are deducted: `roomCost * hours + amenityCosts`

---

### 5. Get My Bookings
**Endpoint:** `GET /auth/manager/myBookings?userId={userId}`  
**Access:** MANAGER only  
**Description:** Get all bookings made by manager

**Query Parameters:**
- `userId` (UUID) - Manager's user ID

**Response:**
```json
[
  {
    "bookingId": "uuid",
    "roomId": "uuid",
    "roomName": "Conference Room A",
    "startTime": "2026-01-10T09:00:00Z",
    "endTime": "2026-01-10T11:00:00Z",
    "status": "CONFIRMED",
    "creditsCost": 120,
    "createdAt": "2026-01-09T10:00:00Z"
  }
]
```

---

### 6. Get Booking by ID
**Endpoint:** `GET /auth/manager/booking/{bookingId}?userId={userId}`  
**Access:** MANAGER only  
**Description:** Get details of a specific booking

**Path Parameters:**
- `bookingId` (UUID) - ID of the booking

**Query Parameters:**
- `userId` (UUID) - Manager's user ID

**Response:**
```json
{
  "bookingId": "uuid",
  "roomId": "uuid",
  "roomName": "Conference Room A",
  "userId": "uuid",
  "startTime": "2026-01-10T09:00:00Z",
  "endTime": "2026-01-10T11:00:00Z",
  "status": "CONFIRMED",
  "creditsCost": 120,
  "amenities": [...],
  "createdAt": "2026-01-09T10:00:00Z"
}
```

---

### 7. Cancel Booking
**Endpoint:** `DELETE /auth/manager/booking/{bookingId}?userId={userId}`  
**Access:** MANAGER only  
**Description:** Cancel a booking

**Path Parameters:**
- `bookingId` (UUID) - ID of the booking to cancel

**Query Parameters:**
- `userId` (UUID) - Manager's user ID

**Response:**
```json
{
  "message": "Booking cancelled successfully by manager: uuid",
  "statusCode": "200",
  "refundedCredits": 120
}
```

**Important:**
- If cancelled before meeting time: Full credit refund
- If cancelled late or no-show: Penalty applied
- `creditsUsed` is adjusted in `manager_credit_summary`

---

## Check-in Management

### 8. Get Today's Bookings
**Endpoint:** `GET /auth/manager/check-in/today-bookings?userId={userId}&date={date}`  
**Access:** MANAGER only  
**Description:** Get all bookings for today for check-in

**Query Parameters:**
- `userId` (UUID) - Manager's user ID
- `date` (String) - Date in format YYYY-MM-DD (e.g., "2026-01-09")

**Response:**
```json
[
  {
    "bookingId": "uuid",
    "roomName": "Conference Room A",
    "startTime": "2026-01-09T09:00:00Z",
    "endTime": "2026-01-09T11:00:00Z",
    "status": "CONFIRMED",
    "checkedIn": false
  },
  {
    "bookingId": "uuid",
    "roomName": "Meeting Room B",
    "startTime": "2026-01-09T14:00:00Z",
    "endTime": "2026-01-09T15:00:00Z",
    "status": "CONFIRMED",
    "checkedIn": true
  }
]
```

---

### 9. Check In to Meeting
**Endpoint:** `POST /auth/manager/check-in/{bookingId}?userId={userId}`  
**Access:** MANAGER only  
**Description:** Check in to a booked meeting

**Path Parameters:**
- `bookingId` (UUID) - ID of the booking

**Query Parameters:**
- `userId` (UUID) - Manager's user ID

**Response:**
```json
{
  "bookingId": "uuid",
  "status": "CHECKED_IN",
  "checkedInAt": "2026-01-09T09:05:00Z",
  "message": "Checked in successfully"
}
```

---

# Member APIs

## Meeting Management

### 1. Get Manager Meetings
**Endpoint:** `GET /member/manager-meetings?managerName={name}&meetingDate={date}`  
**Access:** MEMBER only  
**Description:** View meetings organized by a specific manager

**Query Parameters:**
- `managerName` (String, optional) - Filter by manager name
- `meetingDate` (String, optional) - Filter by date (YYYY-MM-DD)

**Response:**
```json
[
  {
    "meetingId": "uuid",
    "managerName": "John Manager",
    "roomName": "Conference Room A",
    "startTime": "2026-01-10T09:00:00Z",
    "endTime": "2026-01-10T11:00:00Z",
    "purpose": "Team Meeting",
    "status": "SCHEDULED"
  }
]
```

---

# Error Responses

## Standard Error Format
```json
{
  "error": "Error message",
  "statusCode": 400,
  "timestamp": "2026-01-09T10:00:00Z"
}
```

## Common HTTP Status Codes

| Code | Meaning | Description |
|------|---------|-------------|
| 200 | OK | Request successful |
| 201 | Created | Resource created successfully |
| 400 | Bad Request | Invalid request parameters |
| 401 | Unauthorized | Authentication required |
| 403 | Forbidden | Access denied - insufficient permissions |
| 404 | Not Found | Resource not found |
| 409 | Conflict | Resource already exists or conflict |
| 500 | Internal Server Error | Server error |

---

# Database Schema Reference

## Users Table
```sql
CREATE TABLE USERS (
    user_id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL CHECK (role IN ('ADMIN', 'MANAGER', 'MEMBER')),
    credits INTEGER DEFAULT 0 CHECK (credits >= 0),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

## Manager Credit Summary Table
```sql
CREATE TABLE manager_credit_summary (
   user_id UUID PRIMARY KEY,
   manager_name VARCHAR(100) NOT NULL,
   email VARCHAR(255) NOT NULL,
   total_credits INTEGER NOT NULL DEFAULT 2000,
   credits_used INTEGER NOT NULL DEFAULT 0,
   penalty INTEGER NOT NULL DEFAULT 0,
   last_reset_at TIMESTAMP,
   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   
   CONSTRAINT fk_manager_summary_user
       FOREIGN KEY (user_id)
       REFERENCES users(user_id)
);
```

---

# Frontend-Backend Integration

## LocalStorage Structure

After successful login, the following is stored in localStorage:

```javascript
{
  userToken: "jwt-token-or-dummy-token",
  userId: "uuid",
  userName: "John Doe",
  userEmail: "john@example.com",
  userRole: "MANAGER" // Always uppercase: ADMIN, MANAGER, or MEMBER
}
```

## API Call Example (Frontend)

```javascript
// Using the API module
const response = await ManagerAPI.bookRoom({
  userId: localStorage.getItem('userId'),
  roomId: "room-uuid",
  startTime: "2026-01-10T09:00:00Z",
  endTime: "2026-01-10T11:00:00Z",
  purpose: "Team Meeting",
  amenityIds: ["amenity-uuid-1"]
});

if (response.success) {
  console.log('Booking successful:', response.data);
} else {
  console.error('Booking failed:', response.error);
}
```

---

# Role-Based Access Control (RBAC)

## Dashboard Access

| Role | Allowed Dashboard | File |
|------|------------------|------|
| ADMIN | Admin Dashboard | `admindashboard.html` |
| MANAGER | Manager Dashboard | `manager-dashboard.html` |
| MEMBER | Member Dashboard | `member-dashboard.html` |

## Security Rules

1. ✅ Users can only access their role's dashboard
2. ✅ Attempting to access another role's dashboard = redirect to their own
3. ✅ Not authenticated = redirect to login page
4. ✅ All API calls automatically include `userId` for manager endpoints
5. ✅ Backend validates role on every request

---

# Testing Guide

## Test with Postman/Curl

### Example: Login
```bash
curl -X POST http://localhost:8081/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "manager@example.com",
    "password": "password123"
  }'
```

### Example: Get Manager Bookings
```bash
curl -X GET "http://localhost:8081/api/auth/manager/myBookings?userId=your-uuid-here"
```

### Example: Book a Room
```bash
curl -X POST "http://localhost:8081/api/auth/manager/bookRoom?userId=your-uuid-here" \
  -H "Content-Type: application/json" \
  -d '{
    "userId": "your-uuid-here",
    "roomId": "room-uuid",
    "startTime": "2026-01-10T09:00:00Z",
    "endTime": "2026-01-10T11:00:00Z",
    "purpose": "Team Meeting"
  }'
```

---

# Change Log

## Version 1.0 (Current)
- ✅ Authentication endpoints
- ✅ Admin room & amenity management
- ✅ Manager booking & credit system
- ✅ Member meeting viewing
- ✅ Role-based access control
- ✅ Check-in functionality

## Planned Features
- 🔄 Email notifications
- 🔄 Calendar integration
- 🔄 Recurring meetings
- 🔄 Meeting analytics
- 🔄 Advanced search & filters

---

# Support & Contact

For API issues or questions, please check:
1. Console logs (F12 in browser)
2. Network tab for API responses
3. This documentation for endpoint details

**Developer Contact:** Your Team  
**Last Updated:** January 9, 2026
