import React, { useState, useEffect } from 'react';
import axiosInstance from '../axiosInstance';

export default function AllOrders() {
  const [orders, setOrders] = useState([]);

  useEffect(() => {
    const fetchOrders = async () => {
      try {
        const response = await axiosInstance.get('http://localhost:8011/carts/allOrders');
        console.log("All orders are displaying.", response.data);
        setOrders(response.data);
      } catch (error) {
        console.error('There was an error fetching the orders!', error);
      }
    };

    fetchOrders();
  }, []);

  return (
    <div className="container mt-5">
      <h1 className="text-center" style={{ color: '#EEE2DE' }}>All Orders</h1>
      <div className="row justify-content-center">
        <div className="col-md-10">
          <div className="card shadow-lg" style={{ borderColor: '#B31312' }}>
            <div className="card-body" style={{ backgroundColor: '#EEE2DE' }}>
              {orders.length > 0 ? (
                <table className="table table-striped table-bordered" style={{ backgroundColor: '#FFF', color: '#2B2A4C' }}>
                  <thead style={{ backgroundColor: '#B31312', color: '#FFF' }}>
                    <tr>
                      <th>Order ID</th>
                      <th>Customer</th>
                      <th>Total Price</th>
                      <th>Items</th>
                      <th>Address</th>
                    </tr>
                  </thead>
                  <tbody>
                    {orders.map((order) => (
                      <tr key={order.orderId}>
                        <td>{order.orderId}</td>
                        <td>{order.username}</td>
                        <td>₹{order.totalCost}</td>
                        <td>{order.orderDetails}</td>
                        <td>{order.address}</td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              ) : (
                <div className="alert alert-warning text-center">No orders found.</div>
              )}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}