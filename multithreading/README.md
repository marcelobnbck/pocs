# Concurrency Restaurant Simulator

This project is a simplified simulation of a restaurant ecosystem using **multithreading** in Java. Its primary goal is to provide a practical demonstration of how to resolve classic concurrency issues, such as *race conditions* and *backpressure*.

---

## The Problem
In asynchronous systems, multiple threads of execution must share common resources. In the restaurant scenario, if two **chefs** attempt to register an order simultaneously using shared variables, it could result in duplicate IDs (*race condition*). Furthermore, if the chefs cook faster than the **waiters** can serve, the system could run out of memory unless a physical limit is imposed on the serving counter (*backpressure*).

---

## Architecture and Workflow
The project employs the **producer-consumer** architectural pattern:

1. **Chefs (Producers):** Generate orders asynchronously and attempt to place them on the serving counter.
2. **Serving Counter (Blocking Queue):** Acts as a bounded buffer for communication between threads.
3. **Waiters (Consumers):** Retrieve orders from the counter and deliver them.

---

## Technologies
* **Java 17+** (Utilizing `record` for clean data structures)
* **Java Concurrency Utilities** (`ExecutorService`, `BlockingQueue`, `AtomicInteger`)
* **JUnit 5** (For unit and asynchronous integration testing)

---

## Technical Decisions
* **`AtomicInteger` for Unique IDs:** Replaces the standard `static int++` operator. It ensures atomic operations at the hardware level, eliminating *race conditions* when assigning order numbers.
* **`ArrayBlockingQueue(3)` for Backpressure:** The queue has a fixed capacity, effectively limiting the number of items that can be placed on the serving counter. If full, the Chef thread blocks voluntarily (`queue.put()`) without consuming CPU, waiting for the Waiter to free up space. If empty, the Waiter thread sleeps (`queue.take()`).
* **`ExecutorService` (Thread Pool):** Manages and limits the number of active workers, preventing the uncontrolled and costly creation of new `new Thread()` instances.
* **Control via `.isInterrupted()`:** Prevents destructive infinite loops (`while(true)`). It allows for a "graceful shutdown" of the restaurant, ensuring resources are cleaned up upon receiving a stop signal.

---

## How to Run

To run the main restaurant simulation:

```bash
# Compile the project
javac -d bin src/org/example/*.java

# Run the main class
java -cp bin org.example.Restaurant
```

---

## How to Test

The tests validate isolated asynchronous scenarios as well as end-to-end concurrent behavior.

```bash
# Run all tests via Maven (if applicable)
mvn test
```

---

### Scenarios Covered:
* **Unit Tests:** Isolated validation that the Chef correctly inserts data into the queue, that the Waiter consumes from populated queues, and that both threads respond promptly to an interruption signal.
* **Integration Test:** Runs the full restaurant with multiple workers in parallel, using a `ConcurrentHashMap.newKeySet()` to mathematically guarantee that **no order ID is duplicated** during concurrent processing.