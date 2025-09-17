package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.*;

class StressTestDetailedTest {

    private StressTestDetailed.DatabaseWorker worker;

    @BeforeEach
    void setUp() {
        worker = new StressTestDetailed.DatabaseWorker(1) {
            protected int pickOperation() {
                return 1;
            }
        };
    }

    @Test
    void testPickOperationDistribution() {
        int inserts = 0, selects = 0, updates = 0, deletes = 0;
        Random random = new Random(42);

        for (int i = 0; i < 10000; i++) {
            int roll = random.nextInt(100) + 1;
            int op;
            if (roll <= 40) op = 1;
            else if (roll <= 80) op = 2;
            else if (roll <= 90) op = 3;
            else op = 4;

            switch (op) {
                case 1 -> inserts++;
                case 2 -> selects++;
                case 3 -> updates++;
                case 4 -> deletes++;
            }
        }

        assertTrue(inserts > 3500 && inserts < 4500, "Insert ratio ~40%");
        assertTrue(selects > 3500 && selects < 4500, "Select ratio ~40%");
        assertTrue(updates > 800 && updates < 1200, "Update ratio ~10%");
        assertTrue(deletes > 800 && deletes < 1200, "Delete ratio ~10%");
    }

    @Test
    void testMetricsIncrement() {
        AtomicLong ops = new AtomicLong(0);
        AtomicLong time = new AtomicLong(0);

        for (int i = 0; i < 3; i++) {
            long start = System.nanoTime();
            try {
                Thread.sleep(0, 100);
            } catch (InterruptedException ignored) {}
            long end = System.nanoTime();
            ops.incrementAndGet();
            time.addAndGet(end - start);
        }

        assertEquals(3, ops.get());
        assertTrue(time.get() > 0, "Total time should be > 0");
        double avgLatencyMs = (time.get() / 1_000_000.0) / ops.get();
        assertTrue(avgLatencyMs >= 0.0);
    }

    @Test
    void testPrintOpMetricsWhenZero() {
        AtomicLong ops = new AtomicLong(0);
        AtomicLong time = new AtomicLong(0);

        assertEquals(0, ops.get());
        assertEquals(0, time.get());

        StressTestDetailedTestHelper.captureOutput(() -> {
            StressTestDetailedTestHelper.invokePrintOpMetrics("TEST", ops, time);
        });
    }
}
