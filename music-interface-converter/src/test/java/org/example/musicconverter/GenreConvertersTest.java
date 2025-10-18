package org.example.musicconverter;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GenreConvertersTest {
    @Test
    void testStringToGenre() {
        assertEquals(MusicGenre.ROCK, GenreConverters.stringToGenre.convert("rock"));
        assertEquals(MusicGenre.HIPHOP, GenreConverters.stringToGenre.convert("hip-hop"));
        assertEquals(MusicGenre.UNKNOWN, GenreConverters.stringToGenre.convert("unknownstyle"));
    }

    @Test
    void testGenreToInfo() {
        GenreInfo info = GenreConverters.genreToInfo.convert(MusicGenre.JAZZ);
        assertEquals("Improvisational and smooth", info.description());
    }

    @Test
    void testStringToGenreInfo() {
        GenreInfo info = GenreConverters.stringToGenreInfo.convert("pop");
        assertEquals(MusicGenre.POP, info.genre());
        assertTrue(info.description().contains("Catchy"));
    }
}
