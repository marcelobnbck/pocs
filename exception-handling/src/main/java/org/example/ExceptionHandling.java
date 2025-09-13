package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ExceptionHandling {

    public static void main(String[] args) {
        String filePath = "org/example/numbers.txt"; // Example file path

        try {
            int total = sumNumbersFromFile(filePath);
            System.out.println("Total sum: " + total);
        }
        catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
        }
        catch (NumberFormatException e) {
            System.err.println("Invalid number format: " + e.getMessage());
        }
        catch (NullPointerException e) {
            System.err.println("Unexpected null value: " + e.getMessage());
        }
        catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
        finally {
            System.out.println("Execution completed.");
        }
    }

    public static int sumNumbersFromFile(String filePath) throws IOException, NumberFormatException {
        int sum = 0;

        // try-with-resources automatically closes the reader, even on exceptions
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty()) {
                    continue; // Skip empty lines
                }

                int number = Integer.parseInt(line); // May throw NumberFormatException
                sum += number;
            }
        }

        return sum;
    }
}
