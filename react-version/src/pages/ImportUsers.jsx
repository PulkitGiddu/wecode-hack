import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import '../css/homepage.css';
import '../js/common.js';

function ImportUsers() {
  const navigate = useNavigate();
  useEffect(() => {
    if (window.feather) {
      window.feather.replace();
    }

    const csvForm = document.getElementById('csvForm');
    if (csvForm) {
      csvForm.addEventListener('submit', function(event) {
        event.preventDefault(); // Prevent the form from submitting

        const fileInput = document.getElementById('fileUpload');
        const file = fileInput.files[0];

        if (file) {
          const reader = new FileReader();
          reader.onload = function(e) {
            const csvData = e.target.result;
            const parsedData = parseCSV(csvData);
            displayCSVData(parsedData);
          };
          reader.readAsText(file);
        } else {
          alert('Please upload a CSV file.');
        }
      });
    }

    function parseCSV(csv) {
      const rows = csv.trim().split('\n').map(row => row.split(','));
      return rows;
    }

    function displayCSVData(data) {
      const tableHead = document.getElementById('csvTableHead');
      const tableBody = document.getElementById('csvTableBody');

      // Clear existing table content
      tableHead.innerHTML = '';
      tableBody.innerHTML = '';

      // Add table headers
      const headerRow = document.createElement('tr');
      data[0].forEach(header => {
        const th = document.createElement('th');
        th.textContent = header.trim();
        th.style.textAlign = 'center'; // Align headers to the center
        headerRow.appendChild(th);
      });
      tableHead.appendChild(headerRow);

      // Add table rows
      data.slice(1).forEach(row => {
        const tr = document.createElement('tr');
        row.forEach(cell => {
          const td = document.createElement('td');
          td.textContent = cell.trim();
          td.style.textAlign = 'center'; // Align cells to the center
          tr.appendChild(td);
        });
        tableBody.appendChild(tr);
      });
    }
  }, []);

  return (
    <>
      {/* Main content */}
      <div id="main">
        {/* Header Section */}
        <header className="bg-light py-2 w-100 position-fixed">
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
                  <li className="nav-item"><a className="nav-link" href="manager-dashboard.html">Manager Dashboard</a></li>
                  <li className="nav-item"><a className="nav-link" href="homepage.html#highlights">Highlights</a></li>
                  <li className="nav-item"><a className="nav-link" href="homepage.html#working">See Working</a></li>
                  <li className="nav-item"><a className="nav-link" href="homepage.html#features">Features</a></li>
                  <li className="nav-item"><a className="btn btn-primary" href="homepage.html" onClick={() => window.location.href = 'homepage.html'}>Logout</a></li>
                </ul>
              </div>
            </nav>
          </div>
        </header>
      </div>

      {/* Import Users Section */}
      <section className="import-users py-5">
        <div className="container">
          <h1 className="text-center mb-4"> Import Users</h1>
          <div className="row justify-content-center">
            <div className="col-md-8 col-lg-6">
              <form id="csvForm">
                <div className="mb-3">
                  <label htmlFor="fileUpload" className="form-label">Upload CSV File</label>
                  <input className="form-control" type="file" id="fileUpload" accept=".csv" />
                </div>
                <button type="submit" className="btn btn-primary w-100">Import</button>
              </form>
            </div>
          </div>
          <div className="row justify-content-center mt-4">
            <div className="col-md-10">
              <h3 className="text-center">CSV Data:</h3>
              <div className="table-responsive">
                <table className="table table-striped table-bordered table-hover">
                  <thead id="csvTableHead" className="thead-dark"></thead>
                  <tbody id="csvTableBody"></tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
      </section>

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
                      <img src="/assets/img/fontawesome-free-6.6.0-web/svgs/brands/facebook-f.svg" alt="facebook" className="m-1" width="20px" height="20px" />
                    </a>
                  </li>
                  <li className="list-inline-item">
                    <a href="#" className="btn btn-outline-light bg-light btn-floating btn-sm">
                      <img src="/assets/img/fontawesome-free-6.6.0-web/svgs/brands/twitter.svg" alt="twitter" className="m-1" width="20px" height="20px" />
                    </a>
                  </li>
                  <li className="list-inline-item">
                    <a href="#" className="btn btn-outline-light bg-light btn-floating btn-sm">
                      <img src="/assets/img/fontawesome-free-6.6.0-web/svgs/brands/linkedin.svg" alt="linkedin" className="m-1" width="20px" height="20px" />
                    </a>
                  </li>
                  <li className="list-inline-item">
                    <a href="#" className="btn btn-outline-light bg-light btn-floating btn-sm">
                      <img src="/assets/img/fontawesome-free-6.6.0-web/svgs/brands/instagram.svg" alt="instagram" className="m-1" width="20px" height="20px" />
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

export default ImportUsers;