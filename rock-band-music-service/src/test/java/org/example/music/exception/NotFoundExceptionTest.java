package org.example.music.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NotFoundExceptionTest {

    @Test
    void shouldCreateNotFoundExceptionWithMessage() {
        // Arrange
        String errorMessage = "Resource not found";

        // Act
        NotFoundException exception = new NotFoundException(errorMessage);

        // Assert
        assertEquals(errorMessage, exception.getMessage());
        assertTrue(exception instanceof RuntimeException);
    }
}
