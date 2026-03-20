// src/components/product/ProductList.jsx
import React, { useEffect, useState } from 'react';
import ProductCard from '../card/ProductCard';
import { productService } from '../../../services/productService';
import './ProductList.css';

function ProductList() {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function fetchProducts() {
      try {
        const data = await productService.getAll();
        setProducts(data.data || []);
      } catch (err) {
        console.error('Lỗi khi lấy danh sách sản phẩm:', err);
      } finally {
        setLoading(false);
      }
    }

    fetchProducts();
  }, []);

  if (loading) {
    return <p>Loading products...</p>;
  }

  return (
    <div className="product-list">
      {products.length === 0 && <p>Không có sản phẩm nào.</p>}
      {products.map(product => (
        <ProductCard key={product.id} product={product} />
      ))}
    </div>
  );
}

export default ProductList;