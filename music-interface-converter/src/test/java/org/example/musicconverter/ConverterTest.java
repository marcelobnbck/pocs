package org.example.musicconverter;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ConverterTest {
    @Test
    void testAndThenComposition() {
        Converter<String, Integer> lengthConverter = String::length;
        Converter<Integer, String> toString = Object::toString;
        Converter<String, String> composed = lengthConverter.andThen(toString);
        assertEquals("5", composed.convert("Hello"));
    }

    @Test
    void testCompose() {
        Converter<String, String> trim = String::trim;
        Converter<String, Integer> length = String::length;
        Converter<String, Integer> composed = length.compose(trim);
        assertEquals(2, composed.convert(" hi "));
    }
}
