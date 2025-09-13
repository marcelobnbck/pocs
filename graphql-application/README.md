# GraphQL with Spring Boot 3 + Java 21

This is a simple GraphQL API built with **Spring Boot 3.3**, **Java 21 (Corretto 21.0.8)**, and **Maven 3.9.11**.

The API exposes two GraphQL queries:

- `hello`: returns a static greeting.
- `user(id: ID!)`: returns a mocked user by ID.

---

## GraphQL Schema

**Location:** `src/main/resources/graphql/schema.graphqls`

```graphql
type Query {
  hello: String
  user(id: ID!): User
}

type User {
  id: ID!
  name: String
  email: String
}
```

---

## How to Run
**Prerequisites:**
- Java 21 (e.g. Amazon Corretto 21.0.8)
- Maven 3.9.11+

**Run the app:**
`mvn clean spring-boot:run`

The application will start on port 8080.

---

## Test the API
**Endpoint:**

`POST http://localhost:8080/graphql`

**Example Query:** `hello`
```graphql
query {
  hello
}
```
**Example Query:** `user`
```graphql
query {
  user(id: "123") {
    id
    name
    email
  }
}
```

---

## Technologies Used
- Java 21 (Amazon Corretto)
- Spring Boot 3.3
- Spring GraphQL
- GraphQL Java 21.3
- Maven 3.9