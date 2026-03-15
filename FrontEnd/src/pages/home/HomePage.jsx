import React from 'react';
import ProductList from '../../components/product/list/ProductList';
import './HomePage.css';

function HomePage() {
  return (
    <div className="homepage">
      <main className="homepage-content">
        <ProductList />
      </main>
    </div>
  );
}

export default HomePage;