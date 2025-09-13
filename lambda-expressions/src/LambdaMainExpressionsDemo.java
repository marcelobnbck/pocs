import java.util.List;

public class LambdaMainExpressionsDemo {

    @FunctionalInterface
    interface NoArgFunction {
        String getValue(); // No arguments, returns a String
    }

    @FunctionalInterface
    interface SingleArgConsumer {
        void accept(String message); // One argument, returns void (performs an action)
    }

    @FunctionalInterface
    interface SingleArgFunction {
        int calculateSquare(int number); // One argument, returns an int
    }

    @FunctionalInterface
    interface BiArgOperation {
        int operate(int a, int b); // Two arguments, returns an int
    }

    @FunctionalInterface
    interface MultiStatementLambda {
        int process(int x, int y); // Multiple statements in body, returns an int
    }

    public static void main(String[] args) {

        // 1. Lambda with NO Parameters
        System.out.println("\n=== 1. Lambda with NO Parameters ===");

        // Expression body: Simple return value
        NoArgFunction greetingSupplier = () -> "Hello, World!";
        System.out.println("No-arg lambda (expression body): " + greetingSupplier.getValue());

        // Block body: Multiple statements or side-effects
        NoArgFunction complexGreetingSupplier = () -> {
            String part1 = "Greetings";
            String part2 = "from a block!";
            return part1 + " " + part2;
        };
        System.out.println("No-arg lambda (block body): " + complexGreetingSupplier.getValue());

        // 2. Lambda with ONE Parameter
        System.out.println("\n=== 2. Lambda with ONE Parameter ===");

        // Parameter with inferred type, expression body
        SingleArgConsumer printUpperCase = msg -> System.out.println(
                "Uppercase: " + msg.toUpperCase()
        );
        printUpperCase.accept("hello java");

        // Parameter with explicit type, expression body (parentheses required with explicit type)
        SingleArgConsumer printLowerCase = (String msg) -> System.out.println(
                "Lowercase: " + msg.toLowerCase()
        );
        printLowerCase.accept("HELLO JAVA");

        // Parameter with inferred type, expression body (for return value)
        SingleArgFunction squareCalculator = num -> num * num;
        System.out.println("Square of 7: " + squareCalculator.calculateSquare(7));

        // Parameter with inferred type, block body (for return value)
        SingleArgFunction cubeCalculator = num -> {
            int result = num * num * num;
            return result; // 'return' keyword is mandatory in block body if a value is expected
        };
        System.out.println("Cube of 4: " + cubeCalculator.calculateSquare(4));


        // 3. Lambda with MULTIPLE Parameters
        // Multiple parameters with inferred types, expression body
        BiArgOperation sum = (a, b) -> a + b;
        System.out.println("Sum of 15 and 20: " + sum.operate(15, 20));

        // Multiple parameters with explicit types, expression body
        BiArgOperation product = (int x, int y) -> x * y;
        System.out.println("Product of 6 and 8: " + product.operate(6, 8));

        // Multiple parameters with inferred types, block body
        MultiStatementLambda complexOperation = (num1, num2) -> {
            int intermediate = num1 * 2;
            int finalResult = intermediate + num2;
            return finalResult;
        };
        System.out.println("Complex operation (10, 5): " + complexOperation.process(10, 5)); // (10 * 2) + 5 = 25

        // 4. Method References (Concise Lambda Form)
        // A shorthand for lambdas that simply call an existing method.

        List<String> words = new java.util.ArrayList<>(
                java.util.Arrays.asList("apple", "banana", "orange")
        );

        // Lambda: word -> System.out.println(word)
        System.out.println("Printing words using method reference:");
        words.forEach(System.out::println);

        // Lambda: (str1, str2) -> str1.compareToIgnoreCase(str2)
        java.util.Comparator<String> caseInsensitiveComparator = String::compareToIgnoreCase;
        System.out.println("Comparing 'Apple' and 'apple': "
                + caseInsensitiveComparator.compare("Apple", "apple")); // Expected: 0

    }
}
