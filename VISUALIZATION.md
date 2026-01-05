# 📊 Data Structure Visualization

## System Architecture

```
┌─────────────────────────────────────────────────────────┐
│                  Frontend Application                    │
│                                                          │
│  ┌─────────────────────────────────────────────────┐   │
│  │            API Layer (js/api.js)                 │   │
│  │                                                   │   │
│  │  ┌──────────────┐        ┌──────────────┐      │   │
│  │  │  getAllRooms │        │getAllAmenities│     │   │
│  │  └──────┬───────┘        └──────┬────────┘      │   │
│  │         │                        │               │   │
│  │         └────────┬───────────────┘               │   │
│  │                  ↓                               │   │
│  │         USE_DUMMY_DATA?                          │   │
│  │                  │                               │   │
│  │         ┌────────┴────────┐                      │   │
│  │         ↓                 ↓                      │   │
│  │    ┌────────┐        ┌─────────┐               │   │
│  │    │ Backend│        │  Dummy  │               │   │
│  │    │   API  │        │  Data   │               │   │
│  │    └───┬────┘        └────┬────┘               │   │
│  │        │                  │                     │   │
│  │        └──────┬───────────┘                     │   │
│  │               ↓                                  │   │
│  │          Return Data                            │   │
│  └───────────────────────────────────────────────┘   │
└──────────────────────────────────────────────────────┘
```

---

## Database Schema

### 📋 Amenities Table
```
┌──────────────┬──────────────┬─────────────┬───────────┐
│ amenity_id   │ amenityName │ creditCost │ is_active │
│ (UUID)       │ (VARCHAR 50) │   (INT)     │ (BOOLEAN) │
├──────────────┼──────────────┼─────────────┼───────────┤
│ a1e4f8c2...  │ PROJECTOR    │      5      │   true    │
│ a1e4f8c2...  │ WIFI         │     10      │   true    │
│ a1e4f8c2...  │ CONFERENCE.. │     15      │   true    │
│ a1e4f8c2...  │ WHITEBOARD   │      5      │   true    │
│ a1e4f8c2...  │ WATER_DISP.. │      5      │   true    │
│ a1e4f8c2...  │ TV           │     10      │   true    │
│ a1e4f8c2...  │ COFFEE_MAC.. │     10      │   true    │
└──────────────┴──────────────┴─────────────┴───────────┘
```

### 🏢 Meeting Rooms + Amenities (Combined View)
```
┌──────────┬───────────┬──────────┬───────────────────────────┬───────┬──────────┐
│   Room   │   Type    │ Capacity │        Amenities          │ Base  │  Total   │
│   Name   │           │          │                           │ Cost  │  Cost    │
├──────────┼───────────┼──────────┼───────────────────────────┼───────┼──────────┤
│ Bhimtal  │  Huddle   │    20    │ COFFEE_MACHINE, WIFI      │ 100  │  120    │
│          │           │          │ (10 + 10)               │       │          │
├──────────┼───────────┼──────────┼───────────────────────────┼───────┼──────────┤
│ Nainital │ Conference│    50    │ PROJECTOR, WIFI,          │ 200  │  235    │
│          │           │          │ WHITEBOARD, CONFERENCE    │       │          │
│          │           │          │ (5+10+5+15)           │       │          │
├──────────┼───────────┼──────────┼───────────────────────────┼───────┼──────────┤
│ Ranikhet │  Meeting  │    30    │ TV, WIFI, WATER_DISPENSER │ 150  │  175    │
│          │           │          │ (10+10+5)              │       │          │
├──────────┼───────────┼──────────┼───────────────────────────┼───────┼──────────┤
│Mussoorie │Board Room │   100    │ PROJECTOR, CONFERENCE,    │ 300  │  345    │
│          │           │          │ TV, WIFI, COFFEE_MACHINE  │       │          │
│          │           │          │ (5+15+10+10+10)      │       │          │
├──────────┼───────────┼──────────┼───────────────────────────┼───────┼──────────┤
│ Dehradun │  Huddle   │    15    │ WHITEBOARD, WIFI          │  80  │   95    │
│          │           │          │ (5+10)                  │       │          │
└──────────┴───────────┴──────────┴───────────────────────────┴───────┴──────────┘
```

