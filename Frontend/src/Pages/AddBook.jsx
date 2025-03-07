import React, { useState } from 'react';
import axiosInstance from '../axiosInstance';
import 'bootstrap/dist/css/bootstrap.min.css';
import './BookCrud.css'; // Import the common CSS file

export default function AddBook() {
  const [title, setTitle] = useState('');
  const [author, setAuthor] = useState('');
  const [category, setCategory] = useState('');
  const [price, setPrice] = useState('');
  const [quantity, setQuantity] = useState('');
  const [userDetails, setUserDetails] = useState(null);

  const bookData = {
    title: title,
    author: author,
    category: category,
    price: price,
    quantity: quantity
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axiosInstance.post('http://localhost:8011/books/add', bookData);
      console.log("Book added successfully:", response.data);
      setUserDetails(response.data);
      // Optionally, you can reset the form fields here
      setTitle('');
      setAuthor('');
      setCategory('');
      setPrice('');
      setQuantity('');
    } catch (error) {
      console.error('There was an error adding the book!', error);
    }
  };

  return (
    <div className="container mt-5 book-crud-page">
      <div className="row justify-content-center">
        <div className="col-md-6">
          <div className="card shadow-lg border-primary book-crud-card">
            <div className="card-body">
              <h1 className="card-title text-center book-crud-title">Add Book</h1>
              <form onSubmit={handleSubmit}>
                <div className="mb-3">
                  <input
                    type="text"
                    className="form-control border-primary book-crud-input"
                    id="title"
                    name="title"
                    placeholder="Title"
                    value={title}
                    onChange={(e) => setTitle(e.target.value)}
                    required
                  />
                </div>
                <div className="mb-3">
                  <input
                    type="text"
                    className="form-control border-primary book-crud-input"
                    id="author"
                    name="author"
                    placeholder="Author"
                    value={author}
                    onChange={(e) => setAuthor(e.target.value)}
                    required
                  />
                </div>
                <div className="mb-3">
                  <input
                    type="text"
                    className="form-control border-primary book-crud-input"
                    id="category"
                    name="category"
                    placeholder="Category"
                    value={category}
                    onChange={(e) => setCategory(e.target.value)}
                    required
                  />
                </div>
                <div className="mb-3">
                  <input
                    type="number"
                    className="form-control border-primary book-crud-input"
                    id="price"
                    name="price"
                    placeholder="Price"
                    value={price}
                    onChange={(e) => setPrice(e.target.value)}
                    required
                  />
                </div>
                <div className="mb-3">
                  <input
                    type="number"
                    className="form-control border-primary book-crud-input"
                    id="quantity"
                    name="quantity"
                    placeholder="Quantity"
                    value={quantity}
                    onChange={(e) => setQuantity(e.target.value)}
                    required
                  />
                </div>
                <button type="submit" className="btn btn-primary w-100 book-crud-button">Add Book</button>
              </form>
            </div>
            {userDetails && (
            <div className="user-details mt-3">
              <h3>Book Details</h3>
              <p><strong>Title:</strong> {userDetails.title}</p>
              <p><strong>Author:</strong> {userDetails.author}</p>
              <p><strong>Category:</strong> {userDetails.category}</p>
              <p><strong>Price:</strong> {userDetails.price}</p>
              <p><strong>Quantity:</strong> {userDetails.quantity}</p>
            </div>
          )}
          </div>
        </div>
      </div>
    </div>
  );
}