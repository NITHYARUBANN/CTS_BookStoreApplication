import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { toast, ToastContainer } from 'react-toastify';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'react-toastify/dist/ReactToastify.css';

export default function Login() {
  const navigate = useNavigate();
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const userdata = {
    username: username,
    password: password
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post('http://localhost:8011/auth/authenticate', userdata);
      console.log(response);
      localStorage.setItem('jwtToken', response.data.jwtToken);
      localStorage.setItem('userId', response.data.id);
      localStorage.setItem('roles', response.data.roles);
      toast.success("Login successful!");
      navigate('/home');
    } catch (error) {
      toast.error("Invalid Credentials");
    }
  };

  return (
    <div className="container mt-5">
      <div className="row justify-content-center">
        <div className="col-md-6">
          <div className="card shadow-lg" style={{ borderColor: '#B31312' }}>
            <div className="card-body" style={{ backgroundColor: '#EEE2DE' }}>
              <h1 className="card-title text-center" style={{ color: '#2B2A4C' }}>Login</h1>
              <form onSubmit={handleSubmit}>
                <div className="mb-3">
                  <input
                    type="text"
                    className="form-control"
                    id="username"
                    name="username"
                    placeholder="Username"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    required
                    style={{ borderColor: '#B31312', color: '#2B2A4C' }}
                  />
                </div>
                <div className="mb-3">
                  <input
                    type="password"
                    className="form-control"
                    id="password"
                    name="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                    style={{ borderColor: '#B31312', color: '#2B2A4C' }}
                  />
                </div>
                <div className="mb-3 text-end">
                  <Link to="/signup" style={{ color: '#B31312' }}>Don't have an account? Sign Up</Link>
                </div>
                <button type="submit" className="btn w-100" style={{ backgroundColor: '#B31312', color: '#FFF' }}>Login</button>
              </form>
            </div>
          </div>
        </div>
      </div>
      <ToastContainer />
    </div>
  );
}