---

## 💰 Cost Calculation Formula

```
Room Total Cost = Base Per Hour Cost + Σ(Amenity Costs)

Example: Bhimtal Room
  Base Cost:          100
  + COFFEE_MACHINE:    10
  + WIFI:              10
  ─────────────────────────
  Total:              120 per hour
```

---

## 🔄 Data Flow

### Fetching Rooms/Amenities
```
1. User calls getAllRooms() or getAllAmenities()
                    ↓
2. Check: USE_DUMMY_DATA flag or forceDummy parameter
                    ↓
            ┌───────┴────────┐
            ↓                ↓
        true              false
            ↓                ↓
    Return Dummy      Try Backend API
        Data                 ↓
                    ┌────────┴────────┐
                    ↓                 ↓
                Success           Error
                    ↓                 ↓
            Return Backend    Fallback to
                Data          Dummy Data
```

---

## 📦 JSON Structure Examples

### Room Object
```json
{
  "roomId": "649cc30f-622b-462d-8ff4-e3dafb2b9195",
  "roomName": "Bhimtal",
  "roomType": "Huddle",
  "seatingCapacity": 20,
  "perHourCost": 100,
  "amenities": ["COFFEE_MACHINE", "WIFI"],
  "roomCost": 120,
  "isActive": true
}
```

### Amenity Object
```json
{
  "amenity_id": "a1e4f8c2-1234-4567-89ab-cdef12345671",
  "amenityName": "PROJECTOR",
  "creditCost": 5,
  "is_active": true
}
```

---

## 🎯 API Endpoints (Backend)

```
Base URL: http://localhost:8081/api

Admin Endpoints:
├── GET  /admin/getAllRoom          → Get all rooms
├── GET  /admin/getRoomById/:id     → Get room by ID
├── POST /admin/createRoom          → Create new room
├── PUT  /admin/updateRoom          → Update room
├── DELETE /admin/rooms/:id         → Delete room
│
├── GET  /admin/getAllAmenities     → Get all amenities
├── GET  /admin/getAmenitieById/:id → Get amenity by ID
├── POST /admin/addAmenitie         → Create amenity
├── PUT  /admin/updateAmenitie      → Update amenity
└── DELETE /admin/amenities/:id     → Delete amenity
```

---

## 🛠️ Utility Functions

```javascript
// Calculate total room cost
calculateRoomCost(baseCost, amenityNames)
→ Returns: number (total cost)

// Get amenity by name
getAmenityByName('PROJECTOR')
→ Returns: { amenity_id, amenityName, creditCost, is_active }

// Format amenity name
formatAmenityName('COFFEE_MACHINE')
→ Returns: 'Coffee Machine'

// Toggle data source
setDataSource(true)   // Use dummy data
setDataSource(false)  // Use backend API

// Check current source
getDataSource()
→ Returns: 'dummy' or 'backend'
```

---

## 📁 File Structure

```
wecode-hack/
├── js/
│   ├── api.js              ← Main API layer (UPDATED)
│   ├── dashboard.js
│   ├── homepage.js
│   └── ...
│
├── DATA_MANAGEMENT.md      ← Complete documentation (NEW)
├── QUICKSTART.md           ← Quick start guide (NEW)
├── VISUALIZATION.md        ← This file (NEW)
├── data-test.html          ← Test page (NEW)
├── dummy-data-sample.json  ← Sample data (NEW)
│
├── admindashboard.html
├── homepage.html
└── ...
```

---

## ✅ Ready to Use!

1. **Test the system**: Open `data-test.html` in browser
2. **Read docs**: Check `DATA_MANAGEMENT.md` for details
3. **Quick reference**: See `QUICKSTART.md` for examples
4. **Sample data**: Look at `dummy-data-sample.json`

The system is configured to **automatically fall back to dummy data** if the backend is unavailable!
