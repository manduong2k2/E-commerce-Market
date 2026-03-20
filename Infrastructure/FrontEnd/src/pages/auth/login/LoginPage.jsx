import { Link, useNavigate } from 'react-router-dom';
import React, { useState, useContext } from 'react';
import { authService } from '../../../services/authService';
import { AuthContext } from '../../../contexts/AuthContext';
import './LoginPage.css';

export default function LoginPage() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const { setUser } = useContext(AuthContext); // lấy setUser từ context
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    var form = document.getElementById('login-form');
    try {
      var response = await authService.login({ email, password });
      if (response.success) {
        const user = await authService.profile();
        setUser(user);
        navigate('/home');
      } else {
        alert('Login failed!');
      }
    } catch (err) {
      console.error(err);
      alert('Login failed!');
    }
  };

  return (
    <form id="login-form" onSubmit={handleSubmit} className="login-form">
      <h2>Đăng nhập</h2>
      <input
        type="email"
        placeholder="Email"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
        required
      />
      <input
        type="password"
        placeholder="Mật khẩu"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
        required
      />
      <button type="submit">Login</button>
      <div className="forgot-links">
        <Link to="/forgot">Quên mật khẩu?</Link>
      </div>
    </form>
  );
}