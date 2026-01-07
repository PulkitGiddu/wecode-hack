// src/pages/HomePage.jsx
import React, { useEffect } from "react";
import "../css/homepage.css";


const HomePage = () => {
  useEffect(() => {
    // Attach JS functions for sidebar and FAQ
    window.openSidebar = () => document.getElementById("sidebar").classList.add("active");
    window.closeSidebar = () => document.getElementById("sidebar").classList.remove("active");

    // FAQ toggle
    window.toggleFaq = (element) => {
      const faqItem = element.parentElement;
      const faqAnswer = faqItem.querySelector(".faq-answer");
      const isActive = faqItem.classList.contains("active");

      document.querySelectorAll(".faq-item").forEach((item) => {
        item.classList.remove("active");
        const answer = item.querySelector(".faq-answer");
        answer.style.maxHeight = "0";
      });

      if (!isActive) {
        faqItem.classList.add("active");
        faqAnswer.style.maxHeight = faqAnswer.scrollHeight + "px";
      }
    };

    // Open first FAQ item by default
    const firstFaqItem = document.querySelector(".faq-item");
    if (firstFaqItem) {
      const firstAnswer = firstFaqItem.querySelector(".faq-answer");
      if (firstAnswer) firstAnswer.style.maxHeight = firstAnswer.scrollHeight + "px";
    }

    // Close sidebar on mobile link click
    const sidebarLinks = document.querySelectorAll(".sidebar a");
    sidebarLinks.forEach((link) => link.addEventListener("click", () => {
      document.getElementById("sidebar").classList.remove("active");
    }));

    // Preloader
    setTimeout(() => {
      const preloader = document.getElementById('preloader');
      if (preloader) {
        preloader.classList.add('fade-out');
        setTimeout(() => {
          preloader.style.display = 'none';
        }, 500);
      }
    }, 0);

    // Scroll to Top Button
    const scrollTop = document.getElementById('scrollTop');
    if (scrollTop) {
      window.addEventListener('scroll', () => {
        if (window.pageYOffset > 300) {
          scrollTop.classList.add('show');
        } else {
          scrollTop.classList.remove('show');
        }
      });

      scrollTop.addEventListener('click', () => {
        window.scrollTo({
          top: 0,
          behavior: 'smooth'
        });
      });
    }

    // Smooth Scroll for Anchor Links
    const anchorLinks = document.querySelectorAll('a[href^="#"]');
    anchorLinks.forEach(anchor => {
      anchor.addEventListener('click', function(e) {
        const href = this.getAttribute('href');
        if (href !== '#' && href.length > 1) {
          e.preventDefault();
          const target = document.querySelector(href);
          if (target) {
            window.closeSidebar();
            target.scrollIntoView({
              behavior: 'smooth',
              block: 'start'
            });
          }
        }
      });
    });
  }, []);

  return (
    <>
      {/* Preloader */}
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

      {/* Scroll to Top */}
      <button className="scroll-top" id="scrollTop">
        <i className="fas fa-arrow-up"></i>
      </button>

      {/* Sidebar */}
      <div className="sidebar" id="sidebar">
        <span className="closebtn" onClick={() => window.closeSidebar()}>&times;</span>
        <a href="#highlights">Highlights</a>
        <a href="#working">See Working</a>
        <a href="#features">Features</a>
        <a href="#faq">FAQ</a>
        <a href="./login.html">Login</a>
      </div>

      {/* Header */}
      <header>
        <div className="container-fluid">
          <nav className="navbar navbar-expand-lg">
            <span className="menu-toggle" onClick={() => window.openSidebar()}>
              <i className="fas fa-bars"></i>
            </span>
            <a className="navbar-brand" href="./homepage.html">
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
                <li className="nav-item"><a href="./login.html" className="btn btn-login">Login</a></li>
              </ul>
            </div>
          </nav>
        </div>
      </header>

      {/* Hero */}
      <section className="hero">
        <div className="container">
          <div className="row align-items-center">
            <div className="col-lg-6">
              <h1>Streamline Your Meeting Room Management</h1>
              <p>Efficiently manage your meeting rooms and enhance collaboration with Bookit's Meeting Room Management Solution</p>
              <a href="./login.html" className="btn btn-hero">Get Started</a>
            </div>
            <div className="col-lg-6 text-center">
              <img src="/assets/img/businesspeople-working-together.png" alt="Business Meeting" draggable="false" />
            </div>
          </div>
        </div>
      </section>

      {/* Highlights */}
      <section id="highlights" className="highlights">
        <div className="container">
          <h2 className="text-center">Key Highlights of Bookit's Rooms</h2>
          <p className="section-subtitle">Discover the powerful features that make room management effortless</p>
          <div className="highlights-grid">
            <div className="highlight-card">
              <div className="highlight-icon"><i className="fas fa-school"></i></div>
              <h4>Add Amenities</h4>
              <p>Assign different rooms with different amenities to enable informed decisions and optimal space utilization. Each room can be configured with specific features like projectors, whiteboards, video conferencing, and more.</p>
            </div>
            <div className="highlight-card">
              <div className="highlight-icon"><i className="fas fa-clock"></i></div>
              <h4>Customize Room Availability</h4>
              <p>Customize room availability by deciding on the days and times for booking with flexible scheduling options. Set recurring schedules, block off maintenance periods, and manage availability with ease.</p>
            </div>
            <div className="highlight-card">
              <div className="highlight-icon"><i className="fas fa-users"></i></div>
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

      {/* Features */}
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

      {/* FAQ */}
      <section id="faq" className="faq">
        <div className="container">
          <h2 className="text-center">Frequently Asked Questions</h2>
          <p className="section-subtitle"></p>

          <div className="faq-container">
            <div className="faq-item active">
              <div className="faq-question" onClick={(e) => window.toggleFaq(e.currentTarget)}>
                <h5 className="faq-question-text">How does Bookit's Meeting Room Management solution work?</h5>
                <div className="faq-toggle"><i className="fas fa-chevron-down"></i></div>
              </div>
              <div className="faq-answer" style={{ maxHeight: "200px" }}>
                <div className="faq-answer-content">
                  Bookit's solution allows you to efficiently manage meeting rooms by providing a centralized platform for booking, analytics, and management with real-time updates. Our intuitive interface makes it easy for employees to find and book the perfect meeting space.
                </div>
              </div>
            </div>

            <div className="faq-item">
              <div className="faq-question" onClick={(e) => window.toggleFaq(e.currentTarget)}>
                <h5 className="faq-question-text">What kind of reports can Bookit generate?</h5>
                <div className="faq-toggle"><i className="fas fa-chevron-down"></i></div>
              </div>
              <div className="faq-answer">
                <div className="faq-answer-content">
                  Generate detailed reports on room usage, booking patterns, peak hours, and utilization metrics to optimize resource allocation. Our analytics dashboard provides insights into booking trends, popular rooms, and helps you make data-driven decisions.
                </div>
              </div>
            </div>

            <div className="faq-item">
              <div className="faq-question" onClick={(e) => window.toggleFaq(e.currentTarget)}>
                <h5 className="faq-question-text">How long does implementation take?</h5>
                <div className="faq-toggle"><i className="fas fa-chevron-down"></i></div>
              </div>
              <div className="faq-answer">
                <div className="faq-answer-content">
                  Implementation time varies based on organization size and customization needs, typically taking 2-4 weeks for complete setup. Our dedicated support team will guide you through every step of the process.
                </div>
              </div>
            </div>

            <div className="faq-item">
              <div className="faq-question" onClick={(e) => window.toggleFaq(e.currentTarget)}>
                <h5 className="faq-question-text">Is Bookit available on mobile devices?</h5>
                <div className="faq-toggle"><i className="fas fa-chevron-down"></i></div>
              </div>
              <div className="faq-answer">
                <div className="faq-answer-content">
                  Yes! Bookit is fully responsive and available as a mobile app for both iOS and Android platforms. Book rooms on the go, check availability, and manage your meetings from anywhere.
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* Footer */}
      <footer>
        <div className="footer-main">
          <div className="container">
            <div className="row">
              <div className="col-lg-4 col-md-6 mb-4">
                <div className="footer-brand">
                  <a href="./homepage.html" className="footer-logo">BOOKIT</a>
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

              <div className="col-lg-2 col-md-6 mb-4">
                <div className="footer-section">
                  <h5>Quick Links</h5>
                  <ul className="footer-links">
                    <li><a href="#highlights">Highlights</a></li>
                    <li><a href="#working">How It Works</a></li>
                    <li><a href="#features">Features</a></li>
                    <li><a href="#faq">FAQ</a></li>
                    <li><a href="./login.html">Login</a></li>
                  </ul>
                </div>
              </div>      
            </div>
          </div>
        </div>

        <div className="footer-bottom">
          <div className="container">
            <div className="footer-bottom-content">
              <p>© 2024 Bookit. All rights reserved.</p>
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
};

export default HomePage;
