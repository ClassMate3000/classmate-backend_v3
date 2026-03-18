# ClassMate — Backend

Containerized microservices backend for the ClassMate student academic planning application.
Handles course management, task tracking, grade snapshots, reminders, and JWT-based authentication.

---

## Architecture

- Microservices architecture — one service per domain
- API Gateway on port 8091 handles all routing and JWT verification
- Database per service — PostgreSQL for relational data, MongoDB for document data
- Docker Compose for local orchestration
- Flyway for PostgreSQL schema migrations
- Testcontainers for integration testing

---

## Services

| Service | Database | Port | Description |
|---|---|---|---|
| api-gateway | — | 8091 | Routes all requests, enforces JWT auth |
| user-service | PostgreSQL | 8088 | Registration, login, JWT token generation |
| course-service | PostgreSQL | 8084 | Course CRUD, meeting schedules, grade goals |
| courseprogress-service | PostgreSQL | 8085 | Weekly grade snapshots per course |
| task-service | MongoDB | 8086 | Task CRUD, priority, due dates |
| reminder-service | MongoDB | 8087 | Reminder CRUD linked to tasks |

---

## Tech Stack

- Java 21
- Spring Boot 3.2
- Spring Cloud Gateway (WebFlux)
- Spring Security + JWT (JJWT)
- PostgreSQL 15
- MongoDB 7
- Flyway
- Docker & Docker Compose
- JUnit 5 + Mockito
- Testcontainers
- Gradle (multi-project build)
- Swagger / OpenAPI (per service)

---

## Prerequisites

- Java 21+
- Docker & Docker Compose
- Git

---

## Getting Started

Clone and start all services:
```bash
git clone https://github.com/ClassMate3000/classmate-backend_v3.git
cd classmate-backend_v3
docker compose up -d --build
```

Verify containers are running:
```bash
docker ps
```

All services should show `Up` status. The API Gateway is available at `http://localhost:8091`.

---

## Auth Flow

Register a user:
```bash
curl -X POST http://localhost:8091/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{"firstName":"Test","lastName":"User","email":"test@test.com","password":"123"}'
```

Login to get a JWT token:
```bash
curl -X POST http://localhost:8091/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"test@test.com","password":"123"}'
```

Use the returned token on all subsequent requests:
```bash
curl http://localhost:8091/api/v1/courses \
  -H "Authorization: Bearer <token>"
```

---

## API Endpoints

All endpoints are accessed through the API Gateway on port 8091.

| Resource | Method | Endpoint |
|---|---|---|
| Auth | POST | `/api/v1/auth/register` |
| Auth | POST | `/api/v1/auth/login` |
| Courses | GET, POST | `/api/v1/courses` |
| Courses | GET, PUT, DELETE | `/api/v1/courses/{id}` |
| Tasks | GET, POST | `/api/v1/tasks` |
| Tasks | GET, PUT, DELETE | `/api/v1/tasks/{id}` |
| Reminders | GET, POST | `/api/v1/reminders` |
| Reminders | GET, PUT, DELETE | `/api/v1/reminders/{id}` |
| Course Progress | GET, POST | `/api/v1/course-progress` |
| Course Progress | GET, PUT, DELETE | `/api/v1/course-progress/{id}` |

---

## Running Tests
```bash
./gradlew test
```

Integration tests automatically spin up PostgreSQL and MongoDB via Testcontainers — no external database setup required.

---

## Project Structure
```
classmate-backend_v3/
├── api-gateway/
├── user-service/
├── course-service/
├── courseprogress-service/
├── task-service/
├── reminder-service/
├── docker/
├── docker-compose.yml
├── build.gradle
├── settings.gradle
└── README.md
```

---

## Frontend

See [classmate-frontend_v3](https://github.com/ClassMate3000/classmate-frontend_v3) for the React frontend.
The frontend expects the API Gateway on port `8091` — set `VITE_API_GATEWAY_URL=http://localhost:8091` in your `.env`.