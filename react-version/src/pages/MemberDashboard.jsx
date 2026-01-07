import React from 'react';
import '../css/dashboard.css';

const MemberDashboard = () => {
  return (
    <div className="main-container">
      <div className="page-header">
        <h1 className="page-title">Member Dashboard</h1>
        <p className="page-subtitle">Book rooms and view your upcoming meetings</p>
      </div>

      <div className="cards-grid">
        <div className="glass-card">
          <h3 className="card-title-text">Book a Room</h3>
          <p className="card-subtitle">Search available rooms and make bookings.</p>
        </div>
        <div className="glass-card">
          <h3 className="card-title-text">My Meetings</h3>
          <p className="card-subtitle">See your upcoming reservations.</p>
        </div>
      </div>
    </div>
  );
};

export default MemberDashboard;
