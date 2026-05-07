// src/components/category/CategoryCard.jsx
import React from 'react';
import { useNavigate } from 'react-router-dom';
import './CategoryCard.css';

export default function CategoryCard({ category }) {
  const navigate = useNavigate();

  const handleClick = () => {
    // Navigate to category products page
    navigate(`/category/${category.id}`);
  };

  return (
    <div className="category-card user-view" onClick={handleClick}>
      <div className="category-image-container">
        <img 
          src={category.image || 'https://via.placeholder.com/200x200?text=Category'} 
          alt={category.name}
          className="category-image"
          onError={(e) => {
            e.target.src = 'https://via.placeholder.com/200x200?text=No+Image';
          }}
        />
      </div>
      
      <div className="category-info">
        <h3 className="category-name">{category.name}</h3>
      </div>
    </div>
  );
}
