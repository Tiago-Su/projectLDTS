package com.aor.components.sprites;

import com.aor.utils.Vector2d;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.graphics.TextImage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TestTransparentSprite {
    TransparentSprite sprite;
    TransparentSprite spriteWithOffset;
    TextGraphics graphicsStub;

    @BeforeEach
    void setup() {
        graphicsStub = Mockito.mock(TextGraphics.class);

        try {
            sprite = new TransparentSprite(new Vector2d(0, 0), "src/main/resources/Hero/Running/running0.png");
            spriteWithOffset = new TransparentSprite(new Vector2d(0, 0), new Vector2d(1, 1), "src/main/resources/Hero/Running/running0.png");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGetters1() {
        //Test getters with normal image
        sprite.normal();
        spriteWithOffset.normal();

        Assertions.assertEquals(8, sprite.getHeight());
        Assertions.assertEquals(8, spriteWithOffset.getHeight());

        Assertions.assertEquals(7, sprite.getWidth());
        Assertions.assertEquals(7, spriteWithOffset.getWidth());

        Assertions.assertEquals(new Vector2d(0, 0), sprite.getPosition());
        Assertions.assertEquals(new Vector2d(1, 1), spriteWithOffset.getPosition());
    }

    @Test
    void testGetters2() {
        //Test getters with the image flipped
        sprite.xFlip();
        spriteWithOffset.xFlip();

        Assertions.assertEquals(8, sprite.getHeight());
        Assertions.assertEquals(8, spriteWithOffset.getHeight());

        Assertions.assertEquals(7, sprite.getWidth());
        Assertions.assertEquals(7, spriteWithOffset.getWidth());

        Assertions.assertEquals(new Vector2d(0, 0), sprite.getPosition());
        Assertions.assertEquals(new Vector2d(1, 1), spriteWithOffset.getPosition());
    }

    @Test
    void testDraw1() {
        //Drawing with normal image - there is only 37 pixels
        sprite.normal();

        Mockito.when(graphicsStub.drawImage(Mockito.any(TerminalPosition.class), Mockito.any(TextImage.class))).thenReturn(null);
        sprite.draw(graphicsStub);

        Mockito.verify(graphicsStub, Mockito.times(37)).setBackgroundColor(Mockito.any(TextColor.class));
        Mockito.verify(graphicsStub, Mockito.times(37)).setCharacter(Mockito.any(TerminalPosition.class), Mockito.anyChar());
    }

    @Test
    void testDraw2() {
        //Drawing with flipped image - there is only 37 pixels
        sprite.xFlip();

        Mockito.when(graphicsStub.drawImage(Mockito.any(TerminalPosition.class), Mockito.any(TextImage.class))).thenReturn(null);
        sprite.draw(graphicsStub);

        Mockito.verify(graphicsStub, Mockito.times(37)).setBackgroundColor(Mockito.any(TextColor.class));
        Mockito.verify(graphicsStub, Mockito.times(37)).setCharacter(Mockito.any(TerminalPosition.class), Mockito.anyChar());
    }
}
