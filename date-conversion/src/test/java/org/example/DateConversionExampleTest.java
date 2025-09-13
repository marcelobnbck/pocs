package org.example;

import org.junit.jupiter.api.Test;

import java.time.*;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class DateConversionExampleTest {

    @Test
    void testParseValidDate() {
        LocalDate expected = LocalDate.of(2025, 8, 13);
        LocalDate actual = DateConversionExample.parseDate("2025-08-13");
        assertEquals(expected, actual);
    }

    @Test
    void testParseInvalidDate() {
        LocalDate fallback = LocalDate.now();
        LocalDate actual = DateConversionExample.parseDate("invalid-date");
        // Allowing small tolerance due to timing
        assertTrue(Math.abs(Duration.between(fallback.atStartOfDay(), actual.atStartOfDay()).toHours()) < 24);
    }

    @Test
    void testFormatDate() {
        LocalDateTime dateTime = LocalDateTime.of(2025, 8, 13, 14, 30, 0);
        String formatted = DateConversionExample.formatDate(dateTime);
        assertEquals("13/08/2025 14:30:00", formatted);
    }

    @Test
    void testConvertDateToLocalDateTime() {
        LocalDateTime original = LocalDateTime.of(2025, 8, 13, 10, 0);
        Date legacyDate = Date.from(original.atZone(ZoneId.systemDefault()).toInstant());
        LocalDateTime converted = DateConversionExample.convertDateToLocalDateTime(legacyDate);
        assertEquals(original, converted);
    }

    @Test
    void testZonedDateTimeConversion() {
        LocalDateTime dateTime = LocalDateTime.of(2025, 8, 13, 12, 0);
        ZonedDateTime zoned = dateTime.atZone(ZoneId.of("America/Sao_Paulo"));
        assertEquals(ZoneId.of("America/Sao_Paulo"), zoned.getZone());
        assertEquals(dateTime, zoned.toLocalDateTime());
    }
}