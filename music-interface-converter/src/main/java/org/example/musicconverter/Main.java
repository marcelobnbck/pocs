package org.example.musicconverter;

public class Main {
    public static void main(String[] args) {
        String[] examples = {"Rock", "pop", "Hip-Hop", "EDM", "baroque", null};
        for (String s : examples) {
            var genre = GenreConverters.stringToGenre.convert(s);
            var info = GenreConverters.stringToGenreInfo.convert(s);
            System.out.println("Input: " + s);
            System.out.println(" -> Genre: " + genre);
            System.out.println(" -> Info: " + info.summary());
            System.out.println();
        }
    }
}
