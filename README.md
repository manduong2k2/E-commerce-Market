# E-Commerce Platform

A full-stack e-commerce platform built with a **microservices architecture**, combining Spring Boot, Laravel, and React.

---

## Architecture Overview

```
                        ┌─────────────────┐
                        │   React Frontend│
                        │   (Vite + SPA)  │
                        └────────┬────────┘
                                 │ HTTPS
                        ┌────────▼────────┐
                        │   Kong Gateway  │
                        │  (API Gateway)  │
                        └────────┬────────┘
              ┌──────────────────┼──────────────────┐
              │                  │                  │
    ┌────────────────┐  ┌────────────────┐  ┌─────────────────┐
    │  Auth Service  │  │ Catalog Service│  │ Vendor Service  │
    │ (Spring Boot)  │  │ (Spring Boot)  │  │ (Spring Boot)   │
    └────────────────┘  └────────────────┘  └─────────────────┘
              │                  │                   │
    ┌────────────────┐  ┌────────────────┐  ┌────────────────────┐
    │  Cart Service  │  │ Order Service  │  │ Deliveries Service │
    │   (Laravel)    │  │   (Laravel)    │  │ (Spring Boot)      │
    └────────────────┘  └────────────────┘  └────────────────────┘
                                 │
                        ┌────────▼────────┐
                        │    RabbitMQ     │
                        │ (Message Broker)│
                        └─────────────────┘
```

All services communicate through **Kong API Gateway**. Async inter-service events are handled via **RabbitMQ**.

---

## Services

### Backend Services (`Services/`)

| Service | Tech | Port | Database | Description |
|---|---|---|---|---|
| **Auth** | Spring Boot 4.0.5 / Java 24 | default | `auth_services` | Authentication, JWT, email verification |
| **Catalog** | Spring Boot 4.1.0 / Java 24 | 8083 | `catalog_services` | Products, categories, brands (v2 - DDD) |
| **Vendors** | Spring Boot 4.1.0 / Java 24 | 8082 | `vendor_service` | Vendor/seller management |
| **Orders** | Laravel 13 / PHP 8.2 | artisan | `order_services` | Order management |
| **Carts** | Laravel 13 / PHP 8.2 | artisan | `cart_services` | Shopping cart |
| **Billings** | Laravel 13 / PHP 8.2 | artisan | `billing_services` | Payments & invoices |
| **Deliveries** | Laravel 13 / PHP 8.2 | artisan | `delivery_services` | Delivery management |

### Infrastructure (`Infrastructure/`)

| Component | Tech | Port | Description |
|---|---|---|---|
| **Gateway** | Kong 3.6 | 8000 (proxy), 8001 (admin) | API Gateway with PostgreSQL config store |
| **RabbitMQ** | RabbitMQ 4.2.4 | 5673 (AMQP), 15673 (UI) | Message broker for async events |
| **StorageService** | Spring Boot 4.1.0 / Java 24 | 8081 | File/image upload and serving |
| **FrontEnd** | React 19 + Vite 7 | 5173 (dev) | Single-page application |

---

## Tech Stack

### Backend
- **Spring Boot 4.x** — Java 24, Spring Security, Spring Data JPA, Spring AMQP, Spring WebFlux
- **Laravel 13** — PHP 8.2, Eloquent ORM, Laravel Sanctum, Tymon JWT Auth, php-amqplib
- **Authentication** — JWT with RSA key pair (RS256). Auth service holds the private key; all other services verify with the public key only.
- **Architecture** — Java services follow **Domain-Driven Design (DDD)** with `Application`, `Domain`, and `Infrastructure` layers for each bounded context.

### Database
- **PostgreSQL** — each service has its own isolated database

### Messaging
- **RabbitMQ** — event-driven communication between services (e.g., order creation triggered via cart/billing events)

### Frontend
- **React 19** + **Vite 7**
- **React Router DOM 7**
- **FontAwesome** for icons
- All API calls routed through Kong Gateway + Service name

### DevOps
- **Docker Compose** — Gateway and RabbitMQ
- **GitHub Actions** — CI/CD pipeline (deploy on push to `main`)
- **Deploy target** — `your-domain.com`

---

## Project Structure

```
E-com/
├── .github/
│   └── workflows/
│       └── deploy.yml              # CI/CD pipeline
├── Infrastructure/
│   ├── FrontEnd/                   # React SPA - STARTED
│   │   └── src/
│   │       ├── routes/             # AppRouter, PrivateRoute
│   │       ├── contexts/           # AuthContext
│   │       ├── services/           # authService, categoryService, productService
│   │       ├── components/         # Reusable UI components
│   │       ├── pages/              # auth (login, register, forgot), home
│   │       ├── layouts/            # AuthLayout, MasterLayout
│   │       └── configs/            # constants.js (GATEWAY_URL, service names)
│   ├── Gateway/
│   │   └── docker-compose.yml      # Kong API Gateway
│   ├── RabbitMQ/
│   │   └── docker-compose.yml      # RabbitMQ message broker
│   └── StorageService/             # Spring Boot file storage
└── Services/
    ├── Auth/                       # Spring Boot — Authentication - STARTED
    ├── Catalog/                    # Spring Boot — Product catalog (v2, DDD) - STARTED
    ├── Vendors/                    # Spring Boot — Vendor management - STARTED
    ├── Orders/                     # Laravel — Order management - NOT STARTED
    ├── Carts/                      # Laravel — Shopping cart - NOT STARTED
    ├── Billings/                   # Laravel — Billing & payments - NOT STARTED
    └── Deliveries/                 # Laravel — Delivery management - NOT STARTED
```

