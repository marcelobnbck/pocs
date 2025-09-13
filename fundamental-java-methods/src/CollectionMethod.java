import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CollectionMethod {

    public static void main(String[] args) {

        System.out.println("--- Demonstrating Common Collection Methods ---");
        System.out.println("Methods covered: add(), put(), contains(), remove(), size()\n");

        System.out.println("--- ArrayList (List) Demo ---");
        List<String> fruitsList = new ArrayList<>();

        System.out.println("1. add() method:");
        fruitsList.add("Apple");
        fruitsList.add("Banana");
        fruitsList.add("Cherry");
        fruitsList.add("Apple"); // List allows duplicates
        System.out.println("Fruits List after adds: " + fruitsList);

        System.out.println("2. size() method:");
        System.out.println("Current size of Fruits List: " + fruitsList.size()); // Expected: 4

        System.out.println("3. contains() method:");
        System.out.println("Does Fruits List contain 'Banana'? " + fruitsList.contains("Banana"));   // Expected: true
        System.out.println("Does Fruits List contain 'Grape'? " + fruitsList.contains("Grape"));     // Expected: false

        System.out.println("4. remove() method:");
        fruitsList.remove("Apple"); // Removes the first "Apple"
        System.out.println("Fruits List after removing first 'Apple': " + fruitsList); // Expected: [Banana, Cherry, Apple]
        fruitsList.remove(0); // Removes element at index 0 (which is now "Banana")
        System.out.println("Fruits List after removing element at index 0: " + fruitsList); // Expected: [Cherry, Apple]

        System.out.println("Current size of Fruits List after removals: " + fruitsList.size()); // Expected: 2

        System.out.println("--- HashSet (Set) Demo ---");
        Set<Integer> uniqueNumbers = new HashSet<>();

        System.out.println("1. add() method:");
        System.out.println("Adding 10: " + uniqueNumbers.add(10)); // Expected: true
        System.out.println("Adding 20: " + uniqueNumbers.add(20)); // Expected: true
        System.out.println("Adding 10 (duplicate): " + uniqueNumbers.add(10)); // Expected: false
        System.out.println("Adding 30: " + uniqueNumbers.add(30)); // Expected: true
        System.out.println("Unique Numbers Set after adds: " + uniqueNumbers); // Order not guaranteed

        System.out.println("2. size() method:");
        System.out.println("Current size of Unique Numbers Set: " + uniqueNumbers.size()); // Expected: 3 (10, 20, 30)

        System.out.println("3. contains() method:");
        System.out.println("Does Unique Numbers Set contain 20? " + uniqueNumbers.contains(20)); // Expected: true
        System.out.println("Does Unique Numbers Set contain 50? " + uniqueNumbers.contains(50)); // Expected: false

        System.out.println("4. remove() method:");
        System.out.println("Removing 20: " + uniqueNumbers.remove(20)); // Expected: true
        System.out.println("Removing 50 (non-existent): " + uniqueNumbers.remove(50)); // Expected: false
        System.out.println("Unique Numbers Set after removals: " + uniqueNumbers);

        System.out.println("Current size of Unique Numbers Set after removals: " + uniqueNumbers.size()); // Expected: 2 (10, 30)

        System.out.println("--- HashMap (Map) Demo ---");
        Map<String, String> countryCapitals = new HashMap<>();

        System.out.println("1. put() method:");
        countryCapitals.put("USA", "Washington D.C.");
        countryCapitals.put("France", "Paris");
        countryCapitals.put("Germany", "Berlin");
        System.out.println("Country Capitals Map after puts: " + countryCapitals);

        System.out.println("Updating USA's capital (put again with same key): " + countryCapitals.put("USA", "New York (oops!)")); // Returns old value "Washington D.C."
        System.out.println("Country Capitals Map after update: " + countryCapitals);

        System.out.println("2. size() method:");
        System.out.println("Current size of Country Capitals Map: " + countryCapitals.size()); // Expected: 3

        System.out.println("3. containsKey() and containsValue() methods:");
        System.out.println("Does Map contain key 'France'? " + countryCapitals.containsKey("France"));       // Expected: true
        System.out.println("Does Map contain key 'Italy'? " + countryCapitals.containsKey("Italy"));         // Expected: false
        System.out.println("Does Map contain value 'Paris'? " + countryCapitals.containsValue("Paris"));     // Expected: true
        System.out.println("Does Map contain value 'London'? " + countryCapitals.containsValue("London"));   // Expected: false

        System.out.println("4. remove() method:");
        System.out.println("Removing 'France': " + countryCapitals.remove("France")); // Returns "Paris"
        System.out.println("Country Capitals Map after removing 'France': " + countryCapitals);
        System.out.println("Attempt to remove non-existent 'Italy': " + countryCapitals.remove("Italy")); // Returns null

        System.out.println("Current size of Country Capitals Map after removals: " + countryCapitals.size()); // Expected: 2
    }
}