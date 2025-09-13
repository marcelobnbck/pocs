import java.util.Arrays;

public class MathUtils {
    public static int add(int a, int b) {
        return a + b;
    }

    public static int square(int number) {
        return number * number;
    }

    public static boolean isNonEmpty(String s) {
        return s != null && !s.trim().isEmpty();
    }

    public static String getLongerString(String s1, String s2) {
        return s1.length() >= s2.length() ? s1 : s2;
    }

    public static double sumArray(double[] arr) {
        return Arrays.stream(arr).sum();
    }
}

@FunctionalInterface
interface BinaryIntOperation {
    int operate(int a, int b);
}

@FunctionalInterface
interface UnaryIntOperation {
    int apply(int num);
}

@FunctionalInterface
interface StringChecker {
    boolean check(String s);
}

@FunctionalInterface
interface StringChooser {
    String choose(String s1, String s2);
}

@FunctionalInterface
interface ArraySummer {
    double sum(double[] arr);
}