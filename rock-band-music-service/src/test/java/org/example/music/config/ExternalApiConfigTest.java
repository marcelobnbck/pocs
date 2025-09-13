package org.example.music.config;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExternalApiConfigTest {

    @Test
    void restTemplateBeanShouldBeCreated() {
        // Arrange
        ExternalApiConfig config = new ExternalApiConfig();

        // Act
        RestTemplate restTemplate = config.restTemplate();

        // Assert
        assertNotNull(restTemplate, "RestTemplate bean should not be null");
        assertTrue(restTemplate instanceof RestTemplate, "Bean should be instance of RestTemplate");
    }
}
