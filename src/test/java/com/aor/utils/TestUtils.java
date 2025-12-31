package com.aor.utils;

import com.aor.components.sprites.Pixel;
import com.googlecode.lanterna.terminal.Terminal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class TestUtils {
    @Test
    void testCreateTempFile() {
        //Checking if exception is thrown
        try {
            Utils.createTempFile("Background/bg000.png");

        } catch (RuntimeException e) {
            Assertions.fail();
        }
    }

    @Test
    void testXFlipPixels() {
        List<List<Pixel>> pixelsToFlip = new ArrayList<>();
        List<List<Pixel>> expected = new ArrayList<>();

        List<Pixel> line1 = new ArrayList<>();
        line1.add(new Pixel(1));
        line1.add(new Pixel(2));
        line1.add(new Pixel(3));
        pixelsToFlip.add(line1);

        List<Pixel> line2 = new ArrayList<>();
        line2.add(new Pixel(4));
        line2.add(new Pixel(5));
        line2.add(new Pixel(6));
        pixelsToFlip.add(line2);

        List<Pixel> flipped1 = new ArrayList<>();
        flipped1.add(new Pixel(3));
        flipped1.add(new Pixel(2));
        flipped1.add(new Pixel(1));
        expected.add(flipped1);

        List<Pixel> flipped2 = new ArrayList<>();
        flipped2.add(new Pixel(6));
        flipped2.add(new Pixel(5));
        flipped2.add(new Pixel(4));
        expected.add(flipped2);

        List<List<Pixel>> flippedPixels = Utils.xFlipPixels(pixelsToFlip);
        for (int i = 0; i < flippedPixels.size(); i++) {
            for (int j = 0; j < flippedPixels.get(i).size(); j++) {
                Assertions.assertEquals(expected.get(i).get(j), flippedPixels.get(i).get(j));
            }
        }
    }

    @Test
    void testLoadTransparentImage() {
        //Checking if exception is thrown
        try {
            Utils.loadTransparentImage("src/main/resources/Background/bg000.png");

        } catch (IOException e) {
            Assertions.fail();
        }
    }

    @Test
    void testLoadXFlippedTransparentImage() {
        //Checking if exception is thrown
        try {
            Utils.loadXFlippedTransparentImage("src/main/resources/Background/bg000.png");

        } catch (IOException e) {
            Assertions.fail();
        }
    }

    @Test
    void testLoadImage() {
        //Checking if exception is thrown
        try {
            Utils.loadImage("src/main/resources/Background/bg000.png");

        } catch (IOException e) {
            Assertions.fail();
        }
    }

    @Test
    void testLoadXFlippedImage() {
        //Checking if exception is thrown
        try {
            Utils.loadXFlippedImage("src/main/resources/Background/bg000.png");

        } catch (IOException e) {
            Assertions.fail();
        }
    }

    @Test
    void testLoadPixels() {
        //Checking if exception is thrown
        try {
            Utils.loadPixels("src/main/resources/Background/bg000.png");

        } catch (IOException e) {
            Assertions.fail();
        }
    }

    @Test
    void testLoadSquaredFont() {
        try {
            Utils.loadSquaredFont();

        } catch (IOException | FontFormatException e) {
            Assertions.fail();
        }
    }

    @Test
    void testSetupTerminal() {
        try {
            String path = "src/main/resources/Fonts/square.ttf";
            Font font = Font.createFont(Font.TRUETYPE_FONT, new File(path));
            Terminal terminal = Utils.setupTerminal(font);
            terminal.close();

        } catch (FontFormatException | IOException e) {
            Assertions.fail();
        }
    }

    @Test
    void testMoveTowards1() {
        double current = 5;
        double target = 10;
        double delta = 1;

        Assertions.assertEquals(6, Utils.moveTowards(current, target, delta));
    }

    @Test
    void testMoveTowards2() {
        double current = 5;
        double target = 10;
        double delta = 6;

        Assertions.assertEquals(10, Utils.moveTowards(current, target, delta));
    }

    @Test
    void testMoveTowards3() {
        double current = 5;
        double target = 10;
        double delta = -1;

        Assertions.assertEquals(4, Utils.moveTowards(current, target, delta));
    }

    @Test
    void testSumVector() {
        Vector2d v1 = new Vector2d(1, 1);
        Vector2d v2 = new Vector2d(5, 2);

        Assertions.assertEquals(new Vector2d(6, 3), Utils.vectorSum(v1, v2));
    }

    @Test
    void testSubVector() {
        Vector2d v1 = new Vector2d(1, 1);
        Vector2d v2 = new Vector2d(5, 2);

        Assertions.assertEquals(new Vector2d(-4, -1), Utils.vectorSub(v1, v2));
    }

    @Test
    void testScalarProduct() {
        Vector2d v1 = new Vector2d(1, 1);
        int scalar = 5;

        Assertions.assertEquals(new Vector2d(5, 5), Utils.scalarProduct(v1, scalar));
    }

    @Test
    void testNormalize1() {
        Vector2d v1 = new Vector2d(1, 1);
        Vector2d normalizedVector = new Vector2d(1 / Math.sqrt(2), 1 / Math.sqrt(2));
        Assertions.assertEquals(normalizedVector, Utils.normalizedVector(v1));
    }

    @Test
    void testNormalize2() {
        Vector2d v2 = new Vector2d(0, 0);
        v2.normalize();
        Assertions.assertEquals(new Vector2d(0, 0), v2.normalize());
    }

}
