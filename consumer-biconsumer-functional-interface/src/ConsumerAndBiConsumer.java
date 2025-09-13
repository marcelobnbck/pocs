import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ConsumerAndBiConsumer {

    public static void main(String[] args) {

        List<Product> products = new ArrayList<>(Arrays.asList(
                new Product("Laptop", 1200.00, "Electronics", true),
                new Product("Mouse", 25.50, "Electronics", true),
                new Product("Keyboard", 75.00, "Electronics", false), // Out of stock
                new Product("Book", 15.75, "Books", true),
                new Product("Coffee Maker", 89.99, "Home Appliances", false) // Out of stock
        ));

        System.out.println("\nOriginal List of Products:");
        products.forEach(System.out::println);

        // Consumer<T>

        // 1. Basic Consumer: Print product details
        Consumer<Product> printProductDetails = product ->
                System.out.println("  - "
                        + product.getName()
                        + " costs $"
                        + String.format("%.2f", product.getPrice()));
        printProductDetails.accept(products.get(0)); // Print Laptop details
        printProductDetails.accept(products.get(3)); // Print Book details

        // 2. Basic Consumer: Update product price (side effect)
        Consumer<Product> increasePriceBy10Percent = product -> {
            double newPrice = product.getPrice() * 1.10;
            product.setPrice(newPrice); // Modifies the object's state
            System.out.println("  Price of "
                    + product.getName()
                    + " increased to $"
                    + String.format("%.2f", newPrice));
        };
        increasePriceBy10Percent.accept(products.get(1)); // Increase Mouse price
        System.out.println("Mouse object after price increase: "
                + products.get(1)); // Verify change in original list

        // 3. Using Consumer with Collection API
        System.out.println("Applying price increase to all products (except Mouse, already done):");
        // Create a new consumer to apply to the rest
        Consumer<Product> increasePriceGeneral = product -> {
            product.setPrice(product.getPrice() * 1.05); // 5% increase
            System.out.println("  "
                    + product.getName()
                    + " new price: $" + String.format("%.2f", product.getPrice()));
        };
        products.forEach(increasePriceGeneral); // Applies the consumer to each element in the list
        System.out.println("Products after general price increase: " + products);

        // 4. Chaining Consumers:
        Consumer<Product> markAsUnavailable = product -> {
            product.setAvailable(false);
            System.out.println("  "
                    + product.getName() + " marked as UNAVAILABLE.");
        };
        Consumer<Product> logAndMarkUnavailable = printProductDetails.andThen(markAsUnavailable);

        System.out.println("Applying 'logAndMarkUnavailable' to Keyboard:");
        logAndMarkUnavailable.accept(products.get(2)); // Apply to Keyboard
        System.out.println("Keyboard object after chaining: "
                + products.get(2));

        // BiConsumer<T, U>

        // 1. Basic BiConsumer: Print key-value pair
        BiConsumer<String, Double> printKeyValuePair = (key, value) ->
                System.out.println("  Key: "
                        + key + ", Value: "
                        + String.format("%.2f", value));
        printKeyValuePair.accept("Temperature", 25.5);
        printKeyValuePair.accept("Humidity", 60.0);

        // 2. Basic BiConsumer: Update a product's price and availability
        BiConsumer<Product, Double> updatePriceAndAvailability = (product, newPrice) -> {
            product.setPrice(newPrice);
            product.setAvailable(true);
            System.out.println("  " + product.getName()
                    + " updated to $"
                    + String.format("%.2f", newPrice)
                    + " and now available.");
        };
        updatePriceAndAvailability.accept(products.get(4), 95.00); // Update Coffee Maker
        System.out.println("Coffee Maker object after update: "
                + products.get(4));

        // 3. Using BiConsumer with Collection API
        Map<String, Product> productMap = new HashMap<>();
        productMap.put(products.get(0).getName(), products.get(0)); // Laptop
        productMap.put(products.get(1).getName(), products.get(1)); // Mouse
        productMap.put(products.get(3).getName(), products.get(3)); // Book

        System.out.println("Iterating over product map using BiConsumer:");
        BiConsumer<String, Product> printMapEntry = (name, product) ->
                System.out.println("  Map Entry: "
                        + name
                        + " -> "
                        + product.getPrice()
                        + " (Available: "
                        + product.isAvailable()
                        + ")");
        productMap.forEach(printMapEntry);

        // 4. Chaining BiConsumers
        BiConsumer<String, Product> logAndMarkSoldOut = (name, product) -> {
            System.out.println("  Processing order for "
                    + name
                    + "...");
            product.setAvailable(false); // Mark as sold out
        };
        BiConsumer<String, Product> finalOrderProcessor = printMapEntry.andThen(logAndMarkSoldOut);

        System.out.println("Applying 'finalOrderProcessor' to 'Book' entry:");
        finalOrderProcessor.accept("Book", productMap.get("Book"));
        System.out.println("Book object after processing: "
                + productMap.get("Book")); // Verify availability changed

    }
}
