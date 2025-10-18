# Guitar Models (Spring Boot + JPA)

Minimal Spring Boot (Java 17) example demonstrating:
- A `GuitarModel` JPA entity
- A `GuitarModelRepository` using Spring Data JPA
- Service and REST controller
- Unit tests (repository and service)
- H2 in-memory database for local development/tests

## Build & Run

Requirements: Java 17, Maven

Build:
```
mvn -B package
```

Run:
```
mvn spring-boot:run
```

API endpoints (running at http://localhost:8080):
- `GET /api/guitars` - list guitars
- `POST /api/guitars` - create guitar (JSON body)
```json
{
  "brand": "Fender",
  "model": "Stratocaster",
  "type": "electric",
  "strings": 6
}
```

## H2 console:
if running: http://localhost:8080/h2-console
- JDBC URL: jdbc:h2:mem:guitars
- Username: Configured in application.properties file;
- Password: Configured in application.properties file.

## Tests

Run:
```
mvn test
```

