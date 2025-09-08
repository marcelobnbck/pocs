# Java Example: Using `final`, `super`, and `this` keywords

This is a simple Java program demonstrating the use of the keywords **`final`**, **`super`**, and **`this`** in the context of object-oriented programming.

## Code Overview

### **Vehicle Class**
- **`final int wheels`** → A constant value assigned in the constructor and cannot be changed later.
- Constructor uses **`this`** to refer to the current object's field.
- `describe()` method prints the number of wheels.

### **Car Class (extends Vehicle)**
- Inherits from `Vehicle`.
- Constructor calls **`super(4)`** to pass a fixed number of wheels to the parent class.
- Uses **`this`** to set the `brand` attribute.
- Overrides `describe()` to add brand information while still calling the superclass method with **`super.describe()`**.

## How to Run

1. **Compile the Java files**:
```bash
javac Main.java Vehicle.java Car.java
```
Run the program:
```bash
java Main
```
## Output
```terminaloutput
This vehicle has 4 wheels.
Brand: Toyota
```

## Key Concepts Demonstrated
1. final
   Used on variables to make them constants (cannot be reassigned).

Example:
```java
final int wheels;
```

2. this
   Refers to the current object’s attributes and methods.

Example:
```java
this.wheels = wheels;
```

3. super
   Calls the parent class’s constructor or methods.

Example:
```java
super(4);
super.describe();
```