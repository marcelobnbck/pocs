package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDate;
import java.util.List;

public class GsonLibrary {
    public static void main(String[] args) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .excludeFieldsWithoutExposeAnnotation() // only serialize fields with @Expose
                .setPrettyPrinting()
                .create();

        Employee emp = new Employee(
                "Silvio Santos",
                35,
                "Software Engineer",
                new Address("São Paulo", "Brazil"),
                List.of("Java", "Spring", "Docker"),
                15000.99,
                LocalDate.of(2020, 5, 14)
        );

        // Serialize
        String json = gson.toJson(emp);
        System.out.println("Serialized JSON:\n" + json);

        // Deserialize
        String jsonString = """
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

        Employee deserialized = gson.fromJson(jsonString, Employee.class);
        System.out.println("\nDeserialized Object:\n" + deserialized);
    }
}
