// App.js
import React from 'react';
import { Route, Routes, useLocation } from 'react-router-dom';
import './App.css';
import Book from './Pages/Book';
import SignUp from './Pages/SignUp';
import Login from './Pages/Login';
import CartServ from './Pages/CartServ';
import HomePage from './Pages/HomePage';
import AboutUs from './Pages/AboutUs';
import Header from './components/Header';
import AddBook from './Pages/AddBook';
import UpdateBook from './Pages/UpdateBook';
import DeleteBook from './Pages/DeleteBook';
import AllOrders from './Pages/AllOrders';
import CheckOut from './Pages/CheckOut';
import OrderHistoryOfUser from './Pages/OrderHistoryOfUser';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

function App() {
  const location = useLocation();
  const noHeaderRoutes = ['/login', '/signup', '/'];

  return (
    <div align="center">
      {!noHeaderRoutes.includes(location.pathname) && <Header />}
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/" element={<Login />} />
        <Route path="/signup" element={<SignUp />} />
        <Route path="/home" element={<HomePage />} />
        <Route path="/book" element={<Book />} />
        <Route path="/cart" element={<CartServ />} />
        <Route path="/aboutus" element={<AboutUs />} />
        <Route path="/addbook" element={<AddBook />} />
        <Route path="/updatebook" element={<UpdateBook />} />
        <Route path="/deletebook" element={<DeleteBook />} />
        <Route path="/allorders" element={<AllOrders />} />
        <Route path="/checkout" element={<CheckOut />} />
        <Route path="/orderhistoryofuser" element={<OrderHistoryOfUser />} />
      </Routes>
      <ToastContainer />
    </div>
  );
}

export default App;