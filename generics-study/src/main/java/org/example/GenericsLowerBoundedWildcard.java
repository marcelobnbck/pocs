package org.example;

import java.util.ArrayList;
import java.util.List;

public class GenericsLowerBoundedWildcard {
    public static void addNumbers(List<? super Integer> list) {
        list.add(10);
        list.add(20);
        list.add(30);
    }

    public static void main(String[] args) {
        List<? super Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        addNumbers(list);

        for (Object item : list) {
            System.out.println(item);
        }
        //        Can accept:
        //        List<Integer>
        //        List<Number>
        //        List<Object>
    }
}
