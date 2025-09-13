# Annotation Demo Project
This is a Java Spring Boot application that demonstrates the main Java annotations using:

- `@SpringBootApplication`

- `@Entity`, `@Id`,

- `@RestController`, `@GetMapping`, `@RequestParam`,

- `@Service`, `@Autowired`,

- `@Configuration`, `@Bean`.

The application exposes a REST API endpoint to greet a user and uses an H2 in-memory database with JPA setup.

## Requirements
- Java 21 (Amazon Corretto 21.0.8+)
- Maven 3.9.11+
- IntelliJ IDEA (recommended)

## How to Run
### 1. Clone the Repository

```bash
git clone https://github.com/marcelobnbck/java-main-annotations.git
cd java-main-annotations
```

### 2. Build the Project
```bash
mvn clean install
```

### 3. Run the Application
```bash
mvn spring-boot:run
```

### 4. Access the Application

- Open your browser:
```bash
http://localhost:8080/api/greet?name=YourName
```

- You should receive:

```json
Hello, YourName!
```

## Test the API (Optional)
You can also use curl:
```bash
curl "http://localhost:8080/api/greet?name=JonDoe"
```

## H2 Console
To access the in-memory database:

URL: `http://localhost:8080/h2-console`

JDBC URL: `jdbc:h2:mem:testdb`

User: `sa`

Password: `(leave blank)`