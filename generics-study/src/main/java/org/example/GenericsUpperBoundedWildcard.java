package org.example;

import java.util.Arrays;
import java.util.List;

public class GenericsUpperBoundedWildcard {
    public static double sum(List<? extends Number> list) {
        double total = 0;
        for (Number n : list) {
            total += n.doubleValue();
        }
        return total;
    }

    public static void main(String[] args) {
        // List of Integers
        List<Integer> intList = Arrays.asList(10, 20, 30);
        System.out.println(sum(intList));

        // List of Doubles
        List<Double> doubleList = Arrays.asList(1.5, 2.5, 3.5);
        System.out.println(sum(doubleList));

        // List of Mixed Numbers (using Number as the reference)
        List<Number> mixedList = Arrays.asList(1, 2.5, 10L);
        System.out.println(sum(mixedList));
    }
}