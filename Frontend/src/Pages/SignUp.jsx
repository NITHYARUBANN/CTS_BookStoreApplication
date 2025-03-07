import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';

export default function SignUp() {
  const navigate = useNavigate();
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [role, setRole] = useState(''); 

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    if (password.length < 6) {
      alert("Password must be at least 6 characters long.");
      return;
    }

    const userdata = {
      name: username,
      email: email,
      password: password,
      roles: role
    };

    try {
      const response = await axios.post('http://localhost:8011/auth/new', userdata);
      console.log("User registered successfully:", response.data);
      navigate('/login');
    } catch (error) {
      console.error('There was an error signing up!', error);
    }
  };

  return (
    <div className="container mt-5">
      <div className="row justify-content-center">
        <div className="col-md-6">
          <div className="card shadow-lg" style={{ borderColor: '#B31312' }}>
            <div className="card-body" style={{ backgroundColor: '#EEE2DE' }}>
              <h1 className="card-title text-center" style={{ color: '#2B2A4C' }}>Sign Up</h1>
              <form onSubmit={handleSubmit}>
                <div className="mb-3">
                  <input
                    type="text"
                    className="form-control"
                    id="name"
                    name="name"
                    placeholder="Name"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    required
                    style={{ borderColor: '#B31312', color: '#2B2A4C' }}
                  />
                </div>
                <div className="mb-3">
                  <input
                    type="email"
                    className="form-control"
                    id="email"
                    name="email"
                    placeholder="Email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
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
                <div className="mb-3">
                  <select
                    className="form-control"
                    id="role"
                    name="role"
                    value={role}
                    onChange={(e) => setRole(e.target.value)}
                    required
                    style={{ borderColor: '#B31312', color: '#2B2A4C' }}
                  >
                    <option value="">Select the Role</option>
                    <option value="ROLE_ADMIN">Admin</option>
                    <option value="ROLE_CUSTOMER">Customer</option>
                  </select>
                </div>
                <div className="mb-3 text-end">
                  <Link to="/login" style={{ color: '#B31312' }}>Already have an account? Login</Link>
                </div>
                <button type="submit" className="btn w-100" style={{ backgroundColor: '#B31312', color: '#FFF' }}>Sign Up</button>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}