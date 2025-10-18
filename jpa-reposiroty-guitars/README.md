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

## Tests

Run:
```
mvn test
```

## Project structure

Standard Maven layout with `src/main/java`, `src/test/java`. Uses H2 by default.