import React from 'react';
import '../css/dashboard.css';

const ManagerDashboard = () => {
  return (
    <div className="main-container">
      <div className="page-header">
        <h1 className="page-title">Manager Dashboard</h1>
        <p className="page-subtitle">View team bookings and approve requests</p>
      </div>

      <div className="cards-grid">
        <div className="glass-card">
          <h3 className="card-title-text">Team Bookings</h3>
          <p className="card-subtitle">See pending booking requests from your team.</p>
        </div>
        <div className="glass-card">
          <h3 className="card-title-text">Approve Requests</h3>
          <p className="card-subtitle">Approve or reject booking requests.</p>
        </div>
      </div>
    </div>
  );
};

export default ManagerDashboard;
