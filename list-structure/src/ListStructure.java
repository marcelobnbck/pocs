import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListStructure {

    public static void main(String[] args) {

        // List (Interface):
        // 'List' is an interface in Java that defines a contract for ordered collections of
        // elements. Allows duplicate elements and maintains insertion order. We cannot directly
        // instantiate an interface.
        System.out.println("--- Demonstrating List (Interface) ---");
        System.out.println("List is an interface; it cannot be instantiated directly.");
        System.out.println("It defines the behavior for ordered collections.\n");

        // ArrayList (List Implementation):
        // 'ArrayList' is an implementation of the 'List' interface that uses a dynamic array.
        // It is efficient for random access and for adding/removing elements at the end. However,
        // adding or removing elements in the middle of the list can be workload, as it requires
        // shifting elements.
        System.out.println("--- Demonstrating ArrayList ---");
        List<String> myArrayList = new ArrayList<>();

        // Adding elements and allows duplicate elements
        myArrayList.add("Apple");
        myArrayList.add("Banana");
        myArrayList.add("Orange");
        myArrayList.add("Apple"); // Duplicated

        System.out.println("ArrayList elements: " + myArrayList);

        // Accessing elements by index
        System.out.println("Element at index 1: " + myArrayList.get(1));

        // Removing an element
        myArrayList.remove("Orange");
        System.out.println("ArrayList after removing Orange: " + myArrayList);

        // Iterating over the ArrayList
        System.out.println("Iterating over the ArrayList:");
        for (String fruit : myArrayList) {
            System.out.println(fruit);
        }

        // LinkedList (List Implementation):
        // 'LinkedList' is also an implementation of the 'List' interface, but it uses a doubly
        // linked list data structure. This makes easy for adding or removing elements at any
        // position, as it doesn't require shifting elements. However, random access (retrieving
        // by index) is slower, as it may be necessary to traverse the list to the desired index.
        System.out.println("--- Demonstrating LinkedList ---");
        List<String> myLinkedList = new LinkedList<>();

        // Adding elements
        myLinkedList.add("Car");
        myLinkedList.add("Motorcycle");
        myLinkedList.add("Boat");

        System.out.println("LinkedList elements: " + myLinkedList);

        // Adding at the beginning (efficient for LinkedList)
        myLinkedList.add(0, "Airplane");
        System.out.println("LinkedList after adding Airplane at the beginning: " + myLinkedList);

        // Removing an element from the middle (efficient for LinkedList)
        myLinkedList.remove("Motorcycle");
        System.out.println("LinkedList after removing Motorcycle: " + myLinkedList);

        // Accessing an element (can be slower than ArrayList for large lists)
        System.out.println("Element at index 1: " + myLinkedList.get(1));

    }
}