---

# Project Plan – Round 2

---

## 1. Core Technical Components

- **Rate Limiting**  
  Implement rate limiting to prevent API abuse and ensure overall system stability during peak usage.

- **Security & Authentication**  
  Setup JWT (JSON Web Tokens) for authentication and secure endpoints using `@PreAuthorize` rules for role-based access control.

- **Database Integrity & Concurrency Control**  
  Implement database transactions and concurrency control mechanisms to prevent race conditions during simultaneous booking attempts.  
  (Refer: Image 1.2 – Booking Concurrency Flow)

- **Infrastructure Connectivity**  
  Establish seamless connectivity between:
  - Frontend ↔ Backend  
  - Backend ↔ Database  

- **5-Minute Reservation Timer**  
  Utilize a Redis cache to store temporary **Reservation Keys** with a **300-second TTL (Time-To-Live)**.  
  This ensures a room is locked temporarily while a manager completes the booking process.

- **“Ghosting” Penalty Mechanism**  
  Implement logic to deduct **50 extra credits** as a penalty if a manager fails to **check-in** after successfully booking a room.

- **Automated Bot / Event Trigger**  
  Trigger an automated process immediately after a successful booking to:
  - Send notifications
  - Log booking events
  - Perform post-booking actions

## Work Contributions

## Development Pipeline

- **UI/UX**
  - UI Design ↔ UI Code implementation

- **Connectivity**
  - UI → Backend connectivity testing
  - Backend → Database connectivity testing

- **Full Stack Integration**
  - UI (API Calls) → Backend (Endpoints) → Cloud Database

- **Logic & Security**
  - Authentication & Authorization
  - Transaction management (Race condition prevention)

- **Finalization**
  - Comprehensive project documentation

## Team Assignments

| Name     | Primary Responsibilities        |
|----------|---------------------------------|
| Rishita  | Frontend, Documentation         |
| Aman    | Frontend, UI/UX                 |
| Ankit   | Database, Backend               |
| Pulkit  | Database, Backend               |

## License

© 2025 Bookit.
