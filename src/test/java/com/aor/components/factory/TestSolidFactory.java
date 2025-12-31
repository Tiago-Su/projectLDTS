package com.aor.components.factory;

import com.aor.components.sprites.SolidSprite;
import com.aor.utils.Vector2d;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class TestSolidFactory {
    SolidSpriteFactory factory = new SolidSpriteFactory();

    @Test
    void testConstructor1() {
        //Construct without offset
        try {
            SolidSprite sprite = (SolidSprite) factory.newSprite(new Vector2d(1, 1), "src/main/resources/Background/bg000.png");
            SolidSprite expected = new SolidSprite(new Vector2d(1, 1), "src/main/resources/Background/bg000.png");
            Assertions.assertEquals(expected, sprite);

        } catch (IOException e) {
            Assertions.fail();
        }
    }

    @Test
    void testConstructor2() {
        //Construct with offset
        try {
            SolidSprite sprite = (SolidSprite) factory.newSprite(new Vector2d(1, 1), "src/main/resources/Background/bg000.png");
            SolidSprite expected = new SolidSprite(new Vector2d(1, 1), "src/main/resources/Background/bg000.png");
            Assertions.assertEquals(expected, sprite);

        } catch (IOException e) {
            Assertions.fail();
        }
    }
}
