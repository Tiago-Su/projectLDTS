package com.aor.game.state;

import com.aor.game.Game;
import com.aor.game.InputHandler;
import com.aor.music.MusicPlayer;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.graphics.TextImage;
import com.googlecode.lanterna.screen.Screen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.awt.event.KeyEvent;
import java.io.IOException;

class TestMenuState {
    Game mockedGame;
    MenuState menuState;
    TextGraphics mockedGraphics;
    Screen mockedScreen;
    MusicPlayer mockedMusicPlayer;

    @BeforeEach
    void setup() throws IOException {
        mockedGame = Mockito.mock(Game.class);
        mockedGraphics = Mockito.mock(TextGraphics.class);
        mockedScreen = Mockito.mock(Screen.class);
        mockedMusicPlayer = Mockito.mock(MusicPlayer.class);

        Mockito.when(mockedGame.getMusicPlayer()).thenReturn(mockedMusicPlayer);
        Mockito.doNothing().when(mockedMusicPlayer).stop();

        Mockito.when(mockedGraphics.drawImage(Mockito.any(TerminalPosition.class), Mockito.any(TextImage.class))).thenReturn(null);
        Mockito.when(mockedGame.getScreen()).thenReturn(mockedScreen);

        Mockito.doNothing().when(mockedScreen).clear();
        Mockito.doNothing().when(mockedScreen).refresh(Screen.RefreshType.DELTA);

        Mockito.when(mockedGame.getScreen()).thenReturn(mockedScreen);
        Mockito.when(mockedScreen.newTextGraphics()).thenReturn(mockedGraphics);

        Mockito.doNothing().when(mockedGame).setGameState(Mockito.any(GameState.class));
        menuState = new MenuState(mockedGame);
    }

    @Test
    void testUpdate() throws IOException {
        menuState.update(0);

        Mockito.verify(mockedGame, Mockito.times(3)).getScreen();
        Mockito.verify(mockedScreen, Mockito.times(1)).newTextGraphics();
        Mockito.verify(mockedGraphics, Mockito.times(2)).drawImage(Mockito.any(TerminalPosition.class), Mockito.any(TextImage.class));
        Mockito.verify(mockedScreen, Mockito.times(1)).clear();
        Mockito.verify(mockedScreen, Mockito.times(1)).refresh(Screen.RefreshType.DELTA);
    }

    @Test
    void testTransition() {
        menuState.transition(0);
        Mockito.verify(mockedGame, Mockito.times(0)).setGameState(Mockito.any(GameState.class));

        InputHandler.getInstance().readKeys(KeyEvent.VK_SPACE, true);
        menuState.transition(0);
        Mockito.verify(mockedGame, Mockito.times(1)).setGameState(Mockito.any(GameState.class));

    }

}
