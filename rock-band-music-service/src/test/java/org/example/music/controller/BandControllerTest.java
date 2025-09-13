package org.example.music.controller;

import org.example.music.entity.Band;
import org.example.music.service.BandService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BandControllerTest {

    @Mock
    private BandService service;

    @InjectMocks
    private BandController controller;

    private Band band;

    @BeforeEach
    void setUp() {
        band = new Band();
        band.setId(1L);
        band.setName("Test Band");
    }

    @Test
    void list_ShouldReturnAllBands() {
        when(service.listAll()).thenReturn(List.of(band));

        List<Band> result = controller.list();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(service).listAll();
    }

    @Test
    void get_ShouldReturnBandById() {
        when(service.getById(1L)).thenReturn(band);

        Band result = controller.get(1L);

        assertNotNull(result);
        assertEquals(band.getId(), result.getId());
        verify(service).getById(1L);
    }

    @Test
    void create_ShouldReturnCreatedBand() {
        when(service.create(band)).thenReturn(band);

        Band result = controller.create(band);

        assertNotNull(result);
        assertEquals(band.getId(), result.getId());
        verify(service).create(band);
    }

    @Test
    void update_ShouldReturnUpdatedBand() {
        when(service.update(1L, band)).thenReturn(band);

        Band result = controller.update(1L, band);

        assertNotNull(result);
        assertEquals(band.getId(), result.getId());
        verify(service).update(1L, band);
    }

    @Test
    void delete_ShouldCallServiceDelete() {
        doNothing().when(service).delete(1L);

        controller.delete(1L);

        verify(service).delete(1L);
    }

    @Test
    void lyrics_ShouldReturnLyrics() {
        String expectedLyrics = "Test lyrics";
        when(service.getLyrics(1L, "Test Song")).thenReturn(expectedLyrics);

        String result = controller.lyrics(1L, "Test Song");

        assertEquals(expectedLyrics, result);
        verify(service).getLyrics(1L, "Test Song");
    }
}
