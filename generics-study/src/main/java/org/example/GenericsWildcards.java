package org.example;

import java.util.List;

public class GenericsWildcards {
    public static void printList(List<?> list) {
        for (Object item : list) {
            System.out.println(item);
        }
    }

    public static void main(String[] args) {
        List<Integer> nums = List.of(1, 2, 3);
        List<String> names = List.of("A", "B");

        printList(nums);
        printList(names);
        // Read-only example
    }
}