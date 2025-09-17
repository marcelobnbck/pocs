package org.example;

import java.sql.*;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class StressTestDetailed {

    // Database connection settings
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "admin";

    // Stress test settings
    private static final int THREADS = 20;            // Number of concurrent threads
    private static final int TASKS_PER_THREAD = 1000; // Number of operations per thread

    // Workload ratios (must add up to 100)
    private static final int INSERT_RATIO = 40; // 40%
    private static final int SELECT_RATIO = 40; // 40%
    private static final int UPDATE_RATIO = 10; // 10%
    private static final int DELETE_RATIO = 10; // 10%

    // Metrics (global)
    private static final AtomicLong totalOps = new AtomicLong(0);
    private static final AtomicLong totalTimeNanos = new AtomicLong(0);
    private static final AtomicLong errors = new AtomicLong(0);

    // Per-operation metrics
    private static final AtomicLong insertOps = new AtomicLong(0);
    private static final AtomicLong insertTime = new AtomicLong(0);

    private static final AtomicLong selectOps = new AtomicLong(0);
    private static final AtomicLong selectTime = new AtomicLong(0);

    private static final AtomicLong updateOps = new AtomicLong(0);
    private static final AtomicLong updateTime = new AtomicLong(0);

    private static final AtomicLong deleteOps = new AtomicLong(0);
    private static final AtomicLong deleteTime = new AtomicLong(0);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(THREADS);

        long startTime = System.nanoTime();

        for (int i = 0; i < THREADS; i++) {
            executor.submit(new DatabaseWorker(i));
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.HOURS);

        long endTime = System.nanoTime();
        long durationNanos = endTime - startTime;

        printMetrics(durationNanos);
    }

    private static void printMetrics(long durationNanos) {
        long ops = totalOps.get();
        double totalSeconds = durationNanos / 1_000_000_000.0;
        double throughput = ops / totalSeconds;
        double avgLatencyMs = (totalTimeNanos.get() / 1_000_000.0) / ops;

        System.out.println("Stress Test Results -------------------------");
        System.out.println("Total operations: " + ops);
        System.out.printf("Total time: %.2f seconds%n", totalSeconds);
        System.out.printf("Throughput: %.2f ops/sec%n", throughput);
        System.out.printf("Average latency (overall): %.2f ms/op%n", avgLatencyMs);
        System.out.println("Errors: " + errors.get());

        System.out.println("\nPer Operation Metrics -------------------------");
        printOpMetrics("INSERT", insertOps, insertTime);
        printOpMetrics("SELECT", selectOps, selectTime);
        printOpMetrics("UPDATE", updateOps, updateTime);
        printOpMetrics("DELETE", deleteOps, deleteTime);
    }

    private static void printOpMetrics(String op, AtomicLong count, AtomicLong time) {
        long c = count.get();
        if (c == 0) {
            System.out.printf("%s: 0 ops%n", op);
            return;
        }
        double avgLatencyMs = (time.get() / 1_000_000.0) / c;
        System.out.printf("%s: %d ops | avg latency: %.2f ms%n", op, c, avgLatencyMs);
    }

    static class DatabaseWorker implements Runnable {
        private final int workerId;
        private final Random random = new Random();

        DatabaseWorker(int workerId) {
            this.workerId = workerId;
        }

        @Override
        public void run() {
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                connection.setAutoCommit(true);

                String insertSQL = "INSERT INTO stress_test_table (worker_id, value) VALUES (?, ?)";
                String selectSQL = "SELECT COUNT(*) FROM stress_test_table WHERE worker_id = ?";
                String updateSQL = "UPDATE stress_test_table SET value = ? WHERE worker_id = ? LIMIT 1";
                String deleteSQL = "DELETE FROM stress_test_table WHERE worker_id = ? LIMIT 1";

                try (PreparedStatement insertStmt = connection.prepareStatement(insertSQL);
                     PreparedStatement selectStmt = connection.prepareStatement(selectSQL);
                     PreparedStatement updateStmt = connection.prepareStatement(updateSQL);
                     PreparedStatement deleteStmt = connection.prepareStatement(deleteSQL)) {

                    for (int i = 0; i < TASKS_PER_THREAD; i++) {
                        int op = pickOperation();
                        long startOp = System.nanoTime();

                        try {
                            switch (op) {
                                case 1: // INSERT
                                    insertStmt.setInt(1, workerId);
                                    insertStmt.setInt(2, random.nextInt(1000));
                                    insertStmt.executeUpdate();
                                    insertOps.incrementAndGet();
                                    insertTime.addAndGet(System.nanoTime() - startOp);
                                    break;
                                case 2: // SELECT
                                    selectStmt.setInt(1, workerId);
                                    try (ResultSet rs = selectStmt.executeQuery()) {
                                        while (rs.next()) {
                                            rs.getInt(1);
                                        }
                                    }
                                    selectOps.incrementAndGet();
                                    selectTime.addAndGet(System.nanoTime() - startOp);
                                    break;
                                case 3: // UPDATE
                                    updateStmt.setInt(1, random.nextInt(1000));
                                    updateStmt.setInt(2, workerId);
                                    updateStmt.executeUpdate();
                                    updateOps.incrementAndGet();
                                    updateTime.addAndGet(System.nanoTime() - startOp);
                                    break;
                                case 4: // DELETE
                                    deleteStmt.setInt(1, workerId);
                                    deleteStmt.executeUpdate();
                                    deleteOps.incrementAndGet();
                                    deleteTime.addAndGet(System.nanoTime() - startOp);
                                    break;
                            }
                            totalOps.incrementAndGet();
                        } catch (SQLException e) {
                            errors.incrementAndGet();
                        } finally {
                            totalTimeNanos.addAndGet(System.nanoTime() - startOp);
                        }

                        if (i % 200 == 0) {
                            System.out.printf("Worker %d processed %d operations%n", workerId, i);
                        }
                    }
                }
            } catch (SQLException e) {
                System.err.printf("Worker %d failed: %s%n", workerId, e.getMessage());
                errors.incrementAndGet();
            }
        }

        private int pickOperation() {
            int roll = random.nextInt(100) + 1;
            if (roll <= INSERT_RATIO) return 1; // Insert
            if (roll <= INSERT_RATIO + SELECT_RATIO) return 2; // Select
            if (roll <= INSERT_RATIO + SELECT_RATIO + UPDATE_RATIO) return 3; // Update
            return 4; // Delete
        }
    }
}
