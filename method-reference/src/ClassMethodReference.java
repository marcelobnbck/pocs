import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ClassMethodReference {

    public static void main(String[] args) {

        // 1. Referencing a static method with two arguments
        BinaryIntOperation adder = MathUtils::add;
        System.out.println("Sum of 10 and 5: "
                + adder.operate(10, 5)); // Calls MathUtils.add(10, 5)
        System.out.println("Sum of 25 and 75: "
                + adder.operate(25, 75));

        // 2. Referencing a static method with one argument
        UnaryIntOperation squarer = MathUtils::square;
        System.out.println("Square of 8: "
                + squarer.apply(8)); // Calls MathUtils.square(8)
        System.out.println("Square of 12: "
                + squarer.apply(12));

        // 3. Referencing a static method for a Predicate
        Predicate<String> nonEmptyChecker = MathUtils::isNonEmpty;
        System.out.println("Is 'hello' non-empty? "
                + nonEmptyChecker.test("hello"));
        System.out.println("Is '' non-empty? "
                + nonEmptyChecker.test(""));
        System.out.println("Is '   ' non-empty? "
                + nonEmptyChecker.test("   ")); // trim() handles this
        System.out.println("Is null non-empty? "
                + nonEmptyChecker.test(null));

        // 4. Referencing a static method for a BiFunction
        BiFunction<String, String, String> longerStringChooser = MathUtils::getLongerString;
        System.out.println("Longer of 'apple' and 'banana': "
                + longerStringChooser.apply("apple", "banana"));
        System.out.println("Longer of 'short' and 'very long string': "
                + longerStringChooser.apply("short", "very long string"));

        // 5. Using Class::method in Stream API operations
        System.out.println("5. Using Class::method in Stream API");

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        System.out.println("Original numbers: "
                + numbers);

        // Using MathUtils::square in map()
        List<Integer> squaredNumbers = numbers.stream()
                .map(MathUtils::square)
                .collect(Collectors.toList());
        System.out.println("Squared numbers: "
                + squaredNumbers);

        List<String> rawStrings = Arrays.asList("java", "  ", "  stream", null, "api");
        System.out.println("Raw strings: " + rawStrings);

        // Using MathUtils::isNonEmpty in filter()
        List<String> filteredStrings = rawStrings.stream()
                .filter(MathUtils::isNonEmpty) // Filter out empty/null strings
                .collect(Collectors.toList());
        System.out.println("Filtered non-empty strings: " + filteredStrings);

        // 6. Referencing a static method with an array argument
        ArraySummer arraySummer = MathUtils::sumArray;
        double[] data = {10.5, 20.0, 5.5};
        System.out.println("Sum of array "
                + Arrays.toString(data)
                + ": "
                + arraySummer.sum(data));

    }
}
