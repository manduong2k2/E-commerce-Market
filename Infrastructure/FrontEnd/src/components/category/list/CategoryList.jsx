// src/components/category/CategoryList.jsx
import React, { useState, useEffect } from 'react';
import CategoryCard from '../card/CategoryCard';
import { categoryService } from '../../../services/categoryService';
import './CategoryList.css';

export default function CategoryList() {
  const [categories, setCategories] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetchCategories();
  }, []);

  const fetchCategories = async () => {
    try {
      setLoading(true);
      const response = await categoryService.getAll();
      setCategories(response.data || response);
    } catch (err) {
      console.error('Error fetching categories:', err);
      setError('Không thể tải danh mục');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="category-list-container user-view">
      <div className="list-header">
        <h2>Danh mục sản phẩm</h2>
      </div>

      {loading ? (
        <div className="loading-container">
          <div className="spinner"></div>
          <p>Đang tải danh mục...</p>
        </div>
      ) : error ? (
        <div className="error-state">
          <p>{error}</p>
        </div>
      ) : categories.length === 0 ? (
        <div className="empty-state">
          <div className="empty-icon">📦</div>
          <h3>Chưa có danh mục nào</h3>
          <p>Vui lòng quay lại sau</p>
        </div>
      ) : (
        <div className="category-grid">
          {categories.map(category => (
            <CategoryCard
              key={category.id}
              category={category}
            />
          ))}
        </div>
      )}
    </div>
  );
}
