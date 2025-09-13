package org.example;

import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrencyTiming {

    private static final int NUM_TASKS = 20;
    private static final int THREAD_POOL_SIZE = 4;

    private final Map<String, Integer> counters = new ConcurrentHashMap<>();
    private final Map<String, Integer> threadTaskCount = new ConcurrentHashMap<>();
    private final Map<Integer, Long> taskDurations = new ConcurrentHashMap<>();
    private final Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        new ConcurrencyTiming().runDemo();
    }

    private void runDemo() {
        long startTime = System.nanoTime();

        ExecutorService executor = new ThreadPoolExecutor(
                THREAD_POOL_SIZE,
                THREAD_POOL_SIZE,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>()
        );

        for (int i = 0; i < NUM_TASKS; i++) {
            final int taskId = i;
            executor.submit(() -> processTask(taskId, "Key" + (taskId % 5)));
        }

        executor.shutdown();
        try {
            if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }

        long endTime = System.nanoTime();
        double elapsedMillis = (endTime - startTime) / 1_000_000.0;

        printResults(elapsedMillis);
    }

    private void processTask(int taskId, String key) {
        String threadName = Thread.currentThread().getName();

        long taskStart = System.nanoTime();

        // Track thread usage
        threadTaskCount.merge(threadName, 1, Integer::sum);

        // Initialize key in a synchronized block
        synchronized (this) {
            counters.putIfAbsent(key, 0);
        }

        // Lock-protected increment
        lock.lock();
        try {
            int oldValue = counters.get(key);
            int newValue = oldValue + 1;
            counters.put(key, newValue);
            System.out.println(threadName + " incremented " + key + " to " + newValue);
        } finally {
            lock.unlock();
        }

        // ConcurrentHashMap safe increment without external locking
        counters.computeIfPresent(key, (k, v) -> v + 1);

        long taskEnd = System.nanoTime();
        taskDurations.put(taskId, taskEnd - taskStart);
    }

    private void printResults(double totalMillis) {
        System.out.println("\nFinal Counter Values:");
        counters.forEach((key, value) -> System.out.println(key + ": " + value));

        System.out.println("\nThread Usage Stats:");
        threadTaskCount.forEach((thread, count) ->
                System.out.println(thread + " processed " + count + " tasks"));

        System.out.println("\nTask Timing Stats(ms):");
        double min = taskDurations.values().stream().mapToLong(Long::longValue)
                .min().orElse(0) / 1_000_000.0;
        double max = taskDurations.values().stream().mapToLong(Long::longValue)
                .max().orElse(0) / 1_000_000.0;
        double avg = taskDurations.values().stream().mapToLong(Long::longValue)
                .average().orElse(0) / 1_000_000.0;

        System.out.printf("Min: %.3f ms, Max: %.3f ms, Avg: %.3f ms%n", min, max, avg);
        System.out.printf("\nTotal execution time: %.3f ms%n", totalMillis);
    }
}
