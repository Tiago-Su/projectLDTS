package com.aor.game.state;

import com.aor.game.Game;
import com.aor.music.MusicPlayer;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.graphics.TextImage;
import com.googlecode.lanterna.screen.Screen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

class TestEndState {
    Game mockedGame;
    EndState endState;
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

        endState = new EndState(mockedGame);
    }

    @Test
    void testInitialState() {
        Mockito.verify(mockedMusicPlayer, Mockito.times(1)).stop();
        Mockito.verify(mockedGame, Mockito.times(1)).getMusicPlayer();
    }

    @Test
    void testUpdate() throws IOException {
        endState.update(0);

        Mockito.verify(mockedGame, Mockito.times(3)).getScreen();
        Mockito.verify(mockedGraphics, Mockito.times(1)).drawImage(Mockito.any(TerminalPosition.class), Mockito.any(TextImage.class));
        Mockito.verify(mockedScreen, Mockito.times(1)).newTextGraphics();
        Mockito.verify(mockedScreen, Mockito.times(1)).refresh(Screen.RefreshType.DELTA);
        Mockito.verify(mockedScreen, Mockito.times(1)).clear();
    }
}
