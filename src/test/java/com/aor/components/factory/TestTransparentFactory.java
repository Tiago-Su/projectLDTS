package com.aor.components.factory;

import com.aor.components.sprites.TransparentSprite;
import com.aor.utils.Vector2d;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class TestTransparentFactory {
    TransparentSpriteFactory factory = new TransparentSpriteFactory();

    @Test
    void testConstructor1() {
        //Construct without offset
        try {
            TransparentSprite sprite = (TransparentSprite) factory.newSprite(new Vector2d(1, 1), "src/main/resources/Background/bg000.png");
            TransparentSprite expected = new TransparentSprite(new Vector2d(1, 1), "src/main/resources/Background/bg000.png");
            Assertions.assertEquals(expected, sprite);

        } catch (IOException e) {
            Assertions.fail();
        }
    }

    @Test
    void testConstructor2() {
        //Construct with offset
        try {
            TransparentSprite sprite = (TransparentSprite) factory.newSprite(new Vector2d(1, 1), "src/main/resources/Background/bg000.png");
            TransparentSprite expected = new TransparentSprite(new Vector2d(1, 1), "src/main/resources/Background/bg000.png");
            Assertions.assertEquals(expected, sprite);

        } catch (IOException e) {
            Assertions.fail();
        }
    }
}
