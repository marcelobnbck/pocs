# Java Date Conversion Utility

## Overview

This project provides a comprehensive utility for working with dates in Java using the modern `java.time` API. It includes:

- Parsing and formatting dates
- Converting between legacy and modern date types
- Handling time zones
- Unit tests for all core functionalities

## Features

-  Parse date strings to `LocalDate`
-  Convert between `LocalDate`, `LocalDateTime`, `Instant`, and `Date`
-  Format dates using custom patterns
-  Handle time zones with `ZonedDateTime`
-  Unit tests using JUnit 5

## Requirements

- Java 8 or higher
- Maven or Gradle (optional for dependency management)
- JUnit 5 (for testing)

### Maven Dependency for JUnit 5

```xml
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.9.3</version>
    <scope>test</scope>
</dependency>
```

## Usage
Run the Main Class
```bash
javac DateConversionExample.java
java DateConversionExample
```

## Running Tests
Using Maven
```bash
mvn test
```
Using IDE
- Right-click on `DateConversionExampleTest.java`
- Select **Run Tests**

