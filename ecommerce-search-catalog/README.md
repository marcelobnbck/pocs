# E-commerce Search & Catalog

A REST API for product catalog and search, built using **Java 21**, **Spring Boot 3.4**, **Spring Data Elasticsearch**, and Elasticsearch 8. The project features CRUD operations, text-based search and filtering, input validation, error responses in the `ProblemDetail` format, OpenAPI documentation, and integration tests executed against a live Elasticsearch container via Testcontainers.

---

## Architecture

- `product`: Elasticsearch document, repository, service, and controller.
- `common`: Centralized HTTP error handling.
- `ProductService`: Business logic and queries using `ElasticsearchOperations`.
- `ProductServiceIntegrationTest`: Integration test with a real Elasticsearch instance via Testcontainers.
- `ProductServiceUnitTest`: Unit test for the "missing document" scenario.

---

## Endpoints

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/products` | Indexes a product |
| GET | `/api/products/{id}` | Retrieves by ID; returns 404 if it does not exist |
| PUT | `/api/products/{id}` | Updates an existing product |
| DELETE | `/api/products/{id}` | Removes a product; returns 404 if it does not exist |
| GET | `/api/products` | Lists products with pagination and sorting |
| GET | `/api/products/search` | Searches by text with optional filters |
### Exemplos de uso

Criar produto:
```bash
curl -X POST http://localhost:8080/api/products \
  -H 'Content-Type: application/json' \
  -d '{
    "name":"Bluetooth Headphones",
    "description":"Wireless headphones with noise cancellation",
    "category":"audio",
    "brand":"Acme",
    "price":299.90,
    "stock":12
  }'
```

List with pagination:
```bash
curl 'http://localhost:8080/api/products?page=0&size=20&sort=price,asc'
```

Search by text and filter:
```bash
curl 'http://localhost:8080/api/products/search?q=headphones&category=audio&brand=Acme&minPrice=100&maxPrice=350'
```

Retrieve b ID:
```bash
curl http://localhost:8080/api/products/YOUR_ID
```

---

## Requirements

- JDK 21
- Maven 3.9+
- Docker Desktop/Engine running (required for integration tests)
- IDE with Maven support (IntelliJ IDEA, Eclipse, or VS Code)
---

## Running locally

1. Start Elasticsearch:
```bash
docker compose up -d
```
2. Confirm that it is healthy:
```bash
curl http://localhost:9200
```
3. Run the application:
```bash
./mvnw spring-boot:run
```
If the project does not yet have the Maven Wrapper in your IDE, use `mvn spring-boot:run` (or generate the wrapper using `mvn wrapper:wrapper`).
4. Access:
- API: `http://localhost:8080/api/products`
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- Health: `http://localhost:8080/actuator/health`

---

## Tests

With Docker running, execute:
```bash
mvn test
```

`ProductServiceIntegrationTest` initializes Elasticsearch 8.15.5 in a container, creates the index, indexes documents, and tests retrieval, text-based searching with filters, empty result sets, non-existent documents, and deletion. Testcontainers may need to download the image during the first execution.

---

## Decisions and Future Improvements

- The `Product` document includes fields (text/keyword/numeric/date) that are explicitly mapped.
- A limit of 100 items is applied to initial listings and searches to prevent excessively large responses.
- For production: configure Elasticsearch authentication/TLS, secret management, index aliases and versioning, migrations, observability, relevance/boosting, synonyms, autocomplete, load analysis, and a CI pipeline.
- The Elasticsearch image is pinned to version `8.15.5` to ensure reproducible tests; adjust the version according to your environment's update policy.

> Note: Both the application and the tests require access to Elasticsearch. The integration tests do not use mocks for Elasticsearch; Testcontainers spins up an actual container.