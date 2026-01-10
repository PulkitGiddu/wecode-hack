---

# Project Plan – Round 2

---

## 1. Core Technical Components

- **Rate Limiting**  
  Implementing rate limiting using "bucket4j" to prevent API abuse and ensure overall system stability during peak usage.
  DONE ✅

- **Security & Authentication**  
  Setup JWT (JSON Web Tokens) for authentication and secure endpoints using `@PreAuthorize` rules for role-based access control. 
  DONE ✅

- **Database Integrity & Concurrency Control**  
  Implement database transactions and concurrency control mechanisms to prevent race conditions during simultaneous booking attempts.  
  DONE ✅
  Implement Pessimistic Locking for Concurrent Access to book meeting rooms. DONE ✅

- **Infrastructure Connectivity**  
  Establish seamless connectivity between:
  - Frontend ↔ Backend  ✅
  - Backend ↔ Database  ✅

- **“Ghosting” Penalty Mechanism**  
  Implement logic to deduct **50 extra credits** as a penalty if a manager fails to **check-in** after successfully booking a room.
  DONE ✅

- **"Use cache"**
  Use cache to reterive contant data from DB to avoid unnessary Api calls.
  DONE ✅

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
