# Music Converter (Java 17)
A simple example project demonstrating how to use a **generic Converter interface** in Java 17 with a **music genres** domain.

## Features
- Generic `Converter<T, R>` interface
- `andThen()` and `compose()` for chaining converters
- Converts between `String`, `MusicGenre`, and `GenreInfo`
- Includes unit tests using **JUnit 5**

## Build, Package & Run

Build project:
```bash
mvn clean compile
```

Package and run (no external deps):
```bash
mvn -DskipTests package
java -cp target/classes org.example.musicconverter.Main
```

## Run Tests
```bash
mvn test
```