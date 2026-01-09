# Manager API Requirements

## Fixed Issues
1. ✅ Changed script path from `./api.js` to `./api.js` (file is in root, not js folder)
2. ✅ Added all manager endpoints to API
3. ✅ Integrated manager credit summary API
4. ✅ Updated getAllRooms to use manager endpoint for manager role

## Required Backend Endpoints

### 1. GET /api/manager/profile
**Returns manager profile information**

Response format:
```json
{
  "userId": "uuid",
  "name": "John Doe",
  "email": "manager@example.com",
  "role": "MANAGER",
  "createdAt": "2026-01-01T00:00:00Z"
}
```

### 2. GET /api/manager/credit-summary ⭐ NEW
**Returns manager credit summary based on `manager_credit_summary` table**

Response format:
```json
{
  "userId": "uuid",
  "managerName": "John Doe",
  "email": "manager@example.com",
  "totalCredits": 2000,
  "creditsUsed": 1500,
  "creditsRemaining": 500,
  "penalty": 50,
  "lastResetAt": "2026-01-01T00:00:00Z",
  "updatedAt": "2026-01-09T00:00:00Z"
}
```

**Database Query Example:**
```sql
SELECT 
    user_id,
    manager_name,
    email,
    total_credits,
    credits_used,
    (total_credits - credits_used - penalty) as credits_remaining,
    penalty,
    last_reset_at,
    updated_at
FROM manager_credit_summary
WHERE user_id = ?;
```

### 3. GET /api/manager/viewAvailableMeetings
**Returns available meeting rooms/time slots**

Response format:
```json
[
  {
    "roomId": "uuid",
    "roomName": "Conference Room A",
    "availableSlots": [
      {
        "startTime": "2026-01-09T09:00:00Z",
        "endTime": "2026-01-09T10:00:00Z",
        "isAvailable": true
      }
    ]
  }
]
```

### 4. POST /api/manager/bookRoom
**Books a room for the manager**

Request body:
```json
{
  "roomId": "uuid",
  "startTime": "2026-01-09T09:00:00Z",
  "endTime": "2026-01-09T10:00:00Z",
  "purpose": "Team Meeting",
  "amenities": ["uuid1", "uuid2"]
}
```

Response:
```json
{
  "bookingId": "uuid",
  "roomId": "uuid",
  "userId": "uuid",
  "startTime": "2026-01-09T09:00:00Z",
  "endTime": "2026-01-09T10:00:00Z",
  "status": "CONFIRMED",
  "creditsCost": 100
}
```

**Important:** Update `manager_credit_summary.credits_used` when booking is confirmed!

### 5. GET /api/manager/getMyBookings
**Returns all bookings for the manager**

Response format:
```json
[
  {
    "bookingId": "uuid",
    "roomId": "uuid",
    "roomName": "Conference Room A",
    "startTime": "2026-01-09T09:00:00Z",
    "endTime": "2026-01-09T10:00:00Z",
    "status": "CONFIRMED",
    "creditsCost": 100,
    "createdAt": "2026-01-08T00:00:00Z"
  }
]
```

### 6. DELETE /api/manager/booking/{bookingId}
**Cancels a booking**

Response:
```json
{
  "message": "Booking cancelled successfully",
  "refundedCredits": 100
}
```

**Important:** Update `manager_credit_summary.credits_used` to refund credits (or add penalty if cancellation is late)!

### 7. GET /api/manager/check-in/today-bookings
**Returns today's bookings for check-in purposes**

Response format:
```json
[
  {
    "bookingId": "uuid",
    "roomName": "Conference Room A",
    "startTime": "2026-01-09T09:00:00Z",
    "endTime": "2026-01-09T10:00:00Z",
    "status": "CONFIRMED",
    "checkedIn": false
  }
]
```

### 8. GET /api/manager/getAllRooms
**Returns all available rooms for managers to view**

Response format:
```json
[
  {
    "roomId": "uuid",
    "roomName": "Conference Room A",
    "roomType": "CONFERENCE",
    "seatingCapacity": 10,
    "perHourCost": 50,
    "roomCost": 50,
    "amenities": [
      {
        "amenityId": "uuid",
        "amenityName": "PROJECTOR",
        "creditCost": 10
      }
    ],
    "isAvailable": true,
    "image": "https://example.com/room.jpg"
  }
]
```

## Database Schema Notes

### manager_credit_summary Table
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

### Key Business Logic

1. **Credits Calculation:**
   - `creditsRemaining = total_credits - credits_used - penalty`

2. **When Booking Room:**
   - Deduct `roomCost * hours` from available credits
   - Update `credits_used` in `manager_credit_summary`
   - Check if `creditsRemaining >= roomCost` before allowing booking

3. **When Cancelling Booking:**
   - If cancelled before meeting time: Refund full credits
   - If cancelled late or no-show: Add penalty
   - Update `credits_used` and `penalty` accordingly

4. **Monthly Reset:**
   - Reset `credits_used` to 0
   - Reset `penalty` to 0
   - Update `last_reset_at` to current timestamp
   - Keep `total_credits` as is (unless admin changes it)

## Frontend Changes Made

### api.js
- ✅ Added all manager endpoints
- ✅ Created `ManagerAPI` object with all methods
- ✅ Added backward compatibility functions
- ✅ Added `getManagerCreditSummary()` function
- ✅ Updated `getAllRooms()` to check user role

### manager-dashboard.html
- ✅ Fixed script path from `./api.js` to `./api.js`
- ✅ Updated `loadManagerProfile()` to fetch credit summary
- ✅ Added credit display updates (remaining, used, total, penalty)
- ✅ Added progress bar color coding based on credit percentage

## Testing Checklist

- [ ] Backend: Create `/api/manager/credit-summary` endpoint
- [ ] Backend: Update booking endpoints to modify `manager_credit_summary`
- [ ] Backend: Create trigger or service to update credits on booking
- [ ] Frontend: Test credit display on dashboard load
- [ ] Frontend: Test credit updates after booking
- [ ] Frontend: Test credit updates after cancellation
- [ ] Frontend: Test penalty display
- [ ] Integration: Verify credits_used increases on booking
- [ ] Integration: Verify credits_used decreases on cancellation (if applicable)

## Important Notes

⚠️ **Authentication**: All manager endpoints require authentication token in header:
```
Authorization: Bearer <token>
```

⚠️ **User ID**: Extract user_id from the JWT token to query manager_credit_summary table

⚠️ **Error Handling**: Return appropriate HTTP status codes:
- 401 for unauthorized
- 403 for insufficient credits
- 404 for resource not found
- 400 for bad request
- 500 for server errors
