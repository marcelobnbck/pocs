package org.example.music.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AlbumTest {

    @Test
    void testGettersAndSetters() {
        Album album = new Album();
        album.setId(1L);
        album.setTitle("Test Album");

        Band band = new Band();
        album.setBand(band);

        assertEquals(1L, album.getId());
        assertEquals("Test Album", album.getTitle());
        assertEquals(band, album.getBand());
    }
}
