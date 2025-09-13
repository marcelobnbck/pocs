package org.example;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

public class DateConversionExample {

    public static void main(String[] args) {
        // 1. Parse a date string to LocalDate
        String dateStr = "2025-08-13";
        LocalDate localDate = parseDate(dateStr);

        // 2. Convert LocalDate to LocalDateTime
        LocalDateTime localDateTime = localDate.atStartOfDay();

        // 3. Convert LocalDateTime to Instant (UTC)
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();

        // 4. Convert Instant to java.util.Date
        Date legacyDate = Date.from(instant);

        // 5. Format LocalDateTime to string
        String formattedDate = formatDate(localDateTime);

        // 6. Convert Date to LocalDateTime
        LocalDateTime fromLegacy = convertDateToLocalDateTime(legacyDate);

        // 7. Handle time zones
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("America/Sao_Paulo"));

        System.out.println("Original String: " + dateStr);
        System.out.println("LocalDate: " + localDate);
        System.out.println("LocalDateTime: " + localDateTime);
        System.out.println("Instant (UTC): " + instant);
        System.out.println("Legacy Date: " + legacyDate);
        System.out.println("Formatted Date: " + formattedDate);
        System.out.println("Converted from Legacy Date: " + fromLegacy);
        System.out.println("ZonedDateTime (São Paulo): " + zonedDateTime);
    }

    // Parse string to LocalDate
    public static LocalDate parseDate(String dateStr) {
        try {
            return LocalDate.parse(dateStr);
        } catch (DateTimeParseException e) {
            System.err.println("Invalid date format: " + dateStr);
            return LocalDate.now();
        }
    }

    // Format LocalDateTime to string
    public static String formatDate(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return dateTime.format(formatter);
    }

    // Convert java.util.Date to LocalDateTime
    public static LocalDateTime convertDateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }
}