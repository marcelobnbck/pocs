package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    @Test
    void testSum() {
        App app = new App();
        assertEquals(5, app.sum(2, 3));
    }

    @Test
    void testIsEven() {
        App app = new App();
        assertTrue(app.isEven(4));
        assertFalse(app.isEven(5));
    }
}

