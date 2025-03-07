import React from 'react';
import Footer from '../components/Footer';
import 'bootstrap/dist/css/bootstrap.min.css';

export default function HomePage() {
  return (
    <div className="container mt-5">
      <div className="jumbotron" style={{ backgroundColor: '#EEE2DE', color: '#2B2A4C' }}>
        <h1 className="display-4">Welcome to Paperback Paradise BookStore!</h1>
        <p className="lead">Discover your next great read.</p>
        <hr className="my-4" style={{ borderColor: '#B31312' }} />
        <p>Explore our collection of books across various categories.</p>
        <a className="btn btn-primary btn-lg" href="/book" role="button" style={{ backgroundColor: '#B31312', borderColor: '#B31312' }}>Browse Books</a>
      </div>
      <Footer />
    </div>
  );
}