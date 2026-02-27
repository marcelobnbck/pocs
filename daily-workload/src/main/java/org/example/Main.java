package org.example;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonthValue();
        int monthlyDays = today.lengthOfMonth();
        int dayOfMonth = today.getDayOfMonth();
        String weekday = today.getDayOfWeek().toString();
        int weekday2 = today.getDayOfWeek().getValue();
        int firstDayOfMonth = today.withDayOfMonth(1).getDayOfWeek().getValue();
        int lastDayOfMonth = today.withDayOfMonth(monthlyDays).getDayOfWeek().getValue();
        int workingDays = 0;

        // Iterate over the days of the month

        System.out.println("Today: " + today);
        System.out.println("Year: " + year);
        System.out.println("Month: " + month);
        System.out.println("Days in Month: " + monthlyDays);
        System.out.println("Day of Month: " + dayOfMonth);
        System.out.println("Weekday: " + weekday);
        System.out.println("Weekday2: " + weekday2);
        System.out.println("Working Days so far this month: " + workingDays);
        System.out.println("firstDayOfMonth: " + firstDayOfMonth);
        System.out.println("lastDayOfMonth: " + lastDayOfMonth);
    }
}