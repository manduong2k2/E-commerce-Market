import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import VendorCard from '../../../components/vendor/card/VendorCard';
import ProductList from '../../../components/product/list/ProductList';
import { vendorService } from '../../../services/vendorService';
import './MyVendorPage.css';

const TABS = [
  { key: 'vendor',   label: 'Vendor',    icon: '🏪' },
  { key: 'products', label: 'Products',  icon: '📦' },
  { key: 'revenue',  label: 'Revenue',   icon: '💰' },
];

export default function MyVendorPage() {
  const navigate = useNavigate();
  const [activeTab, setActiveTab] = useState('vendor');
  const [vendor, setVendor] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function fetchMyVendor() {
      try {
        const res = await vendorService.getMyVendor();
        if (res.ok) {
          setVendor(res.data?.data ?? res.data);
        } else {
          setVendor(null);
        }
      } catch {
        setVendor(null);
      } finally {
        setLoading(false);
      }
    }
    fetchMyVendor();
  }, []);

  return (
    <div className="my-vendor-page">

      {/* ── Sidebar ── */}
      <aside className="my-vendor-sidebar">
        <p className="my-vendor-sidebar-title">My Store</p>
        {TABS.map((tab) => (
          <button
            key={tab.key}
            className={`my-vendor-nav-item ${activeTab === tab.key ? 'active' : ''}`}
            onClick={() => setActiveTab(tab.key)}
          >
            <span className="my-vendor-nav-icon">{tab.icon}</span>
            {tab.label}
          </button>
        ))}
      </aside>

      {/* ── Content ── */}
      <div className="my-vendor-content">

        {/* Tab: Vendor */}
        {activeTab === 'vendor' && (
          <>
            <div className="my-vendor-tab-header">
              <h2>My Vendor Store</h2>
              {vendor && (
                <button
                  className="btn-edit-vendor"
                  onClick={() => navigate('/vendor-edit')}
                >
                  ✏️ Edit Store
                </button>
              )}
            </div>

            {loading && (
              <div className="my-vendor-loading">
                <p>Loading your store...</p>
              </div>
            )}

            {!loading && !vendor && (
              <div className="my-vendor-empty">
                <span className="my-vendor-empty-icon">🏪</span>
                <h3>You don't have a store yet</h3>
                <p>Create your vendor store to start selling products.</p>
                <button
                  className="btn-create-vendor"
                  onClick={() => navigate('/vendor-create')}
                >
                  + Create Store
                </button>
              </div>
            )}

            {!loading && vendor && (
              <VendorCard vendor={vendor} />
            )}
          </>
        )}

        {/* Tab: Product List */}
        {activeTab === 'products' && (
          <ProductList vendorId={vendor?.id} />
        )}

        {/* Tab: Revenue */}
        {activeTab === 'revenue' && (
          <div className="revenue-placeholder">
            <span className="revenue-placeholder-icon">📊</span>
            <p>Revenue analytics coming soon.</p>
          </div>
        )}

      </div>
    </div>
  );
}
