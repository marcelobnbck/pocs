# Order Service App

## The problem
Provide a small REST service to create and query customer orders, with data stored permanently in a database, clear error responses, automated tests, and a one-command way to run it.

## Architecture
A layered Spring Boot application backed by PostgreSQL.

```
Client --HTTP/JSON--> OrderController --> OrderService --> OrderRepository --JDBC--> PostgreSQL
                            |                                                          ^
                    GlobalExceptionHandler                                  Flyway migrations (on startup)
```

| Layer | Class | Responsibility |
|---|---|---|
| Web | `OrderController` | HTTP routes, input validation, status codes |
| DTO | `CreateOrderRequest`, `OrderResponse` | API contract (separate from the database entity) |
| Service | `OrderService` | Business logic and transactions |
| Persistence | `OrderRepository`, `Order` | JPA entity and Spring Data repository |
| Errors | `GlobalExceptionHandler` | Converts exceptions into JSON error responses |
| Schema | `db/migration/V1__create_orders_table.sql` | Database table definition |

### API
| Method | Path | Success | Errors |
|---|---|---|---|
| POST | `/orders` | 201 Created + `Location` header | 400 invalid body |
| GET | `/orders` | 200 list | |
| GET | `/orders/{id}` | 200 order | 404 not found, 400 non-numeric id |
| GET | `/actuator/health` | 200 | |

Request body for `POST /orders`:
```json
{"customer": "Alice", "totalAmount": 199.99}
```
`customer` is required (max 120 characters). `totalAmount` is required, greater than zero, with at most 2 decimal places.

Error response (RFC 7807, `application/problem+json`):
```json
{"title": "Invalid request", "status": 400, "detail": "Validation failed",
 "errors": {"totalAmount": "totalAmount must be greater than zero"}}
```

## Technologies
| Purpose | Technology |
|---|---|
| Language / runtime | Java 21 |
| Framework | Spring Boot 3.3.4 (Web, Data JPA, Validation, Actuator) |
| Database | PostgreSQL 16 |
| Schema migrations | Flyway |
| Build | Maven |
| Tests | JUnit 5, Mockito, MockMvc, Testcontainers |
| Packaging / running | Docker, Docker Compose |

## How to run

### With Docker (recommended)
Requires only Docker.
```bash
docker compose up --build
```
The app runs at http://localhost:8080. Data is stored in the `orders-data` Docker volume and survives restarts. To delete the data: `docker compose down -v`.

Try it:
```bash
curl -X POST http://localhost:8080/orders -H "Content-Type: application/json" \
     -d '{"customer":"Alice","totalAmount":199.99}'
curl http://localhost:8080/orders
curl http://localhost:8080/orders/1
curl http://localhost:8080/actuator/health
```

### Locally (app outside Docker)
Requires Java 21, Maven 3.8+ and Docker (for the database).
```bash
docker compose up -d db
mvn spring-boot:run
```
Database settings can be overridden with the environment variables `DB_URL`, `DB_USER` and `DB_PASSWORD` (defaults match `docker-compose.yml`).

## How to test
```bash
mvn test      # unit and controller tests; Docker not needed
mvn verify    # also runs the integration test; Docker must be running
```

| Test | Type | What it checks |
|---|---|---|
| `OrderServiceTest` | Unit (Mockito) | Service logic, including not-found |
| `OrderControllerTest` | `@WebMvcTest`, service mocked | Status codes, validation, error format |
| `OrderControllerIT` | `@SpringBootTest` + Testcontainers | Full flow against a real PostgreSQL, including migrations |

## Technical decisions
- **PostgreSQL + Docker volume**: orders are persisted for real and survive restarts.
- **Flyway owns the schema**: the table is versioned in SQL, and Hibernate only validates it (`ddl-auto: validate`). A database `CHECK` also enforces `total_amount > 0`.
- **`BigDecimal` / `NUMERIC(19,2)` for money**: avoids floating-point rounding errors that `Double` causes.
- **DTOs instead of exposing the entity**: clients cannot set `id` or `createdAt`, and the API can change independently of the table.
- **Bean Validation on the request**: invalid input is rejected with 400 before reaching the service or database.
- **Central error handling with RFC 7807**: one place maps exceptions to consistent JSON errors. Unexpected errors are logged on the server and return a generic 500, so internal details are not leaked.
- **201 Created with `Location` header** for `POST /orders`, following REST conventions.
- **Configuration through environment variables**: the same image runs in any environment.
- **Multi-stage Docker build, non-root user**: small runtime image and safer container. Compose starts the app only after the database is healthy.
- **Three test levels**: fast tests without Docker for everyday work, plus one realistic integration test against a real database.
- **`open-in-view: false`**: avoids lazy database access during HTTP response rendering.
