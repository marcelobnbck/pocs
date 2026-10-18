package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {

    private BlockingQueue<Order> orderQueue;

    @BeforeEach
    void setUp() {
        orderQueue = new ArrayBlockingQueue<>(5);
    }

    @Test
    @DisplayName("Chef must successfully produce an order and place it in the queue")
    void chefDeveColocarPedidoNaFila() throws InterruptedException {
        Chef chef = new Chef(orderQueue, "Chef Test");
        Thread chefThread = new Thread(chef);

        chefThread.start();

        Order order = orderQueue.poll(2, TimeUnit.SECONDS);

        assertNotNull(order, "The chef should have placed an order in the queue");
        assertTrue(order.orderId() > 0, "The order ID must be greater than zero");
        assertNotNull(order.dish(), "The dish cannot be null");

        chefThread.interrupt();
        chefThread.join(1000);
    }

    @Test
    @DisplayName("Waiter must successfully consume an order from the queue")
    void waiterDeveConsumirPedidoDaFila() throws InterruptedException {
        Waiter waiter = new Waiter(orderQueue, "Waiter Test");
        Thread waiterThread = new Thread(waiter);

        Order mockOrder = new Order(99, "Pizza");
        orderQueue.put(mockOrder);

        waiterThread.start();

        boolean esvaziou = esperaFilaEsvaziar(orderQueue, 2);

        assertTrue(esvaziou, "The waiter should have removed the order from the queue");

        waiterThread.interrupt();
        waiterThread.join(1000);
    }

    @Test
    @DisplayName("Threads should stop gracefully when interrupted")
    void threadsDevemPararQuandoInterrompidas() throws InterruptedException {
        Thread chefThread = new Thread(new Chef(orderQueue, "Idle Chef"));

        chefThread.start();
        Thread.sleep(200);

        chefThread.interrupt();

        chefThread.join(1000);
        assertFalse(chefThread.isAlive(), "The Chef's thread should have been closed");
    }

    private boolean esperaFilaEsvaziar(BlockingQueue<?> queue, int timeoutSegundos) throws InterruptedException {
        long limite = System.currentTimeMillis() + (timeoutSegundos * 1000L);
        while (!queue.isEmpty()) {
            if (System.currentTimeMillis() > limite) {
                return false;
            }
            Thread.sleep(50);
        }
        return true;
    }
}
