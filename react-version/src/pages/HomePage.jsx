import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../css/homepage.css';

function HomePage() {
  const navigate = useNavigate();
  const [sidebarActive, setSidebarActive] = useState(false);
  const [showScrollTop, setShowScrollTop] = useState(false);
  const [faqExpanded, setFaqExpanded] = useState([true, false, false, false]);

  useEffect(() => {
    // Hide preloader
    setTimeout(() => {
      const preloader = document.getElementById('preloader');
      if (preloader) {
        preloader.classList.add('fade-out');
        setTimeout(() => {
          preloader.style.display = 'none';
        }, 500);
      }
    }, 1500);

    // Scroll to Top Button
    const handleScroll = () => {
      setShowScrollTop(window.pageYOffset > 300);
    };
    window.addEventListener('scroll', handleScroll);
    return () => window.removeEventListener('scroll', handleScroll);
  }, []);

  const toggleFaq = (index) => {
    const newExpanded = [...faqExpanded];
    newExpanded[index] = !newExpanded[index];
    setFaqExpanded(newExpanded);
  };

  const scrollToTop = () => {
    window.scrollTo({
      top: 0,
      behavior: 'smooth'
    });
  };

  return (
    <>
      {/* Improved Loader */}
      <div className="preloader" id="preloader">
        <div className="loader-content">
          <div className="loader-logo">BOOKIT</div>
          <div className="loader-dots">
            <div className="dot"></div>
            <div className="dot"></div>
            <div className="dot"></div>
          </div>
        </div>
      </div>

      {/* Scroll to Top Button */}
      <button className={`scroll-top ${showScrollTop ? 'show' : ''}`} id="scrollTop" onClick={scrollToTop}>
        <i className="fas fa-arrow-up"></i>
      </button>

      {/* Sidebar */}
      <div className={`sidebar ${sidebarActive ? 'active' : ''}`} id="sidebar">
        <span className="closebtn" onClick={() => setSidebarActive(false)}>&times;</span>
        <a href="#highlights" onClick={() => setSidebarActive(false)}>Highlights</a>
        <a href="#working" onClick={() => setSidebarActive(false)}>See Working</a>
        <a href="#features" onClick={() => setSidebarActive(false)}>Features</a>
        <a href="#faq" onClick={() => setSidebarActive(false)}>FAQ</a>
        <a href="#" onClick={(e) => { e.preventDefault(); setSidebarActive(false); navigate('/login'); }}>Login</a>
      </div>

      {/* Header */}
      <header>
        <div className="container-fluid">
          <nav className="navbar navbar-expand-lg">
            <span className="menu-toggle" onClick={() => setSidebarActive(true)}>
              <i className="fas fa-bars"></i>
            </span>
            <a href="#" onClick={(e) => { e.preventDefault(); navigate('/'); }} className="navbar-brand">
              <img src="/assets/img/bookitlogo.png" alt="Bookit" />
            </a>
            <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
              <span className="navbar-toggler-icon"></span>
            </button>
            <div className="collapse navbar-collapse" id="navbarNav">
              <ul className="navbar-nav ms-auto align-items-center">
                <li className="nav-item"><a className="nav-link" href="#highlights">Highlights</a></li>
                <li className="nav-item"><a className="nav-link" href="#working">See Working</a></li>
                <li className="nav-item"><a className="nav-link" href="#features">Features</a></li>
                <li className="nav-item"><a className="nav-link" href="#faq">FAQ</a></li>
                <li className="nav-item"><a href="#" onClick={(e) => { e.preventDefault(); navigate('/login'); }} className="btn btn-login">Login</a></li>
              </ul>
            </div>
          </nav>
        </div>
      </header>

      {/* Hero Section */}
      <section className="hero">
        <div className="container">
          <div className="row align-items-center">
            <div className="col-lg-6">
              <h1>Streamline Your Meeting Room Management</h1>
              <p>Efficiently manage your meeting rooms and enhance collaboration with Bookit's Meeting Room Management Solution</p>
              <a href="#" onClick={(e) => { e.preventDefault(); navigate('/login'); }} className="btn btn-hero">Get Started</a>
            </div>
            <div className="col-lg-6 text-center">
              <img src="/assets/img/businesspeople-working-together.png" alt="Business Meeting" draggable="false" />
            </div>
          </div>
        </div>
      </section>

      {/* Improved Key Highlights */}
      <section id="highlights" className="highlights">
        <div className="container">
          <h2 className="text-center">Key Highlights of Bookit's Rooms</h2>
          <p className="section-subtitle">Discover the powerful features that make room management effortless</p>
          
          <div className="highlights-grid">
            <div className="highlight-card">
              <div className="highlight-icon">
                <i className="fas fa-school"></i>
              </div>
              <h4>Add Amenities</h4>
              <p>Assign different rooms with different amenities to enable informed decisions and optimal space utilization. Each room can be configured with specific features like projectors, whiteboards, video conferencing, and more.</p>
            </div>

            <div className="highlight-card">
              <div className="highlight-icon">
                <i className="fas fa-clock"></i>
              </div>
              <h4>Customize Room Availability</h4>
              <p>Customize room availability by deciding on the days and times for booking with flexible scheduling options. Set recurring schedules, block off maintenance periods, and manage availability with ease.</p>
            </div>

            <div className="highlight-card">
              <div className="highlight-icon">
                <i className="fas fa-users"></i>
              </div>
              <h4>Set Room Capacity</h4>
              <p>Set the maximum number of people the room can occupy for better space management. Ensure optimal utilization and prevent overcrowding with intelligent capacity planning.</p>
            </div>
          </div>
        </div>
      </section>

      {/* How It Works */}
      <section id="working" className="how-it-works">
        <div className="container">
          <h2 className="text-center">How It Works</h2>
          <p className="section-subtitle">Simple, efficient, and powerful room management in three steps</p>
          <div className="row mt-5">
            <div className="col-md-4">
              <div className="work-card">
                <i className="fas fa-building"></i>
                <h5>Easily Create Rooms</h5>
                <p>Create rooms on Bookit's web portal with all necessary details and configurations.</p>
              </div>
            </div>
            <div className="col-md-4">
              <div className="work-card">
                <i className="fas fa-calendar-check"></i>
                <h5>Efficiently Book Meeting Rooms</h5>
                <p>Book rooms with ease through the web portal or mobile app with real-time availability.</p>
              </div>
            </div>
            <div className="col-md-4">
              <div className="work-card">
                <i className="fas fa-chart-line"></i>
                <h5>Informed Decisions via Analytics</h5>
                <p>Utilize analytics to optimize room usage, booking patterns, and resource allocation.</p>
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* Key Features */}
      <section id="features" className="features">
        <div className="container">
          <h2 className="text-center">Key Features</h2>
          <p className="section-subtitle">Everything you need for seamless meeting room management</p>
          <div className="row mt-5">
            <div className="col-md-3">
              <div className="feature-card">
                <i className="fas fa-calendar-alt"></i>
                <h5>Room Booking</h5>
                <p>Enable employees to book meeting rooms easily with intuitive interface.</p>
              </div>
            </div>
            <div className="col-md-3">
              <div className="feature-card">
                <i className="fas fa-clipboard-check"></i>
                <h5>Room Amenities</h5>
                <p>Choose meeting rooms with the required amenities for your needs.</p>
              </div>
            </div>
            <div className="col-md-3">
              <div className="feature-card">
                <i className="fas fa-user-plus"></i>
                <h5>Invite Easily</h5>
                <p>Send invites to employees and visitors seamlessly for meetings.</p>
              </div>
            </div>
            <div className="col-md-3">
              <div className="feature-card">
                <i className="fas fa-clock"></i>
                <h5>Room Availability</h5>
                <p>Quickly find available times for booking rooms in real-time.</p>
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* Improved FAQ Section */}
      <section id="faq" className="faq">
        <div className="container">
          <h2 className="text-center">Frequently Asked Questions</h2>
          <p className="section-subtitle"></p>
          <p className="section-subtitle"></p>
          <p className="section-subtitle"></p>
          
          <div className="faq-container">
            <div className={`faq-item ${faqExpanded[0] ? 'active' : ''}`}>
              <div className="faq-question" onClick={() => toggleFaq(0)}>
                <h5 className="faq-question-text">How does Bookit's Meeting Room Management solution work?</h5>
                <div className="faq-toggle">
                  <i className="fas fa-chevron-down"></i>
                </div>
              </div>
              <div className="faq-answer" style={{maxHeight: faqExpanded[0] ? '200px' : '0px'}}>
                <div className="faq-answer-content">
                  Bookit's solution allows you to efficiently manage meeting rooms by providing a centralized platform for booking, analytics, and management with real-time updates. Our intuitive interface makes it easy for employees to find and book the perfect meeting space.
                </div>
              </div>
            </div>

            <div className={`faq-item ${faqExpanded[1] ? 'active' : ''}`}>
              <div className="faq-question" onClick={() => toggleFaq(1)}>
                <h5 className="faq-question-text">What kind of reports can Bookit generate?</h5>
                <div className="faq-toggle">
                  <i className="fas fa-chevron-down"></i>
                </div>
              </div>
              <div className="faq-answer" style={{maxHeight: faqExpanded[1] ? '200px' : '0px'}}>
                <div className="faq-answer-content">
                  Generate detailed reports on room usage, booking patterns, peak hours, and utilization metrics to optimize resource allocation. Our analytics dashboard provides insights into booking trends, popular rooms, and helps you make data-driven decisions.
                </div>
              </div>
            </div>

            <div className={`faq-item ${faqExpanded[2] ? 'active' : ''}`}>
              <div className="faq-question" onClick={() => toggleFaq(2)}>
                <h5 className="faq-question-text">How long does implementation take?</h5>
                <div className="faq-toggle">
                  <i className="fas fa-chevron-down"></i>
                </div>
              </div>
              <div className="faq-answer" style={{maxHeight: faqExpanded[2] ? '200px' : '0px'}}>
                <div className="faq-answer-content">
                  Implementation time varies based on organization size and customization needs, typically taking 2-4 weeks for complete setup. Our dedicated support team will guide you through every step of the process.
                </div>
              </div>
            </div>

            <div className={`faq-item ${faqExpanded[3] ? 'active' : ''}`}>
              <div className="faq-question" onClick={() => toggleFaq(3)}>
                <h5 className="faq-question-text">Is Bookit available on mobile devices?</h5>
                <div className="faq-toggle">
                  <i className="fas fa-chevron-down"></i>
                </div>
              </div>
              <div className="faq-answer" style={{maxHeight: faqExpanded[3] ? '200px' : '0px'}}>
                <div className="faq-answer-content">
                  Yes! Bookit is fully responsive and available as a mobile app for both iOS and Android platforms. Book rooms on the go, check availability, and manage your meetings from anywhere.
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* Enhanced Footer */}
      <footer>
        <div className="footer-main">
          <div className="container">
            <div className="row">
              {/* Brand Column */}
              <div className="col-lg-4 col-md-6 mb-4">
                <div className="footer-brand">
                  <a href="#" onClick={(e) => { e.preventDefault(); navigate('/'); }} className="footer-logo">BOOKIT</a>
                  <p className="footer-tagline">
                    Streamlining meeting room management with innovative solutions. Transform the way your organization books and manages spaces.
                  </p>
                  <div className="footer-social">
                    <a href="#" className="social-btn" aria-label="Facebook"><i className="fab fa-facebook-f"></i></a>
                    <a href="#" className="social-btn" aria-label="Twitter"><i className="fab fa-twitter"></i></a>
                    <a href="#" className="social-btn" aria-label="LinkedIn"><i className="fab fa-linkedin-in"></i></a>
                    <a href="#" className="social-btn" aria-label="Instagram"><i className="fab fa-instagram"></i></a>
                  </div>
                </div>
              </div>

              {/* Quick Links */}
              <div className="col-lg-2 col-md-6 mb-4">
                <div className="footer-section">
                  <h5>Quick Links</h5>
                  <ul className="footer-links">
                    <li><a href="#highlights">Highlights</a></li>
                    <li><a href="#working">How It Works</a></li>
                    <li><a href="#features">Features</a></li>
                    <li><a href="#faq">FAQ</a></li>
                    <li><a href="#" onClick={(e) => { e.preventDefault(); navigate('/login'); }}>Login</a></li>
                  </ul>
                </div>
              </div>      
            </div>
          </div>
        </div>

        {/* Footer Bottom */}
        <div className="footer-bottom">
          <div className="container">
            <div className="footer-bottom-content">
              <p className="footer-copyright">
                © 2024 Bookit. All rights reserved.
              </p>
              <div className="footer-legal-links">
                <a href="#">Terms & Conditions</a>
                <a href="#">Privacy Policy</a>
                <a href="#">Cookie Policy</a>
                <a href="#">Manage Cookies</a>
              </div>
            </div>
          </div>
        </div>
      </footer>
    </>
  );
}

export default HomePage;
