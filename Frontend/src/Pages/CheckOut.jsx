import React, { useState, useEffect } from 'react';
import axiosInstance from '../axiosInstance';
import 'bootstrap/dist/css/bootstrap.min.css';

export default function CheckOut() {
  const [username, setUsername] = useState('');
  const [address, setAddress] = useState('');
  const [totalPrice, setTotalPrice] = useState(0);
  const [paymentMethod, setPaymentMethod] = useState('Cash on Delivery');
  const [orderDetails, setOrderDetails] = useState(null);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchTotalPrice = async () => {
      const userId = localStorage.getItem('userId'); // Retrieve userId from local storage
      if (!userId) {
        setError('User ID is not available.');
        return;
      }
      try {
        const response = await axiosInstance.get(`http://localhost:8011/carts/total/${userId}`);
        setTotalPrice(response.data);
      } catch (error) {
        console.error('There was an error fetching the total price!', error);
        setError('Failed to fetch total price. Please try again.');
      }
    };

    fetchTotalPrice();
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    const userId = localStorage.getItem('userId'); // Retrieve userId from local storage
    if (!userId) {
      setError('User ID is not available.');
      return;
    }
    try {
      const response = await axiosInstance.post(`http://localhost:8011/carts/checkout/${userId}`, null, {
        params: {
          username: username,
          address: address,
          paymentMethod: paymentMethod,
        }
      });
      setOrderDetails(response.data);
      setError('');
    } catch (error) {
      console.error('There was an error placing the order!', error);
      setError('Failed to place order. Please try again.');
    }
  };

  return (
    <div className="container mt-5">
      <div className="row justify-content-center">
        <div className="col-md-6">
          <div className="card shadow-lg border-primary">
            <div className="card-body">
              <h1 className="card-title text-center text-primary">Checkout</h1>
              <h4 className="text-center">Total Price: ₹{totalPrice}</h4>
              <form onSubmit={handleSubmit}>
                <div className="mb-3">
                  <label htmlFor="username" className="form-label">Receiver Name:</label>
                  <input
                    type="text"
                    className="form-control border-primary"
                    id="username"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    required
                  />
                </div>
                <div className="mb-3">
                  <label htmlFor="address" className="form-label">Address:</label>
                  <input
                    type="text"
                    className="form-control border-primary"
                    id="address"
                    value={address}
                    onChange={(e) => setAddress(e.target.value)}
                    required
                  />
                </div>
                <div className="mb-3">
                  <label htmlFor="paymentMethod" className="form-label">Payment Method:</label>
                  <select
                    className="form-control border-primary"
                    id="paymentMethod"
                    value={paymentMethod}
                    onChange={(e) => setPaymentMethod(e.target.value)}
                    required
                  >
                    <option value="Cash on Delivery">Cash on Delivery</option>
                  </select>
                </div>
                <button type="submit" className="btn btn-primary w-100">Place Order</button>
              </form>
              {error && <p className="text-danger mt-3">{error}</p>}
            </div>
          </div>
        </div>
      </div>
      {orderDetails && (
        <div className="row justify-content-center mt-5">
          <div className="col-md-6">
            <div className="card shadow-lg border-primary">
              <div className="card-body">
                <h2 className="card-title text-center text-primary">Order Summary</h2>
                <p><strong>Receiver's Name:</strong> {orderDetails.username}</p>
                <p><strong>Order Details:</strong> {orderDetails.orderDetails}</p>
                <p><strong>Total Price:</strong> ₹{orderDetails.totalCost}</p>
                <p><strong>Receiver's Address:</strong> {orderDetails.address}</p>
                <p><strong>Payment Method:</strong> {orderDetails.paymentMethod}</p>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}