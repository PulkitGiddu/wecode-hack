# 🏢 Automated Meeting Room Booking System  
**Team Name: 404 Not Found**



## 📌 Executive Summary
The **Automated Meeting Room Booking System**, developed by **Team 404 Not Found**, is a centralized enterprise-grade platform designed to optimize office meeting room utilization. It introduces a **credit-based booking economy** combined with **Role-Based Access Control (RBAC)** to ensure fair usage, eliminate scheduling conflicts, and enforce accountability through an automated and professional workflow.

---

## 🎯 Problem Statement & Scope

### Current Challenges
- **Manual Overhead:** Manual scheduling results in double bookings and operational inefficiencies.
- **Resource Inefficiency:** Premium meeting rooms are frequently misused for small meetings without cost accountability.
- **Lack of Access Control:** No clear separation between administrative configuration, booking authority, and general visibility.

### Proposed Scope
The system delivers an end-to-end digital solution to:
- Search and book meeting rooms based on seating capacity and amenities.
- Manage a dynamic credit economy for managers.
- Provide real-time visibility of room availability and bookings to all employees.

---

## 👥 User Roles & Authorization Model

The system enforces a **Layered Authorization (RBAC)** model.

| Role    | Key Permissions |
|-------- |----------------|
| Admin   | Create, configure, and edit meeting rooms; manage amenities and pricing |
| Manager | Search rooms, book meetings, and spend allocated credits |
| Member  | View-only access to room schedules and booking status |

---

## 💳 Credit Economy Engine

### Pricing Logic
The system follows a **utility-based pricing model**.

---

### Credit Cost Sheet

#### 🪑 Room Size
| Capacity       | Credits / Hr |
|--------------- |-------------|
| ≤ 5 Seats      | 0           |
| 6–10 Seats     | 10          |
| > 10 Seats     | 20          |

#### ⚙️ Amenities
| Amenity                          | Credits / Hr |
|---------------------------------|-------------|
| Wi-Fi / TV / Coffee Machine     | 10 each     |
| Conference Call Facility        | 15          |
| Projector / Whiteboard / Water  | 5 each      |

---

### Example Booking
**Meeting:** Team Training  
**Room Size:** 12 Seats → 20 credits  
**Amenities:** Projector (5) + Wi-Fi (10)

**Total Cost:** `35 Credits / Hour`

---

## 🔁 Credit Wallet Rules
- **Manager Wallet:** 2000 credits allocated by default.
- **Automated Reset:** All manager wallets reset to 2000 credits every **Monday at 6:00 AM** via Cron Job.
- **Restrictions:** Admins and Members have 0 credits and cannot initiate bookings.

---

## 📋 Mandatory Amenities by Meeting Type

| Meeting Type        | Mandatory Amenities              |
|-------------------- |---------------------------------|
| Classroom Training  | Whiteboard, Projector           |
| Online Training     | Wi-Fi, Projector                |
| Conference Call     | Conference Call Facility        |
| Business Meeting    | Projector                       |

The system automatically enforces these rules during booking.

---

## 🏗️ Technical Architecture

### Technology Stack
- **Frontend:** HTML, CSS, JavaScript (Component-based UI)
- **Backend:** Java with Spring Boot (Microservices-ready architecture)
- **Database:** PostgreSQL (Cloud-hosted)

---

## 🖥️ UI/UX Workflow

### Pages & Dashboards
- **Home Page:** Login and navigation hub.
- **Admin Dashboard:**  
  - Create Room  
  - Edit Room  
  - Manage amenities and credit cost mappings
- **Manager Portal:**  
  - Advanced Search & Filter  
  - Booking Confirmation with real-time credit deduction
- **Member View:**  
  - Read-only calendar/grid view of current and upcoming bookings

---
##🧭 Frontend Workflow Diagram
flowchart TD
    %% Homepage and Login
    A[Homepage (Public Access)] --> B[Login / Signup]
    B --> C[Role Identification<br/>Admin / Manager / Member]

    %% Dashboards
    subgraph Dashboards
        D[Admin Dashboard]
        E[Manager Dashboard]
        F[Member Dashboard]
    end

    C --> D
    C --> E
    C --> F

    %% Admin Features
    subgraph "Admin Features"
        D --> D1[Create Rooms]
        D --> D2[Manage Amenities]
        D --> D3[Set Credit Cost]
        D --> D4[Import Users]
    end

    %% Manager Features
    subgraph "Manager Booking Workflow"
        E --> E1[Organize Meetings / Book]
        E1 --> E2[Search & Select Room<br/>(Capacity & Amenities)]
        E2 --> E3[Credit Validation]
        E3 --> E4[Room Lock (5 min)]
        E4 --> E5[Enter Booking Details<br/>(Date, Time, Team)]
        E5 --> E6[Confirmation & Credits Deducted]
        E6 --> E7[Analyze Meetings / Reports]
        E7 --> E8[Team Management]
        E8 --> E9[Notifications & Alerts]
    end

    %% Member Features
    subgraph "Member Features"
        F --> F1[View Room Schedules]
        F --> F2[View Meeting Details]
    end


## 👨‍💻 Implementation Strategy & Team Distribution

### Sub-Team A: UI/UX  
**Members:** Amanpreet, Rishita  
- Designing responsive layouts using CSS frameworks  
- Creating dummy-data UI prototypes for early validation  
- Maintaining technical documentation  

### Sub-Team B: Backend & Database  
**Members:** Ankit, Pulkit  
- Developing RESTful APIs and business logic  
- Implementing database locking to avoid race conditions  
- Scheduling the weekly credit reset Cron Job  
- Ensuring reliable testing using JUnit  

---

## 🚀 Round 2 Roadmap (Future Enhancements)

- **Intelligent Queueing:**  
  Temporary booking holds during peak hours.
- **Alert Reporting:**  
  Real-time alerts for unauthorized access attempts.
- **Advanced Logging:**  
  Production-grade monitoring and diagnostics.

---

## 📄 License
This project is intended for academic and internal enterprise use. Licensing can be defined based on organizational requirements.
