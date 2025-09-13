package org.example;

import java.util.concurrent.*;

record Order(int orderId, String dish) {

    @Override
    public String toString() {
        return "Order #" + orderId + " - " + dish;
    }
}

// Producer: Chefs preparing orders
class Chef implements Runnable {
    private final BlockingQueue<Order> queue;
    private final String name;
    private static int orderCounter = 1;

    public Chef(BlockingQueue<Order> queue, String name) {
        this.queue = queue;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String dish = pickRandomDish();
                Order order = new Order(orderCounter++, dish);
                System.out.println(name + " prepared " + order);
                queue.put(order); // Add order to the queue
                Thread.sleep(700); // simulate prep time
            }
        } catch (InterruptedException e) {
            System.out.println(name + " has finished cooking for the day!");
        }
    }

    private String pickRandomDish() {
        String[] dishes = {"Pasta", "Burger", "Sushi", "Tacos", "Steak"};
        return dishes[(int) (Math.random() * dishes.length)];
    }
}

// Consumer: Waiters serving orders
class Waiter implements Runnable {
    private final BlockingQueue<Order> queue;
    private final String name;

    public Waiter(BlockingQueue<Order> queue, String name) {
        this.queue = queue;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Order order = queue.take(); // Wait for order
                System.out.println(name + " served " + order);
                Thread.sleep(500); // simulate serving time
            }
        } catch (InterruptedException e) {
            System.out.println(name + " has ended their shift!");
        }
    }
}

public class Restaurant {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Order> orderQueue = new ArrayBlockingQueue<>(10);
        ExecutorService executor = Executors.newCachedThreadPool();

        // Start chefs
        executor.execute(new Chef(orderQueue, "Chef Miguel"));
        executor.execute(new Chef(orderQueue, "Chef Matheus"));

        // Start waiters
        executor.execute(new Waiter(orderQueue, "Waiter Marcelo"));
        executor.execute(new Waiter(orderQueue, "Waiter Mariana"));

        // Simulation run for 5 seconds
        Thread.sleep(5000);

        // Shut down
        executor.shutdownNow();
        executor.awaitTermination(2, TimeUnit.SECONDS);

        System.out.println("Restaurant is now closed!");
    }
}