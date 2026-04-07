package org.example;

class GenericsBounded<T extends Number> {
    public double doubleValue(T number) {
        return number.doubleValue();
    }

    public static void main(String[] args) {
        GenericsBounded<Integer> calc1 = new GenericsBounded<>();
        GenericsBounded<Double> calc2 = new GenericsBounded<>();
        // GenericsBounded<String> NOT allowed
    }
}
