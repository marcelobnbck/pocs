package org.example.music.external;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class LyricsClientTest {

    @Mock
    private RestTemplate restTemplate;

    private LyricsClient lyricsClient;

    private static final String API_URL = "http://api.example.com";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        lyricsClient = new LyricsClient(restTemplate);
        ReflectionTestUtils.setField(lyricsClient, "apiUrl", API_URL);
    }

    @Test
    void fetchLyrics_ShouldReturnLyrics_WhenGivenBandAndAlbum() {
        // Arrange
        String bandName = "Metallica";
        String albumTitle = "Master of Puppets";
        String expectedLyrics = "Some lyrics content";
        String expectedUrl = API_URL + "/lyrics?band={band}&album={album}";
        when(restTemplate.getForObject(
                eq(expectedUrl),
                eq(String.class),
                eq(bandName),
                eq(albumTitle)
        )).thenReturn(expectedLyrics);

        // Act
        String actualLyrics = lyricsClient.fetchLyrics(bandName, albumTitle);

        // Assert
        assertThat(actualLyrics).isEqualTo(expectedLyrics);
    }

    @Test
    void fetchLyrics_ShouldReturnEmptyString_WhenApiReturnsNull() {

        // Arrange
        String bandName = "Metallica";
        String albumTitle = "Master of Puppets";
        String expectedUrl = API_URL + "/lyrics?band={band}&album={album}";
        when(restTemplate.getForObject(
                eq(expectedUrl),
                eq(String.class),
                eq(bandName),
                eq(albumTitle)
        )).thenReturn(null);

        // Act
        String actualLyrics = lyricsClient.fetchLyrics(bandName, albumTitle);

        // Assert
        assertThat(actualLyrics).isNull();

    }

    @Test
    void fetchLyrics_ShouldHandleEmptyBandAndAlbum() {

        // Arrange
        String bandName = "";
        String albumTitle = "";
        String expectedLyrics = "No lyrics found";
        String expectedUrl = API_URL + "/lyrics?band={band}&album={album}";
        when(restTemplate.getForObject(
                eq(expectedUrl),
                eq(String.class),
                eq(bandName),
                eq(albumTitle)
        )).thenReturn(expectedLyrics);

        // Act
        String actualLyrics = lyricsClient.fetchLyrics(bandName, albumTitle);

        // Assert
        assertThat(actualLyrics).isEqualTo(expectedLyrics);
    }
}