package com.aor.game.state;

import com.aor.components.sprites.SolidSprite;
import com.aor.game.Game;
import com.aor.game.InputHandler;
import com.aor.utils.Utils;
import com.aor.utils.Vector2d;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

import java.awt.event.KeyEvent;
import java.io.IOException;

public class MenuState implements GameState {
    private Game game;
    private SolidSprite menuSprite;
    private SolidSprite textMenu;

    public MenuState(Game game) {
        try {
            this.game = game;

            String menuSpritePath = Utils.createTempFile("Misc/titleScreen.png");
            String textMenuPath = Utils.createTempFile("Misc/titleText.png");

            menuSprite = new SolidSprite(new Vector2d(36.5, 30), menuSpritePath);
            textMenu = new SolidSprite(new Vector2d(43, 80), textMenuPath);
        } catch (IOException e) {
            game.setIsRunning(false);
        }
    }

    @Override
    public void update(double delta) throws IOException {
        game.getScreen().clear();

        TextGraphics graphics = game.getScreen().newTextGraphics();
        menuSprite.draw(graphics);
        textMenu.draw(graphics);

        game.getScreen().refresh(Screen.RefreshType.DELTA);
    }

    @Override
    public void transition(double delta) {
        if (InputHandler.getInstance().getKeyPressed(KeyEvent.VK_SPACE)) {
            game.setGameState(new RunningState(game));
        }
    }
}
