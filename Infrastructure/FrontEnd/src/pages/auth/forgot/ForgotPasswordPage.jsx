// src/pages/auth/forgot/ForgotPasswordPage.jsx
import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { authService } from '../../../services/authService';
import './ForgotPasswordPage.css';

export default function ForgotPasswordPage() {
  const [email, setEmail] = useState('');
  const [errors, setErrors] = useState({});
  const [loading, setLoading] = useState(false);
  const [submitted, setSubmitted] = useState(false);

  const handleChange = (e) => {
    const value = e.target.value;
    setEmail(value);
    
    // Clear error when user starts typing
    if (errors.email) {
      setErrors(prev => ({
        ...prev,
        email: ''
      }));
    }
  };

  const validateForm = () => {
    const newErrors = {};

    if (!email.trim()) {
      newErrors.email = 'Vui lòng nhập email';
    } else if (!/\S+@\S+\.\S+/.test(email)) {
      newErrors.email = 'Email không hợp lệ';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    if (!validateForm()) {
      return;
    }

    setLoading(true);
    try {
      const response = await authService.forgotPassword(email);
      
      if (response.success || response.message) {
        setSubmitted(true);
      } else {
        setErrors({ email: 'Không tìm thấy email trong hệ thống' });
      }
    } catch (err) {
      console.error('Forgot password error:', err);
      setErrors({ email: err.message || 'Có lỗi xảy ra, vui lòng thử lại' });
    } finally {
      setLoading(false);
    }
  };

  const handleReset = () => {
    setEmail('');
    setErrors({});
    setSubmitted(false);
  };

  return (
    <div className="forgot-container">
      <div className="forgot-form">
        {!submitted ? (
          <>
            <h2>Quên mật khẩu</h2>
            <p className="forgot-description">
              Nhập email của bạn để nhận liên kết đặt lại mật khẩu
            </p>
            
            <form onSubmit={handleSubmit}>
              <div className="form-group">
                <input
                  type="email"
                  placeholder="Email"
                  value={email}
                  onChange={handleChange}
                  className={errors.email ? 'error' : ''}
                  required
                />
                {errors.email && <span className="error-message">{errors.email}</span>}
              </div>

              <button type="submit" className="forgot-btn" disabled={loading}>
                {loading ? 'Đang gửi...' : 'Gửi liên kết đặt lại'}
              </button>
            </form>
          </>
        ) : (
          <div className="success-message">
            <div className="success-icon">✓</div>
            <h2>Kiểm tra email của bạn!</h2>
            <p>
              Chúng tôi đã gửi liên kết đặt lại mật khẩu đến<br />
              <strong>{email}</strong>
            </p>
            <p className="success-note">
              Nếu bạn không nhận được email trong vài phút, hãy kiểm tra thư mục spam.
            </p>
            
            <div className="success-actions">
              <button onClick={handleReset} className="reset-btn">
                Gửi lại
              </button>
              <Link to="/login" className="back-to-login">
                Quay lại đăng nhập
              </Link>
            </div>
          </div>
        )}

        {!submitted && (
          <div className="back-to-login">
            <Link to="/login">← Quay lại đăng nhập</Link>
          </div>
        )}
      </div>
    </div>
  );
}
