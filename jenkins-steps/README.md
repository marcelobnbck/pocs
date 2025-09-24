# Jenkins Java Steps

This is a simple Java project built with **Maven** that demonstrates how to run a CI/CD pipeline using **Jenkins**. The pipeline includes compilation, unit tests, code coverage, and packaging.

## Project Structure
```markdown
jenkins-java-demo/
├── src/
│ ├── main/java/org/example/App.java      # Simple Java application
│ └── test/java/org/example/AppTest.java  # Unit tests (JUnit 5)
├── pom.xml                               # Maven configuration
└── Jenkinsfile                           # Jenkins pipeline definition
```

## Features
- **Java 17**
- **Maven Build**
- **JUnit 5 Tests**
- **JaCoCo Code Coverage**
- **Jenkins Pipeline (Declarative)**

## Running Locally
1. Clone the repository:
```bash
git clone https://github.com/your-username/jenkins-java-demo.git
cd jenkins-java-demo
```

Build and test with Maven:

```bash
mvn clean install
```

Run the application:
```bash
java -cp target/jenkins-java-demo-1.0-SNAPSHOT.jar org.example.App
```

## Code Coverage (JaCoCo)
After running tests, JaCoCo will generate a coverage report:

```bash
target/site/jacoco/index.html
```
Open it in your browser to check line and branch coverage.

## Jenkins Pipeline
The `Jenkinsfile` defines the following stages:

1. **Checkout** – Pulls the code from GitHub
2. **Build** – Compiles the Java source code
3. **Test** – Runs unit tests with JUnit 5
4. **Code Coverage** – Generates JaCoCo coverage reports
5. **Package** – Builds the JAR artifact

## Example Jenkins Pipeline UI

- Build Status
- Test Results (JUnit)
- Code Coverage (JaCoCo)

## Setup in Jenkins
1. Install the following tools in Jenkins:
   - **JDK 17**
   - **Maven 3.9+**
   - **JaCoCo Plugin**
2. Create a new Pipeline Job in Jenkins.
3. Point it to this repository.
4. Run the job – Jenkins will execute the pipeline from the `Jenkinsfile`.