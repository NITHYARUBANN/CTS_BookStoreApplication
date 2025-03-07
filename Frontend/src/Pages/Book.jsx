import React, { useState, useEffect } from 'react';
import axiosInstance from '../axiosInstance';
import 'bootstrap/dist/css/bootstrap.min.css';
import './Book.css';
import Footer from '../components/Footer';

export default function Book() {
  const [books, setBooks] = useState([]);
  const [quantities, setQuantities] = useState({});
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [searchQuery, setSearchQuery] = useState('');
  const [searchType, setSearchType] = useState('title');

  useEffect(() => {
    const fetchBooks = async () => {
      try {
        const response = await axiosInstance.get('http://localhost:8011/books/getAll');
        console.log("Books are displaying.", response.data);
        setBooks(response.data);
      } catch (error) {
        console.error('There was an error fetching the books!', error);
      }
    };

    fetchBooks();
  }, []);

  const handleQuantityChange = (bookId, value) => {
    setQuantities({
      ...quantities,
      [bookId]: value,
    });
  };

  const handleAddToCart = async (book) => {
    const userId = localStorage.getItem('userId');
    console.log('Retrieved userId:', userId);
    if (!userId) {
      setError('User ID not found. Please log in.');
      return;
    }
    const quantity = quantities[book.id] || 1;
    try {
      const response = await axiosInstance.post(`http://localhost:8011/carts/add/${userId}`, null, {
        params: {
          bookId: book.id,
          quantity: quantity,
        }
      });
      console.log(`Added ${book.title} to cart`, response.data);
      setSuccess(`Added ${book.title} to cart`);
      setError('');
    } catch (error) {
      console.error('There was an error adding the book to the cart!', error);
      if (error.response && error.response.data && error.response.data.message) {
        setError(error.response.data.message);
      } else {
        setError('Failed to add book to cart. Please try again.');
      }
      setSuccess('');
    }
  };

  const handleSearch = async () => {
    if (!searchQuery.trim()) {
      setError('Search query cannot be empty.');
      return;
    }

    try {
      let response;
      if (searchType === 'title') {
        response = await axiosInstance.get(`http://localhost:8011/books/searchByTitle?title=${searchQuery}`);
      } else if (searchType === 'author') {
        response = await axiosInstance.get(`http://localhost:8011/books/searchByAuthor?author=${searchQuery}`);
      } else if (searchType === 'category') {
        response = await axiosInstance.get(`http://localhost:8011/books/search?category=${searchQuery}`);
      }
      setBooks(response.data);
      setError('');
    } catch (error) {
      console.error('There was an error searching the books!', error);
      if (error.response) {
        setError(`Error: ${error.response.status} - ${error.response.data.message}`);
      } else {
        setError('Failed to search books. Please try again.');
      }
    }
  };

  return (
    <div className="book-page">
      <div className="container mt-5">
        <div className="row justify-content-center mb-3">
          <div className="col-md-10">
            <div className="input-group">
              <input
                type="text"
                className="form-control search-input"
                placeholder="Search for books..."
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
              />
              <select
                className="form-control search-select"
                value={searchType}
                onChange={(e) => setSearchType(e.target.value)}
                style={{ color: '#2B2A4C', transition: 'background-color 0.3s, color 0.3s' }}
                onMouseEnter={(e) => { e.target.style.color = '#2B2A4C'; e.target.style.backgroundColor = '#EEE2DE'; }}
                onMouseLeave={(e) => { e.target.style.color = '#2B2A4C'; e.target.style.backgroundColor = '#EEE2DE'; }}
              >
                <option value="title">Title</option>
                <option value="author">Author</option>
                <option value="category">Category</option>
              </select>
              <div className="input-group-append">
                <button className="btn btn-primary search-button" onClick={handleSearch}>Search</button>
              </div>
            </div>
          </div>
        </div>
        <h1 className="text-center book-page-title">Book Page</h1>
        <div className="row">
          {error && <p className="text-center error-message">{error}</p>}
          {success && <p className="text-center success-message">{success}</p>}
          {books.length > 0 ? (
            books.map((book) => (
              <div className="col-md-4 mb-4" key={book.id}>
                <div className="card h-100 book-card">
                  <div className="card-body d-flex flex-column">
                    <h5 className="card-title book-title">{book.title}</h5>
                    <h6 className="card-subtitle mb-2 text-muted">{book.author}</h6>
                    <p className="card-text">{book.category}</p>
                    <p className="card-text">₹{book.price}</p>
                    <div className="mt-auto">
                      <div className="input-group mb-2 quantity-input-group">
                        <span className="input-group-text">Quantity</span>
                        <input
                          type="number"
                          min="1"
                          className="form-control quantity-input"
                          value={quantities[book.id] || 1}
                          onChange={(e) => handleQuantityChange(book.id, e.target.value)}
                        />
                      </div>
                      <button
                        className="btn btn-primary btn-block add-to-cart-button"
                        onClick={() => handleAddToCart(book)}
                      >
                        Add to Cart
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            ))
          ) : (
            <p className="text-center">No books available.</p>
          )}
        </div>
      </div>
      <Footer />
    </div>
  );
}