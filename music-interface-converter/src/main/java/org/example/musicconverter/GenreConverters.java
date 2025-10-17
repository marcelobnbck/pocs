package org.example.musicconverter;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public final class GenreConverters {
    private static final Map<String, MusicGenre> NAME_MAP = Map.ofEntries(
            Map.entry("rock", MusicGenre.ROCK),
            Map.entry("pop", MusicGenre.POP),
            Map.entry("jazz", MusicGenre.JAZZ),
            Map.entry("classical", MusicGenre.CLASSICAL),
            Map.entry("hiphop", MusicGenre.HIPHOP),
            Map.entry("hip-hop", MusicGenre.HIPHOP),
            Map.entry("electronic", MusicGenre.ELECTRONIC),
            Map.entry("edm", MusicGenre.ELECTRONIC),
            Map.entry("blues", MusicGenre.BLUES),
            Map.entry("country", MusicGenre.COUNTRY),
            Map.entry("reggae", MusicGenre.REGGAE),
            Map.entry("metal", MusicGenre.METAL)
    );

    public static final Converter<String, MusicGenre> stringToGenre = Converter.of(s -> {
        if (s == null) return MusicGenre.UNKNOWN;
        String key = s.trim().toLowerCase(Locale.ROOT);
        return NAME_MAP.getOrDefault(key, tryMatchByPrefix(key).orElse(MusicGenre.UNKNOWN));
    });

    private static Optional<MusicGenre> tryMatchByPrefix(String key) {
        return NAME_MAP.keySet().stream()
                .filter(k -> k.startsWith(key) || key.startsWith(k))
                .findFirst()
                .map(NAME_MAP::get);
    }

    public static final Converter<MusicGenre, GenreInfo> genreToInfo = Converter.of(g -> {
        return switch (g) {
            case ROCK -> new GenreInfo(g, "Guitar-driven, energetic");
            case POP -> new GenreInfo(g, "Catchy and mainstream");
            case JAZZ -> new GenreInfo(g, "Improvisational and smooth");
            case CLASSICAL -> new GenreInfo(g, "Orchestral and timeless");
            case HIPHOP -> new GenreInfo(g, "Rhythmic with lyrical flow");
            case ELECTRONIC -> new GenreInfo(g, "Synth-based and modern");
            case BLUES -> new GenreInfo(g, "Soulful and expressive");
            case COUNTRY -> new GenreInfo(g, "Storytelling and acoustic");
            case REGGAE -> new GenreInfo(g, "Relaxed and offbeat");
            case METAL -> new GenreInfo(g, "Heavy and powerful");
            default -> new GenreInfo(MusicGenre.UNKNOWN, "Unknown genre");
        };
    });

    public static final Converter<String, GenreInfo> stringToGenreInfo = stringToGenre.andThen(genreToInfo);
}
