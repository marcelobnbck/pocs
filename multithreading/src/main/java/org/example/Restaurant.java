package org.example;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

record Order(int orderId, String dish) {
    @Override
    public String toString() {
        return "Order #" + orderId + " - " + dish;
    }
}

class Chef implements Runnable {
    private final BlockingQueue<Order> orderQueue;
    private final String name;
    private static final AtomicInteger orderCounter = new AtomicInteger(1);

    public Chef(BlockingQueue<Order> orderQueue, String name) {
        this.orderQueue = orderQueue;
        this.name = name;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                String dish = pickRandomDish();
                Order order = new Order(orderCounter.getAndIncrement(), dish);

                System.out.println(name + " is preparing: " + order);
                Thread.sleep(700);

                orderQueue.put(order);
                System.out.println(name + " placed it on the counter: " + order);

            } catch (InterruptedException e) {
                break;
            }
        }
        System.out.println(name + " cleaned his workstation and left!");
    }

    private String pickRandomDish() {
        String[] dishes = {"Pasta", "Burger", "Sushi", "Tacos", "Steak"};
        return dishes[(int) (Math.random() * dishes.length)];
    }
}

class Waiter implements Runnable {
    private final BlockingQueue<Order> orderQueue;
    private final String name;

    public Waiter(BlockingQueue<Order> orderQueue, String name) {
        this.orderQueue = orderQueue;
        this.name = name;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Order order = orderQueue.take();
                System.out.println(name + " took it to serve: " + order);

                Thread.sleep(1200);
                System.out.println("[SUCCESS] " + name + " delivered " + order);

            } catch (InterruptedException e) {
                break;
            }
        }
        System.out.println(name + " finished their shift!");
    }
}

public class Restaurant {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Order> orderQueue = new ArrayBlockingQueue<>(3);

        ExecutorService executor = Executors.newFixedThreadPool(4);

        System.out.println("--- Restaurant Open ---");
        executor.execute(new Chef(orderQueue, "Chef Miguel"));
        executor.execute(new Chef(orderQueue, "Chef Matheus"));
        executor.execute(new Waiter(orderQueue, "Waiter Marcelo"));
        executor.execute(new Waiter(orderQueue, "Waitress Mariana"));

        Thread.sleep(5000);

        System.out.println("\n--- Closing the Restaurant (Interrupting threads)... ---");
        executor.shutdownNow();

        if (executor.awaitTermination(2, TimeUnit.SECONDS)) {
            System.out.println("Restaurant securely closed!");
        }
    }
}
