// services/authService.js
import { GATEWAY_URL, AUTH_SERVICE_NAME } from '../configs/constants';

// helper chung
async function request(path, options = {}) {
  const { method = 'GET', body } = options;
  const res = await fetch(`${GATEWAY_URL}/${AUTH_SERVICE_NAME}${path}`, {
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
  login: (data) => request('/api/auth/login', { method: 'POST', body: data }),
  register: (data) => request('/api/auth/register', { method: 'POST', body: data }),
  refreshToken: (data) => request('/api/auth/refresh-token', { method: 'POST', body: data }),
  verifyEmail: (email, token) =>
    request(`/api/auth/verify-email?email=${encodeURIComponent(email)}&token=${encodeURIComponent(token)}`),
  forgotPassword: (email) => request('/api/auth/forgot-password', { method: 'POST', body: { email } }),
  resetPassword: (email, token, newPassword) =>
    request('/api/auth/reset-password', { method: 'POST', body: { email, token, newPassword } }),
  profile: () => request('/api/auth/profile'),
  logout: () => request('/api/auth/logout', { method: 'POST' }),
};