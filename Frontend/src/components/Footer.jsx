import React from 'react';
import '@fortawesome/fontawesome-free/css/all.min.css';

export default function Footer() {
  return (
    <div className="footer text-center py-3" style={{ backgroundColor: '#EEE2DE', color: '#2B2A4C' }}>
      <a href="https://github.com/NITHYARUBANN/BookPurchaseApplication" target="_blank" rel="noopener noreferrer" style={{ color: '#B31312' }}>
        <i className="fab fa-github fa-2x"></i>
      </a>
    </div>
  );
}