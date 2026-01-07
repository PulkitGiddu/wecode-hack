import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../css/dashboard.css';
import '../js/api.js';

const AdminDashboard = () => {
  const [activeTab, setActiveTab] = useState('rooms');
  const navigate = useNavigate();

  useEffect(() => {
    // Initialize Feather Icons
    if (window.feather) {
      window.feather.replace();
    }

    // Check if user is admin
    const user = JSON.parse(localStorage.getItem('user'));
    if (!user || !user.role?.toLowerCase().includes('admin')) {
      navigate('/login');
    }
  }, [navigate]);

  // Add your admin dashboard functions here
  const openCreateModal = () => {
    // Implement modal open logic
  };

  const closeRoomModal = () => {
    // Implement modal close logic
  };

  const switchTab = (tab) => {
    setActiveTab(tab);
  };

  return (
    <div className="dashboard-container">
      <style jsx>{`
        .dashboard-container {
          padding: 20px;
          max-width: 1400px;
          margin: 0 auto;
        }
        .header {
          display: flex;
          justify-content: space-between;
          align-items: center;
          margin-bottom: 24px;
        }
        .tabs {
          display: flex;
          gap: 8px;
          margin-bottom: 20px;
          border-bottom: 1px solid #e5e7eb;
          padding-bottom: 8px;
        }
        .tab {
          padding: 8px 16px;
          border-radius: 6px;
          cursor: pointer;
          font-weight: 500;
          color: #4b5563;
        }
        .tab.active {
          background-color: #e5e7eb;
          color: #111827;
          font-weight: 600;
        }
        .content {
          background: white;
          border-radius: 8px;
          padding: 20px;
          box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
        }
      `}</style>

      <div className="header">
        <div>
          <h1>Admin Dashboard</h1>
          <p>Manage rooms and amenities</p>
        </div>
        <button 
          className="btn-primary" 
          onClick={openCreateModal}
          style={{
            padding: '8px 16px',
            backgroundColor: '#3b82f6',
            color: 'white',
            border: 'none',
            borderRadius: '6px',
            cursor: 'pointer'
          }}
        >
          + Add {activeTab === 'rooms' ? 'Room' : 'Amenity'}
        </button>
      </div>

      <div className="tabs">
        <div 
          className={`tab ${activeTab === 'rooms' ? 'active' : ''}`}
          onClick={() => switchTab('rooms')}
        >
          Rooms
        </div>
        <div 
          className={`tab ${activeTab === 'amenities' ? 'active' : ''}`}
          onClick={() => switchTab('amenities')}
        >
          Amenities
        </div>
      </div>

      <div className="content">
        {activeTab === 'rooms' ? (
          <div>
            <h2>Rooms Management</h2>
            {/* Add rooms list and management here */}
          </div>
        ) : (
          <div>
            <h2>Amenities Management</h2>
            {/* Add amenities list and management here */}
          </div>
        )}
      </div>
    </div>
  );
};

export default AdminDashboard;