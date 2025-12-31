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

class TestPausedState {
    Game mockedGame;
    PausedState pausedState;
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
        pausedState = new PausedState(mockedGame);
    }

    @Test
    void testTransition() {
        pausedState.transition(0);
        Mockito.verify(mockedGame, Mockito.times(0)).setGameState(Mockito.any(GameState.class));

        InputHandler.getInstance().readKeys(KeyEvent.VK_ESCAPE, true);
        pausedState.transition(0);
        Mockito.verify(mockedGame, Mockito.times(1)).setGameState(Mockito.any(GameState.class));

    }
}
