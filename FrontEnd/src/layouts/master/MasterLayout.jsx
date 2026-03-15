import React, { useState } from 'react';
import Navbar from '../../components/master/navbar/Navbar';
import Sidebar from '../../components/master/sidebar/Sidebar';
import Footer from '../../components/master/footer/Footer';
import './MasterLayout.css';
import { Outlet } from 'react-router-dom';

export default function MasterLayout() {
  const [sidebarExpanded, setSidebarExpanded] = useState(false);

  const toggleSidebar = () => {
    setSidebarExpanded(!sidebarExpanded);
  };

  return (
    <div className="master-layout">
      <Navbar onToggleSidebar={toggleSidebar} sidebarExpanded={sidebarExpanded} />
      <div className="layout-body">
        <Sidebar expanded={sidebarExpanded} />
        <main
          className="layout-main"
          style={{ marginLeft: sidebarExpanded ? 200 : 60 }}
        >
          <Outlet />
        </main>
      </div>
      <Footer />
    </div>
  );
}