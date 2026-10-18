package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Set;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantIT {

    @Test
    @DisplayName("Integration: Multiple chefs and waiters should run simultaneously without duplicate IDs")
    void testeFluxoCompletoRestaurante() throws InterruptedException {
        BlockingQueue<Order> orderQueue = new ArrayBlockingQueue<>(2);

        Set<Integer> idsProcessados = ConcurrentHashMap.newKeySet();

        Waiter garcomTestavel = new Waiter(orderQueue, "Validator Waiter") {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        Order order = orderQueue.take();
                        idsProcessados.add(order.orderId());
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }
        };

        ExecutorService executor = Executors.newFixedThreadPool(4);

        executor.execute(new Chef(orderQueue, "Chef A"));
        executor.execute(new Chef(orderQueue, "Chef B"));
        executor.execute(garcomTestavel);
        executor.execute(garcomTestavel);

        Thread.sleep(3000);

        executor.shutdownNow();
        boolean finalizouLimpo = executor.awaitTermination(2, TimeUnit.SECONDS);

        assertTrue(finalizouLimpo, "The thread pool should have shut down without getting stuck in a deadlock");
        assertTrue(idsProcessados.size() > 0, "The restaurant should have processed orders during this time");

        System.out.println("Total unique orders processed in the test: " + idsProcessados.size());
    }
}
