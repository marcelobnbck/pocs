package org.example.soccer.model;

public record TeamImpl(String id, String name, String city, int foundedYear) implements Team {
    public TeamImpl {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("id is required");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("name is required");
    }
}
