package org.example.musicconverter;

public record GenreInfo(MusicGenre genre, String description) {
    public GenreInfo(MusicGenre genre, String description) {
        this.genre = genre;
        this.description = description;
    }

    public String summary() {
        return genre + " — " + description;
    }
}
