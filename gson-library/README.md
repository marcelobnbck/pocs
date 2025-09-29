# Google Gson

This project demonstrates how to use [Google Gson](https://github.com/google/gson) in Java for advanced JSON serialization and deserialization.  
It includes nested objects, custom type adapters, annotations for field control, and unit tests with JUnit 5.

---

## Project Structure

```markdown
gson-library/
├── pom.xml
└── src
├── main
│ └── java
│ └── org
│ └── example
│ ├── GsonLibrary.java              # Main demo class
│ ├── Employee.java                 # Employee entity (with annotations)
│ ├── Address.java                  # Nested object
│ └── LocalDateAdapter.java         # Custom serializer/deserializer
└── test
└── java
└── org
└── example
└── GsonLibraryTest.java                   # Unit tests for Gson
```

---

## Features

- **Serialization & Deserialization**
    - Convert Java objects to JSON and back.
- **Nested Objects**
    - Example: `Employee` contains an `Address`.
- **Annotations**
    - `@SerializedName` → custom JSON keys.
    - `@Expose` → selective field inclusion.
    - `transient` → ignore sensitive fields.
- **Custom Type Adapters**
    - `LocalDateAdapter` for handling `LocalDate`.
- **Unit Tests**
    - Validate serialization and deserialization using **JUnit 5**.

---

## Requirements

- Java 17+
- Maven 3+

---

## Dependencies

Main dependencies in `pom.xml`:

```xml
<dependencies>
    <!-- Gson -->
    <dependency>
        <groupId>com.google.code.gson</groupId>
        <artifactId>gson</artifactId>
        <version>2.11.0</version>
    </dependency>

    <!-- JUnit 5 -->
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter</artifactId>
        <version>5.10.2</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

---

## Running the Project

To run the demo:

```bash
mvn clean compile exec:java -Dexec.mainClass="org.example.GsonLibrary"
```

This will:

1. Serialize an `Employee` object into JSON.
2. Deserialize JSON back into an `Employee`.

---

## Running Tests

```bash
mvn clean test
```

The tests in GsonLibraryTest.java verify:
- Serialization excludes sensitive fields (salary).
- Deserialization correctly maps JSON to Java objects (including LocalDate).