import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class SetStructures {

    // Define a simple enum for EnumSet demonstration
    enum Day {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }

    public static void main(String[] args) {

        // Set (Interface):
        // 'Set' is an interface in Java that does NOT contain duplicate elements. It does not
        // guarantee any specific order of elements. We cannot directly instantiate an interface.
        // Only with his concrete implementations, like HashSet or EnumSet.
        System.out.println("--- Demonstrating Set (Interface) ---");
        System.out.println("Set is an interface; it cannot be instantiated directly.");
        System.out.println("It defines the behavior for collections that do not allow duplicates.\n");

        // HashSet (Set Implementation):
        // 'HashSet' is an implementation that uses a hash table for storage. It does
        // not guarantee any specific order of elements.
        System.out.println("--- Demonstrating HashSet ---");
        Set<String> myHashSet = new HashSet<>();

        // Adding elements
        myHashSet.add("Apple");
        myHashSet.add("Banana");
        myHashSet.add("Orange");
        myHashSet.add("Apple"); // This will not be added as it's a duplicate

        System.out.println("HashSet elements: " + myHashSet); // Order is not guaranteed

        // Checking for duplicates
        System.out.println("Adding 'Apple' again? " + myHashSet.add("Apple")); // Returns false
        System.out.println("HashSet after trying to add duplicate: " + myHashSet);

        // Checking if an element exists
        System.out.println("Does HashSet contain 'Banana'? " + myHashSet.contains("Banana"));
        System.out.println("Does HashSet contain 'Grape'? " + myHashSet.contains("Grape"));

        // Removing an element
        myHashSet.remove("Orange");
        System.out.println("HashSet after removing Orange: " + myHashSet);

        // Iterating over the HashSet
        System.out.println("Iterating over the HashSet (order not guaranteed):");
        for (String fruit : myHashSet) {
            System.out.println(fruit);
        }

        // EnumSet (Set Implementation for Enums):
        // 'EnumSet' is a specialized Set implementation for use with enum types.
        // All elements in an EnumSet must come from a single enum type. It maintains
        // the natural order of the enum constants.
        System.out.println("--- Demonstrating EnumSet ---");

        // Creating an EnumSet containing all elements of the Day enum
        Set<Day> allDays = EnumSet.allOf(Day.class);
        System.out.println("All days: " + allDays);

        // Creating an EnumSet for weekdays
        Set<Day> weekdays = EnumSet.of(
                Day.MONDAY,
                Day.TUESDAY,
                Day.WEDNESDAY,
                Day.THURSDAY,
                Day.FRIDAY
        );
        System.out.println("Weekdays: " + weekdays);

        // Adding an element (if not already present)
        weekdays.add(Day.SATURDAY); // This will be added
        System.out.println("Weekdays after adding Saturday: " + weekdays);

        // Removing an element
        weekdays.remove(Day.SATURDAY);
        System.out.println("Weekdays after removing Saturday: " + weekdays);

        // Creating an EnumSet for weekend days
        Set<Day> weekendDays = EnumSet.range(Day.SATURDAY, Day.SUNDAY);
        System.out.println("Weekend days: " + weekendDays);

        // Operations like contains also work efficiently
        System.out.println("Does weekdays contain SUNDAY? " + weekdays.contains(Day.SUNDAY));
        System.out.println("Does weekendDays contain SUNDAY? " + weekendDays.contains(Day.SUNDAY));

    }
}