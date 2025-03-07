import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import Footer from '../components/Footer';

export default function AboutUs() {
  return (
    <div className="container mt-5">
      <div className="row justify-content-center">
        <div className="col-md-8">
          <div className="card shadow-lg" style={{ backgroundColor: '#EEE2DE' }}>
            <div className="card-body">
              <h1 className="card-title text-center" style={{ color: '#2B2A4C' }}>About Us</h1>
              <p className="card-text" style={{ color: '#2B2A4C' }}>
                Welcome to <strong>Paperback Paradise</strong>, your number one source for all kinds of books. We're dedicated to giving you the very best of literature, with a focus on quality, customer service, and uniqueness.
              </p>
              <p className="card-text" style={{ color: '#2B2A4C' }}>
                Founded in 2025, <strong>Paperback Paradise</strong> has come a long way from its beginnings. When we first started out, our passion for books drove us to do tons of research so that <strong>Paperback Paradise</strong> can offer you the world's most beloved and rare books. We now serve customers all over the world and are thrilled that we're able to turn our passion into our own website.
              </p>
              <p className="card-text" style={{ color: '#2B2A4C' }}>
                We hope you enjoy our products as much as we enjoy offering them to you. If you have any questions or comments, please don't hesitate to contact us.
              </p>
              <p className="card-text text-center" style={{ color: '#2B2A4C' }}>
                <strong>Sincerely,</strong><br />
                Paperback Paradise Team
              </p>
              <Footer />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}