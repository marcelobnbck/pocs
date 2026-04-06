package org.example;

import java.util.ArrayList;
import java.util.List;

public class LowerBoundedWildcard {
    public static void addNumbers(List<? super Integer> list) {
        list.add(10);
        list.add(20);
    }

    public static void main(String[] args) {
        // Works with List<Integer>
        List<Integer> intList = new ArrayList<>();
        addNumbers(intList);
        System.out.println(intList);

        // Works with List<Number>
        List<Number> numList = new ArrayList<>();
        addNumbers(numList);
        System.out.println(numList);

        // Works with List<Object>
        List<Object> objList = new ArrayList<>();
        addNumbers(objList);
        System.out.println(objList);
    }
}