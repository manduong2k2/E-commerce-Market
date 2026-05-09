// services/productService.js
import { GATEWAY_URL, CATALOG_SERVICE_NAME } from '../configs/constants';

// helper chung
async function request(path, options = {}) {

  const { method = 'GET', body } = options;

  const res = await fetch(
    `${GATEWAY_URL}/${CATALOG_SERVICE_NAME}${path}`,
    {
      method,
      headers: {
        'Content-Type': 'application/json'
      },
      credentials: 'include',
      body: body ? JSON.stringify(body) : undefined,
    }
  );

  const contentType =
    res.headers.get('Content-Type') || '';

  let data;

  if (contentType.includes('application/json')) {
    data = await res.json();
  } else {
    data = await res.text();
  }

  return {
    ok: res.ok,
    status: res.status,
    headers: res.headers,
    data
  };
}

// ===== Product APIs =====
export const productService = {
  // Lấy danh sách sản phẩm, có thể thêm params như page, filter
  getAll: (params = {}) => {
    const query = new URLSearchParams(params).toString();
    return request(`/api/v1/products${query ? `?${query}` : ''}`);
  },

  // Lấy chi tiết 1 sản phẩm
  getById: (id) => request(`/api/v1/products/${id}`),

  // Tạo sản phẩm mới
  create: (data) => request('/api/v1/products', { method: 'POST', body: data }),

  // Cập nhật sản phẩm
  update: (id, data) => request(`/api/v1/products/${id}`, { method: 'PUT', body: data }),

  // Xóa sản phẩm
  delete: (id) => request(`/api/v1/products/${id}`, { method: 'DELETE' }),
};