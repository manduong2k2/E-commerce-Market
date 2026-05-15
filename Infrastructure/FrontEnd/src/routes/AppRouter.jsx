// src/routes/AppRouter.jsx
import React, { useContext } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';

import MasterLayout from '../layouts/master/MasterLayout';
import AuthLayout from '../layouts/auth/AuthLayout';

import LoginPage from '../pages/auth/login/LoginPage';
import RegisterPage from '../pages/auth/register/RegisterPage';
import ForgotPasswordPage from '../pages/auth/forgot/ForgotPasswordPage';
import HomePage from '../pages/home/HomePage';
import OnboardingPage from '../pages/onboarding/OnboardingPage';
import VendorCreatePage from '../pages/vendor/create/VendorCreatePage';
import MyVendorPage from '../pages/vendor/my/MyVendorPage';
import VendorDetailPage from '../pages/vendor/detail/VendorDetailPage';

import { AuthContext } from '../contexts/AuthContext';
import '../index.css';

// ===== PrivateRoute =====
const PrivateRoute = ({ children }) => {
  const { user, loading } = useContext(AuthContext);

  if (loading) return <p>Loading...</p>;
  return user ? children : <Navigate to="/login" replace />;
};

// ===== PublicRoute (optional logic) =====
const PublicRoute = ({ children }) => {
  return children;
};

export default function AppRouter() {
  return (
    <Router>
      <Routes>

        {/* Auth pages */}
        <Route element={<AuthLayout />}>
          <Route path="/login" element={<LoginPage />} />
          <Route path="/register" element={<RegisterPage />} />
          <Route path="/forgot" element={<ForgotPasswordPage />} />
        </Route>

        {/* Onboarding — standalone, no layout wrapper */}
        <Route path="/onboarding" element={<OnboardingPage />} />

        {/* Route for all users */}
        <Route element={<PublicRoute><MasterLayout /></PublicRoute>}>
          <Route path="/home" element={<HomePage />} />
          <Route path="/vendor/:id" element={<VendorDetailPage />} />
        </Route>

        {/* Private pages */}
        <Route element={<PrivateRoute><MasterLayout /></PrivateRoute>}>
          <Route path="/vendor-create" element={<VendorCreatePage />} />
          <Route path="/my-vendor" element={<MyVendorPage />} />
          {/* <Route path="/profile" element={<ProfilePage />} /> */}
          {/* <Route path="/orders" element={<OrdersPage />} /> */}
        </Route>

        {/* Root redirect */}
        <Route path="/" element={<Navigate to="/home" replace />} />

      </Routes>
    </Router>
  );
}