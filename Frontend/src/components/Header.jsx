import React from 'react';
import { Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCartShopping, faBook } from '@fortawesome/free-solid-svg-icons';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';

export default function Header() {
  // Retrieve roles from local storage
  const roles = localStorage.getItem('roles');

  return (
    <nav className="navbar navbar-expand-sm" style={{ backgroundColor: '#EEE2DE' }}>
      <div className="container-fluid">
        <ul className="navbar-nav">
          <li className="nav-item">
            <Link className="nav-link" to="/home" style={{ color: '#2B2A4C' }}>
              Paperback Paradise&nbsp;&nbsp;
              <FontAwesomeIcon icon={faBook} />
            </Link>
          </li>
          <li className="nav-item">
            <Link className="nav-link" to="/book" style={{ color: '#2B2A4C' }}>Books</Link>
          </li>
          <li className="nav-item">
            <Link className="nav-link" to="/orderhistoryofuser" style={{ color: '#2B2A4C' }}>Order History</Link>
          </li>
          <li className="nav-item">
            <Link className="nav-link" to="/aboutus" style={{ color: '#2B2A4C' }}>About Us</Link>
          </li>
          {roles === 'ROLE_ADMIN' && (
            <li className="nav-item dropdown">
              <Link className="nav-link dropdown-toggle" to="#" id="adminDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false" style={{ color: '#2B2A4C' }}>
                Admin Page
              </Link>
              <ul className="dropdown-menu" aria-labelledby="adminDropdown" style={{ backgroundColor: '#EEE2DE' }}>
                <li>
                  <Link className="dropdown-item" to="/addbook" style={{ color: '#2B2A4C', transition: 'background-color 0.3s, color 0.3s' }} onMouseEnter={(e) => { e.target.style.color = '#ffffff'; e.target.style.backgroundColor = '#2B2A4C'; }} onMouseLeave={(e) => { e.target.style.color = '#2B2A4C'; e.target.style.backgroundColor = 'transparent'; }}>Add Book</Link>
                </li>
                <li>
                  <Link className="dropdown-item" to="/updatebook" style={{ color: '#2B2A4C', transition: 'background-color 0.3s, color 0.3s' }} onMouseEnter={(e) => { e.target.style.color = '#ffffff'; e.target.style.backgroundColor = '#2B2A4C'; }} onMouseLeave={(e) => { e.target.style.color = '#2B2A4C'; e.target.style.backgroundColor = 'transparent'; }}>Update Book</Link>
                </li>
                <li>
                  <Link className="dropdown-item" to="/deletebook" style={{ color: '#2B2A4C', transition: 'background-color 0.3s, color 0.3s' }} onMouseEnter={(e) => { e.target.style.color = '#ffffff'; e.target.style.backgroundColor = '#2B2A4C'; }} onMouseLeave={(e) => { e.target.style.color = '#2B2A4C'; e.target.style.backgroundColor = 'transparent'; }}>Delete Book</Link>
                </li>
                <li>
                  <Link className="dropdown-item" to="/allorders" style={{ color: '#2B2A4C', transition: 'background-color 0.3s, color 0.3s' }} onMouseEnter={(e) => { e.target.style.color = '#ffffff'; e.target.style.backgroundColor = '#2B2A4C'; }} onMouseLeave={(e) => { e.target.style.color = '#2B2A4C'; e.target.style.backgroundColor = 'transparent'; }}>All Orders</Link>
                </li>
              </ul>
            </li>
          )}
        </ul>
        <ul className="navbar-nav ms-auto">
          <li className="nav-item dropdown">
            <Link className="nav-link dropdown-toggle" to="#" id="accountDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false" style={{ color: '#2B2A4C' }}>
              My Account
            </Link>
            <ul className="dropdown-menu" aria-labelledby="accountDropdown" style={{ backgroundColor: '#EEE2DE' }}>
              <li>
                <Link className="dropdown-item" to="/login" style={{ color: '#2B2A4C', transition: 'background-color 0.3s, color 0.3s' }} onMouseEnter={(e) => { e.target.style.color = '#ffffff'; e.target.style.backgroundColor = '#2B2A4C'; }} onMouseLeave={(e) => { e.target.style.color = '#2B2A4C'; e.target.style.backgroundColor = 'transparent'; }}>Logout</Link>
              </li>
            </ul>
          </li>
          <li className="nav-item">
            <Link className="nav-link" to="/cart" style={{ color: '#2B2A4C' }}>
              <FontAwesomeIcon icon={faCartShopping} />
            </Link>
          </li>
        </ul>
      </div>
    </nav>
  );
}