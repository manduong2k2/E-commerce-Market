// Lấy từ environment variables
export const GATEWAY_URL = import.meta.env.VITE_GATEWAY_URL;
export const AUTH_SERVICE_NAME = import.meta.env.VITE_AUTH_SERVICE_NAME;
export const CATALOG_SERVICE_NAME = import.meta.env.VITE_CATALOG_SERVICE_NAME;
export const VENDOR_SERVICE_NAME = import.meta.env.VITE_VENDOR_SERVICE_NAME;

// Có thể thêm các constant khác dùng nhiều nơi
export const APP_NAME = 'Frontend System';
export const PAGE_SIZE = 20