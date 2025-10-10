# Order Service App

A **Spring Boot application** built to demonstrate how to implement and run **Integration Tests** using:

- **Spring Boot 3**
- **JUnit 5**
- **MockMvc**
- **Testcontainers (PostgreSQL)**
- **Spring Data JPA**

The project simulates a **Order Management Service**, where you can create and list orders through REST endpoints.  
The integration tests validate the **end-to-end behavior** from HTTP layer → Service → Repository → Database.

---

## Project Structure
```yaml
order-service-app/
├── src/main/java/com/example/orderservice/
│ ├── OrderServiceApplication.java      # Application entry point
│ ├── controller/OrderController.java   # REST endpoints
│ ├── model/Order.java                  # Entity class (JPA)
│ ├── repository/OrderRepository.java   # Data persistence layer
│ └── service/OrderService.java         # Business logic
└── src/test/java/com/example/orderservice/integration/
└── OrderControllerIT.java              # Integration tests using Testcontainers
```

---

## Features

### REST API with endpoints:
- `GET /orders` → Lists all orders
- `POST /orders` → Creates a new order

### Integration testing setup includes:
- **Spring Boot Test Context** (`@SpringBootTest`)
- **MockMvc** for HTTP request simulation
- **Testcontainers** PostgreSQL instance for realistic database testing
- **JUnit 5** test lifecycle and assertions

---

## Technologies Used

| Category | Technology |
|-----------|-------------|
| Framework | Spring Boot 3 |
| Language | Java 17 |
| Database | PostgreSQL (Testcontainers) |
| Build Tool | Maven |
| Testing | JUnit 5, MockMvc, Testcontainers |

---

## Prerequisites

- **Java 17+**
- **Maven 3.8+**
- **Docker** (required for Testcontainers)

Ensure Docker is running before executing integration tests.

---

## Running the Tests

Run all tests (including integration tests):

```bash
mvn clean test
```
Testcontainers will automatically:
- Pull a lightweight PostgreSQL Docker image
- Start it before your tests
- Stop and clean it after tests finish

You should see logs showing PostgreSQL starting up in Docker during test execution.

---

## Running the Application
If you just want to run the application (without tests):

```bash
mvn spring-boot:run
```

The app will start on http://localhost:8080.
You can test the endpoints using curl or Postman:

```bash
# Create an order
curl -X POST http://localhost:8080/orders \
     -H "Content-Type: application/json" \
     -d '{"customer": "Alice", "totalAmount": 199.99}'

# List all orders
curl http://localhost:8080/orders
```

## Why Integration Tests?
Integration tests ensure that:
- The system works as a whole, not just as isolated units.
- The API endpoints, service logic, and database layer all communicate correctly.
- Your application is production-ready and resilient to changes in configuration or dependencies.

---

## Example Integration Test (snippet)

```java
@Test
void shouldCreateOrder() throws Exception {
    mockMvc.perform(post("/orders")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"customer\": \"Alice\", \"totalAmount\": 199.99}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", notNullValue()))
            .andExpect(jsonPath("$.customer", is("Alice")));
}
```