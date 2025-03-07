import axios from 'axios';

const axiosInstance = axios.create({
  baseURL: 'http://localhost:1223',
});

axiosInstance.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('jwtToken');
    const userId = localStorage.getItem('userId'); 
    console.log(token);
    console.log(userId);
    if (token && !config.url.includes('auth/authenticate') && !config.url.includes('auth/register')) {
      config.headers['Authorization'] = `Bearer ${token}`;
    }
    if (userId) {
      config.headers['userId'] = userId;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default axiosInstance;