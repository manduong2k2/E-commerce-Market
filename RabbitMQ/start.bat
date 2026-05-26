@echo off
docker-compose up -d
IF %ERRORLEVEL% EQU 0 (
    echo ✅ Docker Compose started successfully.
) ELSE (
    echo ❌ Docker Compose failed to start.
)
pause