package com.aor.game.state;

import com.aor.game.Game;
import com.aor.game.InputHandler;
import com.googlecode.lanterna.screen.Screen;

import java.awt.event.KeyEvent;
import java.io.IOException;

public class RunningState implements GameState {
    private Game game;

    public RunningState(Game game) {
        this.game = game;
        game.getMusicPlayer().start();
    }

    @Override
    public void update(double delta) throws IOException {
        game.getScreen().clear();
        if (!game.getTransitionTimer().isRunning()) game.getArena().update(game.getScreen().newTextGraphics(), delta / 1_000_000_000d);
        game.getScreen().refresh(Screen.RefreshType.DELTA);

        game.getTransitionTimer().update(delta / 1_000_000_000d);
        if (game.getArena().isHeroDead()) game.resetArena();
        else if (game.getArena().isArenaCompleted()) game.nextArena();
    }

    @Override
    public void transition(double delta) {
        if (InputHandler.getInstance().getKeyDown(KeyEvent.VK_ESCAPE)) game.setGameState(new PausedState(game));
    }
}