package org.example.music.external;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class LyricsClient {
    @Value("${lyrics.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public LyricsClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String fetchLyrics(String bandName, String albumTitle) {
        // Implement lyrics fetching logic
        return restTemplate.getForObject(apiUrl + "/lyrics?band={band}&album={album}",
                String.class, bandName, albumTitle);
    }
}
