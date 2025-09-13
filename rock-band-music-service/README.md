# RockBand Music Service

RockBand Music Service is a Spring Boot application that manages rock bands and their albums. It provides RESTful endpoints with CRUD operations, integrates with an external lyrics API, leverages caching for performance, exposes repositories via Spring Data REST, and uses Testcontainers for reliable integration testing.

## Features

- CRUD operations for Bands and Albums via REST controllers
- Caching of frequently accessed data with Spring Cache (@Cacheable, @CacheEvict, etc.)
- External lyrics lookup using lyrics.ovh and RestTemplate
- Automatic repository exposure with Spring Data REST
- Bean validation on incoming payloads (@Valid, @NotBlank, etc.)
- Global exception handling with @ControllerAdvice
- Integration tests using Testcontainers (PostgreSQL)
- Simple in-memory cache configuration

## Prerequisites
- Java 17 or higher
- Maven 3.6+
- Docker (for Testcontainers)
- PostgreSQL (local or containerized instance)

## Getting Started
Clone the Repository
```bash
git clone https://github.com/marcelobnbck/rockband-music-service.git
cd rockband-music-service
```

## Configure the Application
Adjust `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/musicdb
spring.datasource.username=postgres
spring.datasource.password=secret
spring.jpa.hibernate.ddl-auto=update

# External Lyrics API
lyrics.api.url=https://api.lyrics.ovh/v1

# Cache type
spring.cache.type=simple
```

## Build and Run
Build the JAR:
```bash
mvn clean package
```

## Run the service:
```bash
java -jar target/rockband-music-service-1.0-SNAPSHOT.jar
```

The application will start on http://localhost:8080.

## Configuration
All configuration properties live in src/main/resources/application.properties. Key settings:
- spring.datasource.* — PostgreSQL connection
- lyrics.api.url — Base URL for the lyrics API
- spring.cache.type — Set to simple for in-memory caching
  You can also override properties via environment variables or command-line flags.

##  API Documentation
Base path: /api/bands 
- GET /api/bands (List): Retrieve all bands
- GET /api/bands/{id} (Detail): Retrieve a single band by ID
- POST /api/bands (Create): Add a new band
- PUT /api/bands/{id} (Update): Update an existing band
- DELETE /api/bands/{id} (Delete): Remove a band by ID
- GET /api/bands/{id}/lyrics (Lookup): Fetch lyrics for an album (via ?title=)

## Example Requests
List bands:
```bash
curl -X GET http://localhost:8080/api/bands
```

Create a band:
```bash
curl -X POST http://localhost:8080/api/bands \
-H "Content-Type: application/json" \
-d '{"name":"Nirvana","genre":"Grunge","formedIn":1987}'
```

Fetch lyrics:
```bash
curl -X GET "http://localhost:8080/api/bands/1/lyrics?title=Smells%20Like%20Teen%20Spirit"
```

## Testing
Run all tests (unit, repository, and integration with Testcontainers):
```bash
mvn test
```

- Repository tests use @DataJpaTest with a PostgreSQL container
- Web layer tests use @WebMvcTest and mocked services
- Integration tests annotated with @SpringBootTest and @Testcontainers
  Make sure Docker is running before executing integration tests.

## Built With
- Spring Boot 3.1.x
- Spring Data JPA & Spring Data REST
- Spring Cache
- RestTemplate (external API client)
- Maven for build and dependency management
- PostgreSQL as the primary database
- Testcontainers for containerized integration tests
- Java 17