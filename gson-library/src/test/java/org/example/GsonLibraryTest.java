package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GsonLibraryTest {

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .excludeFieldsWithoutExposeAnnotation()
            .create();

    @Test
    void testSerialization() {
        Employee emp = new Employee(
                "Silvio Santos",
                35,
                "Software Engineer",
                new Address("São Paulo", "Brazil"),
                List.of("Java", "Spring", "Docker"),
                15000.99,
                LocalDate.of(2020, 5, 14)
        );

        String json = gson.toJson(emp);

        assertTrue(json.contains("Silvio Santos"));
        assertTrue(json.contains("Software Engineer"));
        assertFalse(json.contains("15000.99")); // salary must be excluded
    }

    @Test
    void testDeserialization() {
        String json = """
                {
                  "name": "Maria Oliveira",
                  "age": 40,
                  "job_title": "Manager",
                  "address": {
                    "city": "New York",
                    "country": "USA"
                  },
                  "skills": ["Leadership", "Negotiation"],
                  "hireDate": "2018-03-01"
                }
                """;

        Employee emp = gson.fromJson(json, Employee.class);

        assertEquals("Maria Oliveira", emp.toString().split("'")[1]); // crude check for name
        assertEquals("Manager", emp.toString().split("'")[3]);
        assertEquals(LocalDate.of(2018, 3, 1), emp.hireDate); // hireDate parsed correctly
    }
}
