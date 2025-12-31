package com.aor.game.state;

import com.aor.game.Game;
import com.aor.game.InputHandler;

import java.awt.event.KeyEvent;
import java.io.IOException;

public class PausedState implements GameState {
    private final Game game;

    public PausedState(Game game) {
        this.game = game;
    }

    @Override
    public void update(double delta) throws IOException {}

    @Override
    public void transition(double delta) {
        if (InputHandler.getInstance().getKeyDown(KeyEvent.VK_ESCAPE)) game.setGameState(new RunningState(game));
    }
}
