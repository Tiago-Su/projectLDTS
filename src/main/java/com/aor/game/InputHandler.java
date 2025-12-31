package com.aor.game;
import java.util.HashMap;
import java.util.Map;

public class InputHandler {
    private static InputHandler instance;
    private final Map<Integer, KeyState> keysPressed;

    enum KeyState {
        UP, //Key not pressed
        DOWN, //Key pressed
        CONSUMED //Key pressed and consumed but not released yet
    }

    private InputHandler() {
        keysPressed = new HashMap<>();
    }

    public static InputHandler getInstance() {
        if (instance == null) instance = new InputHandler();
        return instance;
    }

    public void readKeys(int key, boolean isPressed) {
        if (isPressed) {
            if (keysPressed.get(key) != KeyState.CONSUMED) keysPressed.put(key, KeyState.DOWN);

        } else {
            keysPressed.put(key, KeyState.UP);
        }
    }

    public boolean getKeyPressed(int key) {
        return (keysPressed.get(key) != null) && (keysPressed.get(key) != KeyState.UP);
    }

    public boolean getKeyDown(int key) {
        if (keysPressed.get(key) != null && keysPressed.get(key) == KeyState.DOWN) {
            keysPressed.put(key, KeyState.CONSUMED);
            return true;
        }

        return false;
    }
}
