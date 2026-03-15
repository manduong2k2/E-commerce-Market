// services/authService.js
import { AUTH_API_URL } from '../configs/constants';

// helper chung
async function request(path, options = {}) {
  const { method = 'GET', body } = options;
  const res = await fetch(`${AUTH_API_URL}${path}`, {
    method,
    headers: { 'Content-Type': 'application/json' },
    credentials: 'include', // cookie HttpOnly tự gửi
    body: body ? JSON.stringify(body) : undefined,
  });
  
  // auto parse text nếu response không phải json
  const contentType = res.headers.get('Content-Type') || '';
  if (contentType.includes('application/json')) {
    return res.json();
  } else {
    return res.text();
  }
}

// ===== Auth APIs =====
export const authService = {
  login: (data) => request('/auth/login', { method: 'POST', body: data }),
  register: (data) => request('/auth/register', { method: 'POST', body: data }),
  refreshToken: (data) => request('/auth/refresh-token', { method: 'POST', body: data }),
  verifyEmail: (email, token) =>
    request(`/auth/verify-email?email=${encodeURIComponent(email)}&token=${encodeURIComponent(token)}`),
  forgotPassword: (email) => request('/auth/forgot-password', { method: 'POST', body: { email } }),
  resetPassword: (email, token, newPassword) =>
    request('/auth/reset-password', { method: 'POST', body: { email, token, newPassword } }),
  profile: () => request('/auth/profile'),
  logout: () => request('/auth/logout', { method: 'POST' }),
};