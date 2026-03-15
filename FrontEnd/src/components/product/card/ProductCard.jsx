import React from 'react';
import './ProductCard.css'; // import file CSS riêng

function ProductCard({ product }) {
  const { name, categories, brand, priceMin, priceMax, files } = product;
  const imageUrl = files.length > 0 ? files[0].url : 'https://via.placeholder.com/150';

  return (
    <div className="product-card">
      <img src={imageUrl} alt={name} className="product-image" />
      <div className="product-info">
        <a href="#" className="product-name">{name}</a>
        <p className="product-brand">Brand: {brand}</p>
        <p className="product-categories">Categories: {categories.join(', ')}</p>
        <p className="product-price">Price: ${priceMin} ~ ${priceMax}</p>
      </div>
    </div>
  );
}

export default ProductCard;