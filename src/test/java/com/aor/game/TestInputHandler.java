package com.aor.game;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;

class TestInputHandler {
    @Test
    void testGetInstance() {
        Assertions.assertNotEquals(null, InputHandler.getInstance());
    }

    @Test
    void testKeyPressed() {
        //Pressed space
        InputHandler.getInstance().readKeys(KeyEvent.VK_SPACE, true);
        Assertions.assertTrue(InputHandler.getInstance().getKeyPressed(KeyEvent.VK_SPACE));
    }

    @Test
    void testKeyPressedDown() {
        //Pressed space
        InputHandler.getInstance().readKeys(KeyEvent.VK_SPACE, true);
        Assertions.assertTrue(InputHandler.getInstance().getKeyDown(KeyEvent.VK_SPACE));
        Assertions.assertFalse(InputHandler.getInstance().getKeyDown(KeyEvent.VK_SPACE));
    }

    @Test
    void testKeyReleased() {
        //Pressed space
        InputHandler.getInstance().readKeys(KeyEvent.VK_SPACE, true);
        Assertions.assertTrue(InputHandler.getInstance().getKeyPressed(KeyEvent.VK_SPACE));

        InputHandler.getInstance().readKeys(KeyEvent.VK_SPACE, false);
        Assertions.assertFalse(InputHandler.getInstance().getKeyPressed(KeyEvent.VK_SPACE));
    }
}
