import React from 'react';
import { Outlet } from 'react-router-dom';
import Navbar from '../../components/master/navbar/Navbar';
import Footer from '../../components/master/footer/Footer';
import '../../pages/auth/login/LoginPage.css';
import './AuthLayout.css';

export default function AuthLayout() {
  return (
    <div className="auth-layout-wrapper">
      <Navbar />
      <div className="auth-layout-page">
        <div className="auth-layout-card">
          <Outlet /> {/* Form login hoặc các route con */}
        </div>
      </div>
      <Footer />
    </div>
  );
}