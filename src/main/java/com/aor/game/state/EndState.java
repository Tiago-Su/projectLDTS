package com.aor.game.state;

import com.aor.components.sprites.SolidSprite;
import com.aor.game.Game;
import com.aor.utils.Timer;
import com.aor.utils.Utils;
import com.aor.utils.Vector2d;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;

public class EndState implements GameState {
    private Game game;
    private Timer timer;
    private SolidSprite finalText;

    public EndState(Game game) {
        try {
            this.game = game;
            this.timer = new Timer(3, false);
            game.getMusicPlayer().stop();

            String finalTextPath = Utils.createTempFile("Misc/endText.png");
            finalText = new SolidSprite(new Vector2d(51.5, 61.5), finalTextPath);
            timer.start();

        } catch (IOException e) {
            game.setIsRunning(false);
        }
    }

    @Override
    public void update(double delta) throws IOException {
        game.getScreen().clear();
        TextGraphics graphics = game.getScreen().newTextGraphics();
        finalText.draw(graphics);
        game.getScreen().refresh(Screen.RefreshType.DELTA);
        timer.update(delta / 1_000_000_000d);
    }

    @Override
    public void transition(double delta) {
        if (!timer.isRunning()) game.resetGame();
    }
}
