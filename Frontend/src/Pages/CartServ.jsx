import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axiosInstance from '../axiosInstance';
import 'bootstrap/dist/css/bootstrap.min.css';

export default function CartServ() {
  const [cartItems, setCartItems] = useState([]);
  const [totalPrice, setTotalPrice] = useState(0);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    const fetchCartItems = async () => {
      const userId = localStorage.getItem('userId'); // Get userId from local storage
      console.log('Retrieved userId from local storage:', userId);
      if (!userId) {
        setError('User ID is not available.');
        return;
      }
      try {
        const response = await axiosInstance.get(`http://localhost:8011/carts/getAll/${userId}`);
        setCartItems(response.data);
        setError('');

        // Calculate total price
        const total = response.data.reduce((sum, item) => sum + (item.price * item.quantity), 0);
        setTotalPrice(total);
      } catch (error) {
        console.error('There was an error fetching the cart items!', error);
        setError('Failed to fetch cart items. Please try again.');
      }
    };

    fetchCartItems();
  }, []);

  const handleDelete = async (itemId) => {
    const userId = localStorage.getItem('userId'); // Get userId from local storage
    try {
      await axiosInstance.delete(`http://localhost:8011/carts/delete/${userId}/${itemId}`);
      setCartItems(cartItems.filter(item => item.id !== itemId));
      setError('');

      // Recalculate total price
      const total = cartItems.filter(item => item.id !== itemId).reduce((sum, item) => sum + (item.price * item.quantity), 0);
      setTotalPrice(total);
    } catch (error) {
      console.error('There was an error deleting the cart item!', error);
      setError('Failed to delete cart item. Please try again.');
    }
  };

  const handleCheckout = () => {
    navigate('/checkout');
  };

  return (
    <div className="container mt-5">
      <h1 className="text-center">Your Cart</h1>
      {error && <p className="text-danger text-center">{error}</p>}
      <div className="text-end mb-3">
        <h4>Total Price: ₹{totalPrice}</h4>
      </div>
      {cartItems.length > 0 ? (
        <ul className="list-group">
          {cartItems.map((item) => (
            <li className="list-group-item d-flex justify-content-between align-items-center border border-dark mb-2" key={item.id}>
              <div>
                <h5>{item.name}</h5>
                <p>Quantity: {item.quantity}</p>
                <p>Price: ₹{item.price * item.quantity}</p>
              </div>
              <button
                className="btn btn-danger"
                onClick={() => handleDelete(item.id)}
              >
                Delete
              </button>
            </li>
          ))}
        </ul>
      ) : (
        <p className="text-center">No items in the cart for this user.</p>
      )}
      {cartItems.length > 0 && (
        <div className="d-flex justify-content-end mt-3">
          <button
            className="btn btn-primary"
            onClick={handleCheckout}
          >
            Checkout
          </button>
        </div>
      )}
    </div>
  );
}