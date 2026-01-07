import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import '../css/addamenity.css';
import '../js/manageamenities.js';
import '../js/homepage.js';

function AddAmenity() {
  const navigate = useNavigate();
  useEffect(() => {
    if (window.feather) {
      window.feather.replace();
    }
  }, []);

  return (
    <>
      {/* loader */}
      <div id="myloader" className="preloader">
        <img id="myloader" className="preloader-image" src="data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIiB2ZXJzaW9uPSIxLjEiIHN0eWxlPSItLWFuaW1hdGlvbi1zdGF0ZTogcnVubmluZzsiPgo8c3R5bGU+CiAgICAgICAgOnJvb3QgewogICAgICAgICAgLS1hbmltYXRpb24tc3RhdGU6IHBhdXNlZDsKICAgICAgICB9CgogICAgICAgIC8qIHVzZXIgcGlja2VkIGEgdGhlbWUgd2hlcmUgdGhlICJyZWd1bGFyIiBzY2hlbWUgaXMgZGFyayAqLwogICAgICAgIC8qIHVzZXIgcGlja2VkIGEgdGhlbWUgYSBsaWdodCBzY2hlbWUgYW5kIGFsc28gZW5hYmxlZCBhIGRhcmsgc2NoZW1lICovCgogICAgICAgIC8qIGRlYWwgd2l0aCBsaWdodCBzY2hlbWUgZmlyc3QgKi8KICAgICAgICBAbWVkaWEgKHByZWZlcnMtY29sb3Itc2NoZW1lOiBsaWdodCkgewogICAgICAgICAgOnJvb3QgewogICAgICAgICAgICAtLXByaW1hcnk6ICMwMDAwMDA7CiAgICAgICAgICAgIC0tc2Vjb25kYXJ5OiAjZmZmZmZmOwogICAgICAgICAgICAtLXRlcnRpYXJ5OiAjMzM2OUZGOwogICAgICAgICAgICAtLWhpZ2hsaWdodDogIzMzNjlGRjsKICAgICAgICAgICAgLS1zdWNjZXNzOiAjM2Q4NTRkOwogICAgICAgICAgfQogICAgICAgIH0KCiAgICAgICAgLyogdGhlbiBkZWFsIHdpdGggZGFyayBzY2hlbWUgKi8KICAgICAgICBAbWVkaWEgKHByZWZlcnMtY29sb3Itc2NoZW1lOiBkYXJrKSB7CiAgICAgICAgICA6cm9vdCB7CiAgICAgICAgICAgIC0tcHJpbWFyeTogIzAwMDAwMDsKICAgICAgICAgICAgLS1zZWNvbmRhcnk6ICNmZmZmZmY7CiAgICAgICAgICAgIC0tdGVydGlhcnk6ICMzMzY5RkY7CiAgICAgICAgICAgIC0taGlnaGxpZ2h0OiAjMzM2OUZGOwogICAgICAgICAgICAtLXN1Y2Nlc3M6ICMzZDg1NGQ7CiAgICAgICAgICB9CiAgICAgICAgfQoKICAgICAgICAvKiB0aGVzZSBzdHlsZXMgbmVlZCB0byBsaXZlIGhlcmUgYmVjYXVzZSB0aGUgU1ZHIGhhcyBhIGRpZmZlcmVudCBzY29wZSAqLwogICAgICAgIC5kb3RzIHsKICAgICAgICAgIGFuaW1hdGlvbi1uYW1lOiBsb2FkZXI7CiAgICAgICAgICBhbmltYXRpb24tdGltaW5nLWZ1bmN0aW9uOiBlYXNlLWluLW91dDsKICAgICAgICAgIGFuaW1hdGlvbi1kdXJhdGlvbjogM3M7CiAgICAgICAgICBhbmltYXRpb24taXRlcmF0aW9uLWNvdW50OiBpbmZpbml0ZTsKICAgICAgICAgIGFuaW1hdGlvbi1wbGF5LXN0YXRlOiB2YXIoLS1hbmltYXRpb24tc3RhdGUpOwogICAgICAgICAgc3Ryb2tlOiAjZmZmOwogICAgICAgICAgc3Ryb2tlLXdpZHRoOiAwLjVweDsKICAgICAgICAgIHRyYW5zZm9ybS1vcmlnaW46IGNlbnRlcjsKICAgICAgICAgIG9wYWNpdHk6IDA7CiAgICAgICAgICByOiBtYXgoMXZ3LCAxMXB4KTsKICAgICAgICAgIGN5OiA1MCU7CiAgICAgICAgICBmaWx0ZXI6IHNhdHVyYXRlKDIpIG9wYWNpdHkoMC44NSk7CiAgICAgICAgICBmaWxsOiB2YXIoLS10ZXJ0aWFyeSk7CiAgICAgICAgfQoKICAgICAgICAuZG90czpudGgtY2hpbGQoMikgewogICAgICAgICAgYW5pbWF0aW9uLWRlbGF5OiAwLjE1czsKICAgICAgICB9CgogICAgICAgIC5kb3RzOm50aC1jaGlsZCgzKSB7CiAgICAgICAgICBhbmltYXRpb24tZGVsYXk6IDAuM3M7CiAgICAgICAgfQoKICAgICAgICAuZG90czpudGgtY2hpbGQoNCkgewogICAgICAgICAgYW5pbWF0aW9uLWRlbGF5OiAwLjQ1czsKICAgICAgICB9CgogICAgICAgIC5kb3RzOm50aC1jaGlsZCg1KSB7CiAgICAgICAgICBhbmltYXRpb24tZGVsYXk6IDAuNnM7CiAgICAgICAgfQoKICAgICAgICBAa2V5ZnJhbWVzIGxvYWRlciB7CiAgICAgICAgICAwJSB7CiAgICAgICAgICAgIG9wYWNpdHk6IDA7CiAgICAgICAgICAgIHRyYW5zZm9ybTogc2NhbGUoMSk7CiAgICAgICAgICB9CiAgICAgICAgICA0NSUgewogICAgICAgICAgICBvcGFjaXR5OiAxOwogICAgICAgICAgICB0cmFuc2Zvcm06IHNjYWxlKDAuNyk7CiAgICAgICAgICB9CiAgICAgICAgICA2NSUgewogICAgICAgICAgICBvcGFjaXR5OiAxOwogICAgICAgICAgICB0cmFuc2Zvcm06IHNjYWxlKDAuNyk7CiAgICAgICAgICB9CiAgICAgICAgICAxMDAlIHsKICAgICAgICAgICAgb3BhY2l0eTogMDsKICAgICAgICAgICAgdHJhbnNmb3JtOiBzY2FsZSgxKTsKICAgICAgICAgIH0KICAgICAgICB9CiAgICAgIDwvc3R5bGU+CjxnIGNsYXNzPSJjb250YWluZXIiPgo8Y2lyY2xlIGNsYXNzPSJkb3RzIiBjeD0iMzB2dyIvPgo8Y2lyY2xlIGNsYXNzPSJkb3RzIiBjeD0iNDB2dyIvPgo8Y2lyY2xlIGNsYXNzPSJkb3RzIiBjeD0iNTB2dyIvPgo8Y2lyY2xlIGNsYXNzPSJkb3RzIiBjeD0iNjB2dyIvPgo8Y2lyY2xlIGNsYXNzPSJkb3RzIiBjeD0iNzB2dyIvPgo8L2c+Cjwvc3ZnPg==" alt="Stape Community" />
      </div>

      {/* Main content */}
      <div id="main">
        {/* Header Section */}
        <header className="bg-light py-2 w-100">
          <div className="container-fluid">
            <nav className="navbar navbar-expand-lg navbar-light">
              <span className="navbar-toggler-icon" onClick={() => window.openNav()}></span>
              <a className="navbar-brand me-auto" href="./homepage.html">
                <img src="/assets/img/bookitlogo.png" alt="Bookit" height="40px" />
              </a>
              <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span className="navbar-toggler-icon"></span>
              </button>
              <div className="collapse navbar-collapse" id="navbarNav">
                <ul className="navbar-nav ms-auto">
                  <li className="nav-item"><a className="nav-link" href="homepage.html">Main Page</a></li>
                  <li className="nav-item"><a className="nav-link" href="homepage.html#highlights">Highlights</a></li>
                  <li className="nav-item"><a className="nav-link" href="homepage.html#working">See Working</a></li>
                  <li className="nav-item"><a className="nav-link" href="homepage.html#features">Features</a></li>
                  <li className="nav-item"><a className="btn btn-primary" href="homepage.html" onClick={() => window.location.href = 'homepage.html'}>Logout</a></li>
                </ul>
              </div>
            </nav>
          </div>
        </header>

        {/* Admin Dashboard Layout */}
        <div className="d-flex">
          {/* Sidebar */}
          <div id="sidebar" className="bg-dark text-light p-3">
            <h4>Admin Dashboard</h4>
            <ul className="nav flex-column">
              <li className="nav-item">
                <button className="nav-link text-light" onClick={() => navigate('/dashboard')} data-toggle="tooltip" data-placement="right" title="Admin Dashboard">Dashboard</button>
              </li>
            </ul>
          </div>
          {/* Main Content Area */}
          <div id="content" className="p-4 min-vh-100">
            <div className="container">
              <div className="mb-4">
                <h3>Add an amenity</h3>

                {/* select amenity to remove */}
                <div className="mb-3">
                  <label htmlFor="selectRoom" className="form-label">Add an Amenity</label>
                  <div className="input-group mb-3">
                    <input type="text" className="form-control" placeholder="Enter Amenity Name" disabled />
                    <input type="text" defaultValue="" className="form-control" placeholder="Enter Amenity Name" required />
                  </div>
                  <div className="input-group mb-3">
                    <input type="text" className="form-control" placeholder="Enter Credits" disabled />
                    <input type="number" defaultValue="0" min="0" className="form-control" placeholder="Enter Credits" required />
                  </div>
                </div>

                <button className="btn btn-primary" onClick={() => window.addAmenity()} data-toggle="modal" data-target="#exampleModal">Add Amenity</button>
              </div>
            </div>
          </div>
        </div>
      </div>

      {/* Footer Section */}
      <footer className="bg-dark text-light pt-2 position-fixed fixed-bottom">
        <div className="container text-center text-md-left">
          {/* Legal Links and Social Media Icons */}
          <div className="row align-items-center">
            {/* Legal Links */}
            <div className="col-md-7 col-lg-8">
              <p className="text-center text-md-left">© 2024 Bookit. All rights reserved.
                <a href="#" className="text-light" style={{textDecoration:'none'}}>Terms & Conditions</a> |
                <a href="#" className="text-light" style={{textDecoration:'none'}}>Privacy Policy</a> |
                <a href="#" className="text-light" style={{textDecoration:'none'}}>Manage Cookies</a>
              </p>
            </div>

            {/* Social Media Icons */}
            <div className="col-md-5 col-lg-4">
              <div className="text-center text-md-right">
                <ul className="list-unstyled list-inline">
                  <li className="list-inline-item">
                    <a href="#" className="btn btn-outline-light bg-light btn-floating btn-sm">
                      <img src="/assets/img/fontawesome-free-6.6.0-web/svgs/brands/facebook-f.svg" alt="facebook" className="m-1" width="15px" height="20px" />
                    </a>
                  </li>
                  <li className="list-inline-item">
                    <a href="#" className="btn btn-outline-light bg-light btn-floating btn-sm">
                      <img src="/assets/img/fontawesome-free-6.6.0-web/svgs/brands/twitter.svg" alt="twitter" className="m-1" width="15px" height="20px" />
                    </a>
                  </li>
                  <li className="list-inline-item">
                    <a href="#" className="btn btn-outline-light bg-light btn-floating btn-sm">
                      <img src="/assets/img/fontawesome-free-6.6.0-web/svgs/brands/linkedin.svg" alt="linkedin" className="m-1" width="15px" height="20px" />
                    </a>
                  </li>
                  <li className="list-inline-item">
                    <a href="#" className="btn btn-outline-light bg-light btn-floating btn-sm">
                      <img src="/assets/img/fontawesome-free-6.6.0-web/svgs/brands/instagram.svg" alt="instagram" className="m-1" width="15px" height="20px" />
                    </a>
                  </li>
                </ul>
              </div>
            </div>
          </div>
        </div>
      </footer>
    </>
  );
}

export default AddAmenity;