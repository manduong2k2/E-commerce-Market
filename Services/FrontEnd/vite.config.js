import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
  plugins: [react()],
  server: {
    host: '0.0.0.0',
    port: 5173,
    allowedHosts: ['jul2nd.myvnc.com'],

    hmr: {
      host: 'jul2nd.myvnc.com',
      protocol: 'wss',
      clientPort: 443
    }
  }
})