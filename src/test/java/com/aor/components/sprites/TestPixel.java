package com.aor.components.sprites;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestPixel {
    Pixel pixel;

    @BeforeEach
    void setup() {
        //Color red (255, 0, 0)
        int r = 255 * 256 * 256;
        int g = 0;
        int b = 0;
        pixel = new Pixel(r + g + b);
    }

    @Test
    void testToString() {
        String red = "#ff0000";
        Assertions.assertEquals(red, pixel.toString());
    }

    @Test
    void testEquals() {
        Pixel p = new Pixel(6);
        Assertions.assertEquals(new Pixel(6), p);
        Assertions.assertNotEquals(new Pixel(7), p);
    }
}
