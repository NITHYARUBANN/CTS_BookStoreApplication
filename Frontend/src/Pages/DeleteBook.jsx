import React, { useState } from 'react';
import axiosInstance from '../axiosInstance';
import 'bootstrap/dist/css/bootstrap.min.css';
import './BookCrud.css'; // Import the common CSS file

export default function DeleteBook() {
  const [id, setId] = useState('');
  const [message, setMessage] = useState('');
  const [error, setError] = useState('');

  const handleDelete = async (e) => {
    e.preventDefault();
    try {
      const response = await axiosInstance.delete(`http://localhost:8011/books/delete/${id}`);
      console.log("Book deleted successfully:", response.data);
      setMessage('Book deleted successfully');
      setError('');
      setId('');
    } catch (error) {
      console.error('There was an error deleting the book!', error);
      if (error.response && error.response.data && error.response.data.message) {
        setError(error.response.data.message);
      } else {
        setError('Failed to delete book. Please try again.');
      }
      setMessage('');
    }
  };

  return (
    <div className="container mt-5 book-crud-page">
      <div className="row justify-content-center">
        <div className="col-md-6">
          <div className="card shadow-lg border-primary book-crud-card">
            <div className="card-body">
              <h1 className="card-title text-center book-crud-title">Delete Book</h1>
              {message && <p className="text-center success-message">{message}</p>}
              {error && <p className="text-center error-message">{error}</p>}
              <form onSubmit={handleDelete}>
                <div className="mb-3">
                  <input
                    type="text"
                    className="form-control border-primary book-crud-input"
                    id="id"
                    name="id"
                    placeholder="Book ID"
                    value={id}
                    onChange={(e) => setId(e.target.value)}
                    required
                  />
                </div>
                <button type="submit" className="btn btn-primary w-100 book-crud-button">Delete Book</button>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}