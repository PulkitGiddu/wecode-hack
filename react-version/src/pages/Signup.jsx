import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../css/login.css';

function Signup() {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    password: '',
    role: ''
  });
  const [showPassword, setShowPassword] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    const { name, email, password, role } = formData;
    
    if (!role) {
      alert('Please select a role.');
      return;
    }
    
    const roleUpper = role.toUpperCase();
    const signupData = { name, email, password, role: roleUpper };
    console.log('Frontend sending:', signupData);
    
    try {
      const res = await fetch('http://localhost:8081/api/auth/signUp', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(signupData)
      });
      const text = await res.text();
      console.log('Backend response:', text);
      
      if (res.ok) {
        // Redirect based on user type
        switch(roleUpper) {
          case 'ADMIN':
            navigate('/dashboard');
            break;
          case 'MANAGER':
            navigate('/dashboard');
            break;
          case 'MEMBER':
            navigate('/dashboard');
            break;
          default:
            alert('Please select a valid user role.');
        }
      } else {
        alert('Signup failed. Please check your information.');
      }
    } catch (err) {
      console.error('Request failed:', err);
      alert('Signup failed. Please try again.');
    }
  };

  return (
    <>
      {/* Signup Container */}
      <div className="login-container">
        {/* Left Side - Branding */}
        <div className="login-left">
          <div className="brand-content">
            <div className="logo-wrapper">
              <img src="/assets/img/bookitlogo.png" alt="Bookit" className="logo" />
            </div>
            
            <div className="intro-text">
              <h2 className="intro-heading">Transform Your Meeting Experience</h2>
              <p className="intro-description">
                Streamline room bookings, optimize space utilization, and enhance collaboration across your organization with our <em>intelligent</em> meeting room management platform.
              </p>
            </div>

            <div className="features-list">
              <div className="feature-item">
                <span className="feature-icon">✓</span>
                <span className="feature-text">Smart room scheduling</span>
              </div>
              <div className="feature-item">
                <span className="feature-icon">✓</span>
                <span className="feature-text">Real-time availability</span>
              </div>
              <div className="feature-item">
                <span className="feature-icon">✓</span>
                <span className="feature-text">Seamless integrations</span>
              </div>
            </div>
          </div>
        </div>

        {/* Right Side - Signup Form */}
        <div className="login-right">
          <div className="glass-card">
            <div className="form-container">
              <h2 className="welcome-title">Create Your Account</h2>
              <p className="welcome-subtitle">Enter your details to get started with Bookit.</p>
              
              <form onSubmit={handleSubmit}>
                <div className="form-group">
                  <label htmlFor="name" className="form-label">Name</label>
                  <input 
                    type="text" 
                    name="name" 
                    className="form-input" 
                    id="name" 
                    placeholder="Jane Doe"
                    value={formData.name}
                    onChange={handleChange}
                    required
                  />
                </div>

                <div className="form-group">
                  <label htmlFor="email" className="form-label">Email</label>
                  <input 
                    type="email" 
                    name="email" 
                    className="form-input" 
                    id="email" 
                    placeholder="user@company.com"
                    value={formData.email}
                    onChange={handleChange}
                    required
                  />
                </div>

                <div className="form-group">
                  <label htmlFor="password" className="form-label">Password</label>
                  <div className="password-wrapper">
                    <input 
                      type={showPassword ? 'text' : 'password'}
                      name="password" 
                      className="form-input" 
                      id="password" 
                      placeholder="Enter password"
                      value={formData.password}
                      onChange={handleChange}
                      required
                    />
                    <button type="button" className={`password-toggle ${showPassword ? 'active' : ''}`} onClick={() => setShowPassword(!showPassword)}>
                      <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                        <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"></path>
                        <circle cx="12" cy="12" r="3"></circle>
                      </svg>
                    </button>
                  </div>
                </div>

                <div className="form-group">
                  <label className="form-label">Role</label>
                  <div className="role-selector">
                    <label className="role-option">
                      <input type="radio" name="role" value="admin" id="admin" onChange={handleChange} required />
                      <span className="role-card">Admin</span>
                    </label>
                    <label className="role-option">
                      <input type="radio" name="role" value="manager" id="manager" onChange={handleChange} />
                      <span className="role-card">Manager</span>
                    </label>
                    <label className="role-option">
                      <input type="radio" name="role" value="member" id="member" onChange={handleChange} />
                      <span className="role-card">Member</span>
                    </label>
                  </div>
                </div>

                <button type="submit" className="btn-login">Create Account</button>
              </form>
              
              <div style={{textAlign: 'center', marginTop: '20px', color: '#666', fontSize: '14px'}}>
                <p>Already have an account? <span onClick={() => navigate('/login')} style={{color: '#4F46E5', textDecoration: 'none', fontWeight: '500', cursor: 'pointer'}}>Sign in</span></p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}

export default Signup;