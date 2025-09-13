# WebClient User Service

A sample Java project demonstrating how to fetch user data using Spring's `WebClient` and test HTTP interactions with `MockWebServer`.

## Features

- Fetch user information from a REST API
- Reactive programming with Project Reactor
- Unit tests using JUnit 5 and MockWebServer

## Technologies

- Java
- Maven
- Spring WebFlux (`WebClient`)
- JUnit 5
- OkHttp MockWebServer

## Getting Started

### Prerequisites

- Java 17+
- Maven 3.8+

### Build

```sh
mvn clean install
```

### Run Tests
```sh
mvn test
```

### Project Structure
- `src/main/java/com/example/webclient/model/User.java` - User model 
- `src/main/java/com/example/webclient/client/UserWebClient.java` - WebClient client
- `src/test/java/com/example/webclient/client/UserWebClientTest.java` - Unit tests