// services/categoryService.js
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

// ===== Category APIs =====
export const categoryService = {
  // Lấy danh sách danh mục, có thể thêm params như page, filter
  getAll: () => {
    return request(`/api/v1/categories`);
  },

  // Lấy chi tiết 1 danh mục
  getById: (id) => request(`/api/v1/categories/${id}`),

  // Tạo danh mục mới
  create: (data) => request('/api/v1/categories', { method: 'POST', body: data }),

  // Cập nhật danh mục
  update: (id, data) => request(`/api/v1/categories/${id}`, { method: 'PUT', body: data }),

  // Xóa danh mục
  delete: (id) => request(`/api/v1/categories/${id}`, { method: 'DELETE' }),

  // Lấy danh mục theo trạng thái
  getByStatus: (status) => request(`/api/v1/categories/status/${status}`),

  // Tìm kiếm danh mục
  search: (keyword) => request(`/api/v1/categories/search?q=${encodeURIComponent(keyword)}`),

  // Lấy số lượng sản phẩm trong danh mục
  getProductCount: (categoryId) => request(`/api/v1/categories/${categoryId}/products/count`),
};