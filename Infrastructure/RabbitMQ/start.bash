#!/bin/bash

# Thực hiện docker-compose up -d
docker-compose up -d

# Kiểm tra exit code của lệnh vừa chạy
if [ $? -eq 0 ]; then
    echo "✅ Docker Compose started successfully."
else
    echo "❌ Docker Compose failed to start."
fi