package org.example;

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
        List<? extends Number> list = List.of(1, 2, 3);
        System.out.println(sum(list));
        // Accepts Integer, Double, etc.
    }
}
