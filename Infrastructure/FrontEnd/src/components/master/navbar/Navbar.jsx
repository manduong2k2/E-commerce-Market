import React, { useContext } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { AuthContext } from '../../../contexts/AuthContext';
import { authService } from '../../../services/authService';
import './Navbar.css';

export default function Navbar({ onToggleSidebar, sidebarExpanded }) {
  const { user, setUser } = useContext(AuthContext);
  const navigate = useNavigate();

  const handleLogout = async () => {
    try {
      await authService.logout();
      await setUser(null);
      window.location.href = '/login';
    } catch (err) {
      console.error(err);
      alert('Logout thất bại!');
    }
  };

  return (
    <nav className="navbar">
      <div className="navbar-left">
        {user ? <button className="toggle-btn" onClick={onToggleSidebar}>
          ☰
        </button> : null}
        <Link to="/dashboard" className="navbar-logo" >
          MyApp
        </Link>
      </div>

      <div className="navbar-right">
        {user ? (
          <>
            <span className="navbar-user">Hi, {user.name}</span>
            <button className="navbar-btn" onClick={handleLogout}>
              Logout
            </button>
          </>
        ) : (
          <>
            <Link to="/login" className="navbar-btn">
              Login
            </Link>
            <Link to="/register" className="navbar-btn">
              Register
            </Link>
          </>
        )}
      </div>
    </nav>
  );
}