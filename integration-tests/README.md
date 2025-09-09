# Todo App – Spring Boot + Testcontainers

A Todo API built with Spring Boot 3, Spring Data JPA, and PostgreSQL.
It comes with full-stack integration tests powered by JUnit 5, MockMvc, and Testcontainers.

## Features

- REST API for managing Todos (GET, POST, PUT, DELETE)
- PostgreSQL persistence with Spring Data JPA
- Integration tests with Testcontainers (runs PostgreSQL inside Docker)
- Validation with Jakarta Bean Validation
- Auto schema creation (ddl-auto=update)

## Getting Started

### 1. Prerequisites
- Java 17+
- Maven 3.9+
- Docker (required for Testcontainers)

Verify installation:
```bash
java --version
mvn --version
docker --version
```

### 2. Clone & Build
```bash
git clone https://github.com/marcelobnbck/integration-tests
cd todo-app
mvn clean package
```

### 3. Run the app locally
Make sure PostgreSQL is running (or configure your own connection in application.yaml):
```bash
mvn spring-boot:run
```
API will be available at: `http://localhost:8080/api/todos`

## Running Tests
Integration tests use Testcontainers to spin up a real PostgreSQL inside Docker.
No manual DB setup required.
```bash
mvn test
```

During tests you can check running containers:
```bash
docker ps
```

## API Endpoints
### List Todos
```http
GET /api/todos
```

### Get by ID
```http
GET /api/todos/{id}
```

### Create Todo
```http
POST /api/todos
Content-Type: application/json

{
    "title": "Buy coffee",
    "done": false
}
```

### Update Todo
```http
PUT /api/todos/{id}
Content-Type: application/json

{
    "title": "Buy more coffee",
    "done": true
}
```

### Delete Todo
```http
DELETE /api/todos/{id}
```

## Tech Stack
- Spring Boot 3 (Web, Data JPA, Validation)
- PostgreSQL (runtime + Testcontainers)
- JUnit 5 / MockMvc (integration testing)
- Docker + Testcontainers (ephemeral DB for tests)