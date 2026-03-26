package org.example;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ReportGenerator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- 1:1 Weekly Report ---");

        System.out.println("1.1 - How are you feeling? Everything ok?");
        String feeling = scanner.nextLine();

        Map<String, Object> context = new HashMap<>();
        context.put("generatedDate", ZonedDateTime.now().toString());
        context.put("answer11", feeling);

        renderReport(context);
    }

    private static void renderReport(Map<String, Object> context) {
        System.out.println("--- Report generated successfully ---");
        // TODO Handlebars library to read .hbs file
        System.out.println(context);
    }

}
