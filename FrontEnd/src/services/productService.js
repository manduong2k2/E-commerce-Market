// services/productService.js
import { PRODUCT_API_URL } from '../configs/constants';

// helper chung
async function request(path, options = {}) {
  const { method = 'GET', body } = options;
  const res = await fetch(`${PRODUCT_API_URL}${path}`, {
    method,
    headers: { 'Content-Type': 'application/json' },
    credentials: 'include', // gửi cookie HttpOnly nếu cần
    body: body ? JSON.stringify(body) : undefined,
  });

  const contentType = res.headers.get('Content-Type') || '';
  if (contentType.includes('application/json')) {
    return res.json();
  } else {
    return res.text();
  }
}

// ===== Product APIs =====
export const productService = {
  // Lấy danh sách sản phẩm, có thể thêm params như page, filter
  getAll: (params = {}) => {
    const query = new URLSearchParams(params).toString();
    return request(`/products${query ? `?${query}` : ''}`);
  },

  // Lấy chi tiết 1 sản phẩm
  getById: (id) => request(`/products/${id}`),

  // Tạo sản phẩm mới
  create: (data) => request('/products', { method: 'POST', body: data }),

  // Cập nhật sản phẩm
  update: (id, data) => request(`/products/${id}`, { method: 'PUT', body: data }),

  // Xóa sản phẩm
  delete: (id) => request(`/products/${id}`, { method: 'DELETE' }),
};