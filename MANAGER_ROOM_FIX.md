# Manager Dashboard - Room Loading Fix

## Issues Found & Fixed

### ❌ Problem 1: API Path Mismatch
**Issue:** Backend uses `/api/auth/manager/*` but frontend was calling `/api/manager/*`

**Backend:**
```java
@RequestMapping("/api/auth/manager")
public class ManagerController {
    @GetMapping("/viewAvailableMeetingRoom")
    ...
}
```

**Frontend (OLD - WRONG):**
```javascript
MANAGER: {
    GET_ALL_ROOMS: '/manager/getAllRooms'  // ❌ Wrong path
}
```

**Frontend (NEW - FIXED):**
```javascript
MANAGER: {
    GET_ALL_ROOMS: '/auth/manager/viewAvailableMeetingRoom'  // ✅ Correct path
}
```

---

### ❌ Problem 2: Missing userId Parameter
**Issue:** Backend requires `userId` as query parameter but frontend wasn't sending it

**Backend requires:**
```java
@GetMapping("/viewAvailableMeetingRoom")
public ResponseEntity<List<MeetingRoomDto>> viewAvailableMeetingRooms(@RequestParam UUID userId)
```

**Frontend (FIXED):**
```javascript
// Now automatically adds userId to all manager endpoints
if (userId && endpoint.includes('/auth/manager/')) {
    const separator = endpoint.includes('?') ? '&' : '?';
    endpoint = `${endpoint}${separator}userId=${userId}`;
}
```

---

### ❌ Problem 3: Endpoint Name Mismatch
**Issue:** Backend has different endpoint names than frontend expected

| Frontend Expected | Backend Actual | Status |
|------------------|----------------|--------|
| `getAllRooms` | `viewAvailableMeetingRoom` | ✅ Fixed |
| `getMyBookings` | `myBookings` | ✅ Fixed |
| `viewAvailableMeetings` | `viewAvailableMeetingRoom` | ✅ Fixed |

---

## Updated API Endpoints

### ✅ All Manager Endpoints (CORRECTED)

```javascript
MANAGER: {
    GET_PROFILE: '/auth/manager/profile',
    VIEW_AVAILABLE_MEETINGS: '/auth/manager/viewAvailableMeetingRoom',
    BOOK_ROOM: '/auth/manager/bookRoom',
    GET_MY_BOOKINGS: '/auth/manager/myBookings',
    CANCEL_BOOKING: '/auth/manager/booking/{bookingId}',
    GET_TODAY_BOOKING: '/auth/manager/check-in/today-bookings',
    GET_CREDIT_SUMMARY: '/auth/manager/credit-summary',
    GET_ALL_ROOMS: '/auth/manager/viewAvailableMeetingRoom'
}
```

---

## Backend Endpoint Mapping

| Function | Method | Backend Path | Query Params |
|----------|--------|-------------|--------------|
| Get All Rooms | GET | `/api/auth/manager/viewAvailableMeetingRoom` | `userId` (required) |
| Get Profile | GET | `/api/auth/manager/profile` | `userId` (required) |
| Book Room | POST | `/api/auth/manager/bookRoom` | Body includes `userId` |
| My Bookings | GET | `/api/auth/manager/myBookings` | `userId` (required) |
| Cancel Booking | DELETE | `/api/auth/manager/booking/{bookingId}` | `userId` (required) |
| Today's Bookings | GET | `/api/auth/manager/check-in/today-bookings` | `userId`, `date` (required) |

---

## Testing Instructions

### 1. Open Browser Console
Press `F12` and go to Console tab

### 2. Check Stored Values
```javascript
console.log('User Role:', localStorage.getItem('userRole'));
console.log('User ID:', localStorage.getItem('userId'));
```

**Expected Output:**
```
User Role: MANAGER
User ID: <some-uuid-value>
```

### 3. Check Network Requests
Go to Network tab and look for:
- Request URL should be: `http://localhost:8081/api/auth/manager/viewAvailableMeetingRoom?userId=<uuid>`
- Response should contain array of rooms

### 4. Check Console Logs
You should see detailed logs like:
```
🔄 getAllRooms() called
👤 User Role: MANAGER
🆔 User ID: <uuid>
📡 Fetching rooms from Manager API...
✅ Manager API result: {success: true, data: [...]}
🏢 Loading all rooms for manager dashboard...
📞 Calling getAllRooms()...
📦 Rooms received: [...]
📊 Number of rooms: 5
```

---

## Common Issues & Solutions

### Issue: "User not found" error
**Solution:** Make sure you're logged in as a MANAGER role

### Issue: "Access denied. Only managers can access this endpoint"
**Solution:** Check localStorage - `userRole` must be `MANAGER`

### Issue: Still no rooms showing
**Checklist:**
1. ✅ Backend server is running on port 8081
2. ✅ Logged in as MANAGER user
3. ✅ userId is saved in localStorage
4. ✅ Rooms exist in database
5. ✅ CORS is enabled on backend
6. ✅ Check browser console for specific error messages

### Issue: 404 Not Found
**Solution:** Ensure backend controller path is `/api/auth/manager` not `/api/manager`

---

## Backend TODO (If Needed)

If credit summary endpoint doesn't exist yet, you need to add:

```java
@GetMapping("/credit-summary")
public ResponseEntity<Map<String, Object>> getCreditSummary(@RequestParam UUID userId) {
    verifyManagerRole(userId);
    // Query manager_credit_summary table
    // Return totalCredits, creditsUsed, creditsRemaining, penalty
}
```

---

## Files Modified

1. ✅ `api.js` - Updated all manager endpoints to match backend
2. ✅ `api.js` - Added automatic userId parameter injection
3. ✅ `api.js` - Added detailed logging for debugging
4. ✅ `manager-dashboard.html` - Added logging to room loading function

---

## Next Steps

1. **Clear Browser Cache** - Press `Ctrl + Shift + Delete`
2. **Hard Refresh** - Press `Ctrl + F5`
3. **Re-login** - Logout and login again as MANAGER
4. **Check Console** - Look for the detailed logs to debug any remaining issues
5. **Check Network Tab** - Verify API calls are going to correct endpoints with userId

---

## Expected Backend Response Format

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
    ]
  }
]
```

If your backend returns different field names, let me know and I'll adjust the frontend mapping.
