package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class InputCollector {
    private final Scanner scanner;

    public InputCollector() {
        this.scanner = new Scanner(System.in);
    }

    public Map<String, Object> collectReportData() {
        Map<String, Object> data = new HashMap<>();

        System.out.println("--- 1:1 Weekly Report ---\n");
        System.out.println("Enter your name: ");
        data.put("name", scanner.nextLine());

        System.out.println("\n--- Week By Week Metrics ---");
        data.put("pgReadThisWeek", promptInt("Pages read this week:"));
        data.put("pgReadLastWeek", promptInt("Pages read last week:"));
        data.put("pocsThisWeek", promptInt("Program POCs this week:"));
        data.put("pocsLastWeek", promptInt("Program POCs last week:"));
        data.put("slackThisWeek", promptInt("Slack participations this week:"));
        data.put("slackLastWeek", promptInt("Slack participations last week:"));

        System.out.println("\n--- Section 1: You ---");
        System.out.println("1.1 How are you feeling? Everything ok?");
        data.put("answer11", scanner.nextLine());
        System.out.println("1.2 Any feedback for us?");
        data.put("answer12", scanner.nextLine());
        System.out.println("1.3 Feedback for your leader?");
        data.put("answer13", scanner.nextLine());
        System.out.println("\n--- Section 2: Performance ---");
        System.out.println("2.1 Link of the Last 3 PRs:");
        data.put("prLink1", scanner.nextLine());
        data.put("prLink2", scanner.nextLine());
        data.put("prLink3", scanner.nextLine());
        System.out.println("2.2 How long is the last PR Open? How many days on the current task?");
        data.put("answer22", scanner.nextLine());
        System.out.println("2.4 When is the last day you got feedback from a LC Manager?");
        data.put("dateAnswer23", scanner.nextLine());

        System.out.println("\n--- Section 3: Project ---");
        System.out.println("3.1 How is your project going?");
        data.put("answer31", scanner.nextLine());
        System.out.println("3.2 Any issue? stuck in something?");
        data.put("answer32", scanner.nextLine());
        System.out.println("3.3 Miss any release? Any Blockers?");
        data.put("answer33", scanner.nextLine());
        System.out.println("3.4 For how long are you in the current task? (days)");
        data.put("numberDays34", scanner.nextLine());

        System.out.println("\n--- Section 4: Impact ---");
        System.out.println("4.1 How is the relationship with LC going?");
        data.put("answer41", scanner.nextLine());
        System.out.println("4.2 Date and list of Last Proactive Task/Project:");
        data.put("answer42", scanner.nextLine());

        System.out.println("\n--- Section 5: Self Improvement ---");
        System.out.println("5.1 Link of the past POC:");
        data.put("pocLinksAnswer51", scanner.nextLine());
        System.out.println("5.2 What are you reading?");
        data.put("bookName52", scanner.nextLine());
        data.put("totalNumberPages52", promptInt("Total pages:"));
        System.out.println("5.3 What Feedbacks do you need to improve?");
        data.put("bookName53", scanner.nextLine());

        System.out.println("\n--- Section 6: AI Usage ---");
        System.out.println("6.1 How do you use AI on POCs?");
        data.put("answer61", scanner.nextLine());
        System.out.println("6.2 What are the main use cases you are exploring?");
        data.put("answer62", scanner.nextLine());
        System.out.println("6.3 Did you find any interesting use case?");
        data.put("answer63", scanner.nextLine());
        System.out.println("6.4 Would you like to share any successful use case?");
        data.put("answer64", scanner.nextLine());
        System.out.println("6.5 Did you face any challenge or issues on AI coding?");
        data.put("answer65", scanner.nextLine());
        return data;
    }

    private int promptInt(String message) {
        System.out.println(message);
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a valid number:");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }

    public void close() {
        scanner.close();
    }
}
