package org.example;

public class GenericsReusableLogic {
    public static <T> void printArray(T[] array) {
        for (T element : array) {
            System.out.println(element);
        }
    }

    public static void main(String[] args) {
        Integer[] nums = {1, 2, 3};
        String[] words = {"A", "B", "C"};

        // Same method works for any type
        GenericsReusableLogic.printArray(nums);
        GenericsReusableLogic.printArray(words);
    }
}