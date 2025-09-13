package org.example.music.service;

import org.example.music.entity.Band;
import org.example.music.exception.NotFoundException;
import org.example.music.external.LyricsClient;
import org.example.music.repository.BandRepository;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BandServiceTest {

    @Mock
    private BandRepository repository;
    @Mock
    private LyricsClient lyricsClient;

    @InjectMocks
    private BandService service;

    private Band band;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        band = new Band();
        band.setId(1L);
        band.setName("Test Band");
        band.setGenre("Rock");
        band.setFormedIn(java.time.Year.of(2000));
    }

    @Test
    void testGetById_found() {
        when(repository.findById(1L)).thenReturn(Optional.of(band));
        Band result = service.getById(1L);
        assertEquals("Test Band", result.getName());
    }

    @Test
    void testGetById_notFound() {
        when(repository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> service.getById(2L));
    }

    @Test
    void testListAll() {
        when(repository.findAll()).thenReturn(List.of(band));
        List<Band> bands = service.listAll();
        assertEquals(1, bands.size());
    }

    @Test
    void testCreate() {
        when(repository.save(band)).thenReturn(band);
        Band result = service.create(band);
        assertEquals("Test Band", result.getName());
    }

    @Test
    void testUpdate() {
        Band updated = new Band();
        updated.setName("Updated Band");
        updated.setGenre("Pop");
        updated.setFormedIn(java.time.Year.of(2010));

        when(repository.findById(1L)).thenReturn(Optional.of(band));
        when(repository.save(any(Band.class))).thenReturn(updated);

        Band result = service.update(1L, updated);
        assertEquals("Updated Band", result.getName());
        assertEquals("Pop", result.getGenre());
        // FIX: Extract the int value from the java.time.Year object
        assertEquals(2010, result.getFormedIn().getValue());
    }

    @Test
    void testDelete() {
        doNothing().when(repository).deleteById(1L);
        service.delete(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testGetLyrics() {
        when(repository.findById(1L)).thenReturn(Optional.of(band));
        when(lyricsClient.fetchLyrics("Test Band", "Test Album")).thenReturn("Lyrics");
        String lyrics = service.getLyrics(1L, "Test Album");
        assertEquals("Lyrics", lyrics);
    }
}