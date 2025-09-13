package org.example.music.entity;

import org.junit.jupiter.api.Test;
import java.time.Year;
import java.util.List;

import static java.util.List.*;
import static org.junit.jupiter.api.Assertions.*;

class BandTest {

    @Test
    void testGettersAndSetters() {
        Band band = new Band();
        band.setId(1L);
        band.setName("The Example Band");
        band.setGenre("Rock");
        band.setFormedIn(Year.of(2000));

        assertEquals(1L, band.getId());
        assertEquals("The Example Band", band.getName());
        assertEquals("Rock", band.getGenre());
        assertEquals(Year.of(2000), band.getFormedIn());

        Album album = new Album();
        band.setAlbums(of(album));
        assertEquals(1, band.getAlbums().size());
        assertEquals(album, band.getAlbums().get(0));
    }

}