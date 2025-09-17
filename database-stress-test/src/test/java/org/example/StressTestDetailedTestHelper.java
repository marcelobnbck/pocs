package org.example;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.atomic.AtomicLong;

public class StressTestDetailedTestHelper {

    public static String captureOutput(Runnable runnable) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(baos));
        try {
            runnable.run();
        } finally {
            System.setOut(original);
        }
        return baos.toString();
    }

    public static void invokePrintOpMetrics(String op, AtomicLong count, AtomicLong time) {
        try {
            var method = StressTestDetailed.class
                    .getDeclaredMethod("printOpMetrics", String.class, AtomicLong.class, AtomicLong.class);
            method.setAccessible(true);
            method.invoke(null, op, count, time);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