### DDD Layer Structure (Java services)

```
src/main/java/com/e_com/{ServiceName}/
├── Application/
│   ├── Controller/     # REST controllers
│   ├── DTO/            # Request/Response DTOs
│   └── Service/        # Application services
├── Domain/
│   ├── Contract/       # Interfaces (IService, IRepository)
│   ├── Model/          # Domain models (AggregateRoot, Entity, ValueObject)
│   └── Constants/      # Enums, constants
├── Infrastructure/
│   ├── Persistence/    # JPA entities, repositories
│   ├── Messaging/      # RabbitMQ consumers/publishers
│   └── Security/       # JWT, auth aspects
└── Shared/             # Cross-cutting concerns
```

---

## Features

- **Authentication**  — Register, login, logout, refresh token, email verification, forgot/reset password
- **Product Catalog** — CRUD for products (with variants, extra attributes), categories, and brands
- **Shopping Cart**   — Add, update, remove cart items (requires authentication)
- **Orders**          — View and manage orders (created via RabbitMQ events from cart/billing)
- **Billing**         — Payment processing and invoice management
- **Delivery**        — Delivery tracking and management
- **Vendors**         — Vendor/seller registration and management
- **File Storage**    — Upload and serve product variant images
- **Event-Driven**    — Services communicate asynchronously via RabbitMQ
- **Gateway Routing** — Kong auto-registers service routes on startup via `GatewayRegistry`

---

## Getting Started

### Prerequisites

- Docker & Docker Compose
- Java 24 + Maven
- PHP 8.2 + Composer
- Node.js 20+ + npm
- PostgreSQL (running locally on port 5432)

### 1. Start Infrastructure

```bash
# Start RabbitMQ
cd Infrastructure/RabbitMQ
docker-compose up -d
# UI available at http://localhost:15673 (admin / admin)

# Start Kong Gateway (requires a 'kong' database in PostgreSQL)
cd Infrastructure/Gateway
docker-compose up -d
# Proxy: http://localhost:8000
# Admin API: http://localhost:8001
```

### 2. Start Java Services

```bash
# Auth Service
cd Services/Auth
./mvnw spring-boot:run

# Storage Service
cd Infrastructure/StorageService
./mvnw spring-boot:run

# Catalog
cd Services/Catalog
./mvnw spring-boot:run

# Vendor Service
cd Services/Vendors
./mvnw spring-boot:run
```

### 3. Start Laravel Services

```bash
# Example for Catalog (repeat for Orders, Carts, Billings, Deliveries)
cd Services/Catalog
composer install
cp .env.example .env
php artisan key:generate
php artisan migrate
php artisan serve
```

### 4. Start Frontend

```bash
cd Infrastructure/FrontEnd
npm install
npm run dev       # development server
npm run build     # production build
```

---

## Environment Configuration

### Frontend (`Infrastructure/FrontEnd/.env`)

```env
VITE_GATEWAY_URL=https://gateway.your-domain.com
VITE_AUTH_SERVICE_NAME=auth-service
VITE_CATALOG_SERVICE_NAME=catalog-service
```

### Java Services (`application.properties`)

Each service requires:
- `spring.datasource.url` — PostgreSQL connection
- `jwt.public-key`        — RSA public key for JWT verification
- `spring.rabbitmq.*`     — RabbitMQ connection
- `spring.mail.*`         — SMTP (Gmail) for email notifications

### Auth Service additionally requires:
- `jwt.private-key` — RSA private key for signing tokens

---

## Production URLs

| Service    |                 URL                  |
|------------|--------------------------------------|
| Gateway    | `https://gateway.your-domain.com`    |
| Auth       | `https://auth.your-domain.com`       |
| Catalog    | `https://catalog.your-domain.com`    |
| Storage    | `https://storage.your-domain.com`    |
| Vendors    | `https://vendors.your-domain.com`    |
| Deliveries | `https://deliveries.your-domain.com` |

---

## CI/CD

GitHub Actions deploys automatically on push to `main`:

1. Checkout source code
2. Setup SSH key from `SSH_PRIVATE_KEY` secret
3. SSH into `your-domain.com` and run `git pull origin main`

> Note: The current pipeline only pulls the latest code. Service restarts must be done manually on the server.

---

## Notes
- **JWT uses RSA key pair**: Only the Auth service holds the private key. All other services verify tokens using the shared public key.
- **Orders are event-driven**: The Orders API does not expose a `store` or `update` endpoint — orders are created via RabbitMQ events from Cart/Billing services.
- **All API traffic goes through Kong**: Frontend calls `{GATEWAY_URL}/{service-name}/api/...` for every request.
