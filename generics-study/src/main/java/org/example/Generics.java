package org.example;

class Generics<T> {
    private T value;

    public void set(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }

    public static void main(String[] args) {
        Generics<String> box = new Generics<>();
        box.set("Hello");

        String value = box.get(); // No cast needed

        System.out.println(value);
    }
}