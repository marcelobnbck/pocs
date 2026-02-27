package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReminderApp {

    static class Reminder {
        private String title;
        private String description;

        public Reminder(String title, String description) {
            this.title = title;
            this.description = description;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return "Title: " + title + " | Description: " + description;
        }
    }

    private static final List<Reminder> reminders = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            printMenu();
            int option = getIntInput("\nChoose an option: ");

            switch (option) {
                case 1 -> addReminder();
                case 2 -> editReminder();
                case 3 -> removeReminder();
                case 4 -> listReminders();
                case 5 -> {
                    running = false;
                    System.out.println("Closed");
                }
                default -> System.out.println("Invalid option!");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\nREMINDER APP\n");
        System.out.println("1 - Add Reminder");
        System.out.println("2 - Edit Reminder");
        System.out.println("3 - Remove Reminder");
        System.out.println("4 - List Reminders");
        System.out.println("5 - Exit");
    }

    private static void addReminder() {
        System.out.print("Enter title: ");
        String title = scanner.nextLine();

        System.out.print("Enter description: ");
        String description = scanner.nextLine();

        reminders.add(new Reminder(title, description));
        System.out.println("Reminder added successfully!");
    }

    private static void editReminder() {
        listReminders();
        if (reminders.isEmpty()) return;

        int index = getIntInput("Enter reminder number to edit: ") - 1;

        if (index >= 0 && index < reminders.size()) {
            System.out.print("New title: ");
            String title = scanner.nextLine();

            System.out.print("New description: ");
            String description = scanner.nextLine();

            reminders.get(index).setTitle(title);
            reminders.get(index).setDescription(description);

            System.out.println("Reminder updated successfully!");
        } else {
            System.out.println("Invalid reminder number!");
        }
    }

    private static void removeReminder() {
        listReminders();
        if (reminders.isEmpty()) return;

        int index = getIntInput("Enter reminder number to remove: ") - 1;

        if (index >= 0 && index < reminders.size()) {
            reminders.remove(index);
            System.out.println("Reminder removed successfully!");
        } else {
            System.out.println("Invalid reminder number!");
        }
    }

    private static void listReminders() {
        if (reminders.isEmpty()) {
            System.out.println("No reminders found.");
            return;
        }

        System.out.println("\nYour Reminders:");
        for (int i = 0; i < reminders.size(); i++) {
            System.out.println((i + 1) + ". " + reminders.get(i));
        }
    }

    private static int getIntInput(String message) {
        while (true) {
            try {
                System.out.print(message);
                int input = Integer.parseInt(scanner.nextLine());
                return input;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
