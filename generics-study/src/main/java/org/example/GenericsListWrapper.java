package org.example;

import java.util.ArrayList;
import java.util.List;

class GenericsListWrapper<T> {
    private List<T> items = new ArrayList<>();

    public void add(T item) {
        items.add(item);
    }

    public T get(int index) {
        return items.get(index);
    }

    public List<T> getAll() {
        return items;
    }

    public static void main(String[] args) {
        GenericsListWrapper<String> repo = new GenericsListWrapper<>();
        repo.add("Java");
        repo.add("Python");

        System.out.println(repo.get(0)); // Java

        // Similar to how frameworks (Spring, Hibernate) use generics.
    }
}