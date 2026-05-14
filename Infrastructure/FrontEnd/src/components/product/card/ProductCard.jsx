import React from 'react';
import { useNavigate } from 'react-router-dom';
import './ProductCard.css';
import defaultProductImage from '../../../assets/product.png'

function ProductCard({ product }) {
  const navigate = useNavigate();
  const { name, price, files } = product;
  const imageUrl = files && files.length > 0 ? files[0].url : defaultProductImage;

  const handleClick = () => {
    // Navigate to product detail page
    navigate(`/product/${product.id}`);
  };

  return (
    <div className="product-card user-view" onClick={handleClick}>
      <div className="product-image-container">
        <img 
          src={imageUrl} 
          alt={name} 
          className="product-image"
          onError={(e) => {
            e.target.src = defaultProductImage;
          }}
        />
      </div>
      
      <div className="product-info">
        <h3 className="product-name">{name}</h3>
        {price && (
          <p className="product-price">${price}</p>
        )}
      </div>
    </div>
  );
}

export default ProductCard;