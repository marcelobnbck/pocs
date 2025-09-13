import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class MapOnlyDemo {

    public static void main(String[] args) {

        System.out.println("--- Map, HashMap, and EnumMap Demonstration ---");

        Product laptop = new Product("P001", "Laptop Pro", 1200.00, ProductStatus.IN_STOCK);
        Product mouse = new Product("P002", "Mouse", 25.50, ProductStatus.IN_STOCK);
        Product keyboard = new Product("P003", "Keyboard", 75.00, ProductStatus.OUT_OF_STOCK);
        Product monitor = new Product("P004", "4K Monitor", 350.00, ProductStatus.IN_STOCK);
        Product headphones = new Product("P005", "Headphones", 150.00, ProductStatus.PRE_ORDER);
        Product oldLaptop = new Product("P001", "Laptop Pro (Old Gen)", 1100.00, ProductStatus.DISCONTINUED); // Same ID as 'laptop'
        Product newKeyboard = new Product("P006", "Keyboard Pro", 80.00, ProductStatus.IN_STOCK);

        System.out.println("\n--- Map (Interface) ---");
        System.out.println("Map is an interface; it specifies how key-value pairs behave.");
        System.out.println("You interact with Map implementations (like HashMap) through this interface.");
        System.out.println("Map<K, V> K is Key type, V is Value type.\n");

        System.out.println("--- HashMap Demonstration: Product Inventory ---");

        Map<String, Product> inventoryMap = new HashMap<>();

        inventoryMap.put(laptop.getProductId(), laptop);
        inventoryMap.put(mouse.getProductId(), mouse);
        inventoryMap.put(keyboard.getProductId(), keyboard);
        inventoryMap.put(monitor.getProductId(), monitor);

        System.out.println("Initial Inventory Map: " + inventoryMap);
        System.out.println("Size: " + inventoryMap.size());

        System.out.println("\n--- Overwriting a product (P001) ---");
        System.out.println("Original Laptop (P001) in map: " + inventoryMap.get("P001"));
        inventoryMap.put(oldLaptop.getProductId(), oldLaptop); // Overwrites laptop with oldLaptop
        System.out.println("Map after putting oldLaptop (same ID P001): " + inventoryMap);
        System.out.println("Updated Laptop (P001) in map: " + inventoryMap.get("P001")); // Now retrieves oldLaptop
        System.out.println("New Size (should be same as no new key added): " + inventoryMap.size());

        inventoryMap.put(headphones.getProductId(), headphones);
        System.out.println("\n--- Adding new product P005 ---");
        System.out.println("Map after adding Headphones (P005): " + inventoryMap);

        System.out.println("\n--- Retrieving Products ---");
        Product retrievedProduct = inventoryMap.get("P003");
        System.out.println("Retrieved P003 (Keyboard): " + (retrievedProduct != null ? retrievedProduct.getName() : "Not found"));
        System.out.println("Attempt to retrieve non-existent P007: " + inventoryMap.get("P007")); // Returns null

        System.out.println("\n--- Checking for existence ---");
        System.out.println("Does map contain P002 (Mouse)? " + inventoryMap.containsKey("P002"));
        System.out.println("Does map contain P008 (non-existent)? " + inventoryMap.containsKey("P008"));
        System.out.println("Does map contain 'Laptop Pro (Old Gen)'? (uses Product's equals) " + inventoryMap.containsValue(oldLaptop));
        System.out.println("Does map contain 'Ergonomic Keyboard'? " + inventoryMap.containsValue(newKeyboard)); // Should be false, not added

        System.out.println("\n--- Removing Products ---");
        inventoryMap.remove("P004"); // Remove Monitor
        System.out.println("Map after removing P004 (Monitor): " + inventoryMap);
        System.out.println("Size after removal: " + inventoryMap.size());
        System.out.println("Attempt to remove non-existent P009: " + inventoryMap.remove("P009")); // Returns null

        System.out.println("\n--- HashMap and Nulls ---");
        inventoryMap.put(null, new Product("P_NULL", "Null Key Product", 0.0, ProductStatus.DISCONTINUED));
        System.out.println("Map after adding null key: " + inventoryMap);
        System.out.println("Value for null key: " + inventoryMap.get(null));
        inventoryMap.put("P007", null); // Can have null values
        System.out.println("Map after adding null value: " + inventoryMap);
        System.out.println("Value for P007: " + inventoryMap.get("P007"));

        System.out.println("\n--- Iterating HashMap (Order NOT Guaranteed) ---");
        System.out.println("Keys: " + inventoryMap.keySet());
        System.out.println("Values: " + inventoryMap.values());

        System.out.println("Iterating Key-Value pairs:");
        for (Map.Entry<String, Product> entry : inventoryMap.entrySet()) {
            System.out.println("  ID: " + entry.getKey() + ", Name: " + (entry.getValue() != null ? entry.getValue().getName() : "N/A (Null Value)"));
        }

        System.out.println("\n--- Clearing HashMap ---");
        inventoryMap.clear();
        System.out.println("Map after clear: " + inventoryMap);
        System.out.println("Is map empty? " + inventoryMap.isEmpty());

        System.out.println("--- EnumMap Demonstration: Product Counts by Status ---");

        Map<ProductStatus, Integer> statusCounts = new EnumMap<>(ProductStatus.class);

        for (ProductStatus status : ProductStatus.values()) {
            statusCounts.put(status, 0); // Start all counts at 0
        }
        System.out.println("Initialized EnumMap: " + statusCounts);

        Product[] allProductsArray = {laptop, mouse, keyboard, monitor, headphones, oldLaptop, newKeyboard};

        for (Product p : allProductsArray) {
            statusCounts.put(p.getStatus(), statusCounts.get(p.getStatus()) + 1);
        }

        System.out.println("\n--- Product Counts by Status ---");
        System.out.println("Final Status Counts: " + statusCounts);

        System.out.println("Number of IN_STOCK products: " + statusCounts.get(ProductStatus.IN_STOCK));
        System.out.println("Number of DISCONTINUED products: " + statusCounts.get(ProductStatus.DISCONTINUED));

        System.out.println("\n--- Updating counts (e.g., product goes OUT_OF_STOCK) ---");
        statusCounts.compute(ProductStatus.IN_STOCK, (k, currentInStock) -> currentInStock - 1); // One less in stock
        statusCounts.put(ProductStatus.OUT_OF_STOCK, statusCounts.get(ProductStatus.OUT_OF_STOCK) + 1); // One more out of stock
        System.out.println("Status counts after one product changed status: " + statusCounts);

        System.out.println("\n--- Checking for existence ---");
        System.out.println("Does EnumMap contain IN_STOCK key? " + statusCounts.containsKey(ProductStatus.IN_STOCK));

        System.out.println("\n--- Iterating EnumMap (Order IS Guaranteed) ---");
        for (Map.Entry<ProductStatus, Integer> entry : statusCounts.entrySet()) {
            System.out.println("  Status: " + entry.getKey() + ", Count: " + entry.getValue());
        }

        Map<ProductStatus, Integer> anotherMap = new HashMap<>();
        anotherMap.put(ProductStatus.IN_STOCK, 5);
        anotherMap.put(ProductStatus.PRE_ORDER, 2);

        Map<ProductStatus, Integer> copiedEnumMap = new EnumMap<>(anotherMap);
        System.out.println("\nCopied EnumMap from a HashMap: " + copiedEnumMap);

    }
}