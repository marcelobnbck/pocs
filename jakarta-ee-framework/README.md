# 📘 Jakarta EE Project
This is a **Jakarta EE 10 application** showcasing the main features of the framework:
- **Jakarta RESTful Web Services (JAX-RS)** → Build REST APIs
- **Jakarta Persistence (JPA)** → Database entities with ORM
- **Jakarta CDI** → Context and Dependency Injection
- **Jakarta EJB** → Stateless session beans for business logic
- **Jakarta JSON-B** → JSON serialization/deserialization
- **Jakarta Bean Validation** → Data validation on entities

# 🚀 Features
- `Book` entity managed via JPA
- `BookRepository` with CDI + EntityManager
- `BookService` stateless EJB containing business logic
- `BookResource` JAX-RS REST API with JSON-B
- Automatic request validation using Bean Validation

# 📂 Project Structure
```bash
src/main/java/org/example/
├── model/Book.java           # Entity with validation
├── repository/BookRepository.java
├── service/BookService.java
├── rest/BookResource.java    # REST endpoints
├── rest/RestApplication.java # JAX-RS activation
└── Main.java                 # Entry point (for embedded runtimes)

src/main/resources/META-INF/persistence.xml  # JPA config
pom.xml                                      # Maven build file
```

# 🛠 Requirements
- **Java 17+**
- **Maven 3.8+**
- Jakarta EE compatible server (e.g., **Payara Micro 6**, **WildFly 27+**, or **OpenLiberty 23+**)

# ⚡ Build & Run

## 2. Package the application
```bash
mvn clean package
```

This generates a WAR file at:
```bash
target/jakartaee-demo.war
```

## 2. Run with Payara Micro (recommended)
Download Payara Micro (https://www.payara.fish/downloads/payara-platform-community-edition/) and run:
```bash
java -jar payara-micro-6.2024.9.jar --deploy target/jakartaee-demo.war
```

By default, the application runs at:
```bash
http://localhost:8080/jakartaee-demo
```

## 📡 REST Endpoints

### ➕ Create a book
```bash
curl -X POST http://localhost:8080/jakartaee-demo/api/books \
-H "Content-Type: application/json" \
-d '{"title":"Jakarta EE Guide","author":"Marcelo"}'
``` 

### 📖 Get all books
```bash
curl http://localhost:8080/jakartaee-demo/api/books
```

### 🔍 Get a book by ID
```bash
curl http://localhost:8080/jakartaee-demo/api/books/1
```

## 🧩 Technologies
- Jakarta EE 10
- JAX-RS, CDI, EJB, JPA, JSON-B, Bean Validation
- H2 in-memory database (for testing)
- Maven

## 📝 Notes
- The persistence unit (`persistence.xml`) is configured with **H2 in-memory DB**.
- On server restart, data is cleared (DB in-memory mode).
- To use MySQL, PostgreSQL, or another DB, just update `persistence.xml`.
