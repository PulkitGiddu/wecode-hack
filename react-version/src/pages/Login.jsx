import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../css/login.css';

function Login() {
  const navigate = useNavigate();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [showPassword, setShowPassword] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    const loginData = { email, password };
    console.log('Frontend sending:', loginData);
    
    try {
      const res = await fetch('http://localhost:8081/api/auth/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(loginData)
      });
      
      if (res.ok) {
        const data = await res.json();
        console.log('Backend response:', data);
        
        // Store user data
        localStorage.setItem('user', JSON.stringify(data));
        
        // Redirect to dashboard
        navigate('/dashboard');
      } else {
        const errorText = await res.text();
        console.error('Login failed:', errorText);
        alert('Login failed. Please check your credentials.');
      }
    } catch (err) {
      console.error('Request failed:', err);
      alert('Login failed. Please try again.');
    }
  };

  return (
    <>
      {/* Login Container */}
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
                <span className="feature-text">Smart Meetings Scheduling</span>
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

        {/* Right Side - Login Form */}
        <div className="login-right">
          <div className="glass-card">
            <div className="form-container">
              <h2 className="welcome-title">Welcome Back</h2>
              <p className="welcome-subtitle">Enter your credentials to access your dashboard</p>
              
              <form onSubmit={handleSubmit}>
                <div className="form-group">
                  <label htmlFor="email" className="form-label">Email</label>
                  <input 
                    type="email" 
                    name="email" 
                    className="form-input" 
                    id="email" 
                    placeholder="user@company.com"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
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
                      value={password}
                      onChange={(e) => setPassword(e.target.value)}
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

                <button type="submit" className="btn-login">Sign In</button>
              </form>
              
              <div style={{textAlign: 'center', marginTop: '20px', color: '#666', fontSize: '14px'}}>
                <p>Don't have an Account? <span onClick={() => navigate('/signup')} style={{color: '#4F46E5', textDecoration: 'none', fontWeight: '500', cursor: 'pointer'}}>Sign up</span></p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}

export default Login;