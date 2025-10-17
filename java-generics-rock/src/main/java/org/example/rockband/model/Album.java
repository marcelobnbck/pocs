package org.example.rockband.model;

public class Album {
    private String title;
    private int releaseYear;

    public Album(String title, int releaseYear) {
        this.title = title;
        this.releaseYear = releaseYear;
    }

    public String getTitle() {
        return title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    @Override
    public String toString() {
        return title + " (" + releaseYear + ")";
    }
}
