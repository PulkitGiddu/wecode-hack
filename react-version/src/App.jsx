import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import HomePage from './pages/HomePage';
import Login from './pages/Login';
import Signup from './pages/Signup';
import Dashboard from './pages/Dashboard';
import ManageAmenities from './pages/ManageAmenities';
import AddAmenity from './pages/AddAmenity';
import Analytics from './pages/Analytics';
import ImportUsers from './pages/ImportUsers';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/manage-amenities" element={<ManageAmenities />} />
        <Route path="/add-amenity" element={<AddAmenity />} />
        <Route path="/analytics" element={<Analytics />} />
        <Route path="/import-users" element={<ImportUsers />} />
      </Routes>
    </Router>
  );
}

export default App;