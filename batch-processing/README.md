# Simple Batch Processing in Java

This project demonstrates a **simple batch processing** approach in Java — reading a CSV file, processing its contents, and writing the results to a new file.

It also includes **JUnit 5 tests** to validate the batch process.

## Features
- Reads an input CSV file.
- Processes data in batches (here, converts names to uppercase).
- Writes processed data to an output CSV file.
- JUnit tests for verification.
- Minimal dependencies — only Java and JUnit.

## Technologies
- **Java 21 (Coretto 21.0.8)**
- **JUnit 5** for testing
- **Maven 3.9.11** for build and dependency management

## Project Structure

- batch-processing/
  - src/
    - main / java / BatchProcessing.java
    - test / java / BatchProcessingTest.java
  - pom.xml
  - input_file.csv (example input file)
  - README.md

## Running the Application

### 1. Compile and Run with Maven
```bash
mvn clean compile exec:java \
    -Dexec.mainClass=BatchProcessing \
    -Dexec.args="input_file.csv output_file.csv"
```

### 2. Example Input (input_file.csv)
```csv
firstName,lastName
Joao,Silva
Maria,Lima
Alex,Oliveira
```

### 3. Example Output (output_file.csv)
```csv
firstName,lastName
JOAO,SILVA
MARIA,LIMA
ALEX,OLIVEIRA
```

## Running Tests
```bash
mvn clean test
```

### The test verifies that:
- The output file exists.
- The first line is the header.
- All names are uppercase.