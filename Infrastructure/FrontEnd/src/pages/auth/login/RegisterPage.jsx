// src/pages/auth/RegisterPage.jsx
import React, { useState } from 'react';
import { authService } from '../../../services/authService';

export default function RegisterPage() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const res = await authService.register({ email, password });
      console.log(res);
      alert('Đăng ký thành công!');
    } catch (err) {
      console.error(err);
      alert('Đăng ký thất bại!');
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <input type="email" value={email} onChange={e => setEmail(e.target.value)} required />
      <input type="password" value={password} onChange={e => setPassword(e.target.value)} required />
      <button type="submit">Register</button>
    </form>
  );
}