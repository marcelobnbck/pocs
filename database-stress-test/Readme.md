# PostgreSQL Stress Test (Java)

This project is a **Java-based stress testing tool for PostgreSQL**.  
It allows you to simulate high-concurrency workloads (inserts, selects, updates, deletes) against a PostgreSQL database, and collects performance metrics such as throughput, latency, and per-operation statistics.

---

## Features

- Multi-threaded stress test using **JDBC** and **ExecutorService**.
- Configurable workload ratios:
    - **INSERT**
    - **SELECT**
    - **UPDATE**
    - **DELETE**
- Global metrics:
    - Total operations
    - Throughput (ops/sec)
    - Average latency
    - Error count
- Per-operation metrics:
    - Count of operations
    - Average latency per operation
- Unit tests for workload distribution and metrics logic (using JUnit 5).

---

## 📂 Project Structure

```yaml
src/
└── main/java/org/example/
├── StressTestDetailed.java # Main stress test runner 
└── test/java/org/example/
├── StressTestDetailedTest.java # Unit tests
├── StressTestDetailedTestHelper.java # Test helper for metrics printing
```

---

## Prerequisites

- Java 17+ (or compatible version)
- PostgreSQL running locally or accessible remotely
- PostgreSQL JDBC driver (e.g., postgresql-42.7.x.jar)

---

## Setup

1. **Create a test database and table** in PostgreSQL:

```sql
CREATE DATABASE testdb;

CREATE TABLE stress_test_table (
    id SERIAL PRIMARY KEY,
    worker_id INT,
    value INT
);
```

Update database credentials in `PostgresStressTestDetailed.java`:

```java
private static final String DB_URL = "jdbc:postgresql://localhost:5432/testdb";
private static final String DB_USER = "postgres";
private static final String DB_PASSWORD = "password";
```

Compile the project:

```bash
javac -cp postgresql-42.7.x.jar:. org/example/StressTestDetailed.java
```

Run the stress test:

```bash
java -cp postgresql-42.7.x.jar:. org.example.stresstest.PostgresStressTestDetailed
```

## Example Output
```yaml
Worker 3 processed 200 operations
Worker 7 processed 200 operations
...
Stress Test Results =====
Total operations: 20000
Total time: 6.12 seconds
Throughput: 3267.97 ops/sec
Average latency (overall): 0.30 ms/op
Errors: 0

Per Operation Metrics ---
INSERT: 7980 ops | avg latency: 0.35 ms
SELECT: 7984 ops | avg latency: 0.28 ms
UPDATE: 2048 ops | avg latency: 0.42 ms
DELETE: 1988 ops | avg latency: 0.47 ms
```

## Running Tests
Unit tests are written with JUnit 5.

To run:

```bash
mvn test
```

Tests include:

- Workload distribution correctness (pickOperation).
- Metrics increment and latency calculation.
- Output printing safety.