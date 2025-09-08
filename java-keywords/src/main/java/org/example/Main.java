package org.example;

class Vehicle {
    final int wheels;

    Vehicle(int wheels) {
        this.wheels = wheels;
    }

    void describe() {
        System.out.println("This vehicle has " + wheels + " wheels.");
    }
}

class Car extends Vehicle {
    String brand;

    Car(String brand) {
        super(4);
        this.brand = brand;
    }

    @Override
    void describe() {
        super.describe();
        System.out.println("Brand: " + this.brand);
    }
}

public class Main {
    public static void main(String[] args) {
        Car car = new Car("Toyota");
        car.describe();
    }
}
