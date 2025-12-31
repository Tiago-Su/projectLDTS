package com.aor.game.state;

import com.aor.game.Arena;
import com.aor.game.Game;
import com.aor.music.MusicPlayer;
import com.aor.utils.Timer;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.graphics.TextImage;
import com.googlecode.lanterna.screen.Screen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

class TestRunningState {
    Game mockedGame;
    RunningState runningState;
    TextGraphics mockedGraphics;
    Screen mockedScreen;
    MusicPlayer mockedMusicPlayer;
    Arena mockedArena;
    Timer mockedTimer;

    @BeforeEach
    void setup() throws IOException {
        mockedGame = Mockito.mock(Game.class);
        mockedGraphics = Mockito.mock(TextGraphics.class);
        mockedScreen = Mockito.mock(Screen.class);
        mockedMusicPlayer = Mockito.mock(MusicPlayer.class);
        mockedArena = Mockito.mock(Arena.class);
        mockedTimer = Mockito.mock(Timer.class);

        Mockito.when(mockedGame.getMusicPlayer()).thenReturn(mockedMusicPlayer);
        Mockito.doNothing().when(mockedMusicPlayer).stop();

        Mockito.when(mockedGraphics.drawImage(Mockito.any(TerminalPosition.class), Mockito.any(TextImage.class))).thenReturn(null);
        Mockito.when(mockedGame.getScreen()).thenReturn(mockedScreen);

        Mockito.doNothing().when(mockedScreen).clear();
        Mockito.doNothing().when(mockedScreen).refresh(Screen.RefreshType.DELTA);

        Mockito.when(mockedGame.getScreen()).thenReturn(mockedScreen);
        Mockito.when(mockedScreen.newTextGraphics()).thenReturn(mockedGraphics);

        Mockito.when(mockedGame.getArena()).thenReturn(mockedArena);
        Mockito.doNothing().when(mockedArena).update(Mockito.any(TextGraphics.class), Mockito.anyDouble());
        Mockito.when(mockedGame.getTransitionTimer()).thenReturn(mockedTimer);

        Mockito.doNothing().when(mockedTimer).update(Mockito.anyDouble());
        Mockito.doNothing().when(mockedGame).resetArena();
        Mockito.doNothing().when(mockedGame).nextArena();

        runningState = new RunningState(mockedGame);
    }

    @Test
    void testInitialState() {
        Mockito.verify(mockedGame, Mockito.times(1)).getMusicPlayer();
        Mockito.verify(mockedMusicPlayer, Mockito.times(1)).start();
    }

    @Test
    void testUpdate1() throws IOException {
        //Level not cleared nor hero dead
        Mockito.when(mockedArena.isHeroDead()).thenReturn(false);
        Mockito.when(mockedArena.isArenaCompleted()).thenReturn(false);

        runningState.update(0);

        Mockito.verify(mockedGame, Mockito.times(3)).getScreen();
        Mockito.verify(mockedGame, Mockito.times(3)).getArena();
        Mockito.verify(mockedGame, Mockito.times(2)).getTransitionTimer();
        Mockito.verify(mockedGame, Mockito.times(0)).resetArena();
        Mockito.verify(mockedGame, Mockito.times(0)).nextArena();

        Mockito.verify(mockedArena, Mockito.times(1)).update(Mockito.any(TextGraphics.class), Mockito.anyDouble());
        Mockito.verify(mockedArena, Mockito.times(1)).isArenaCompleted();
        Mockito.verify(mockedArena, Mockito.times(1)).isHeroDead();

        Mockito.verify(mockedScreen, Mockito.times(1)).newTextGraphics();
        Mockito.verify(mockedScreen, Mockito.times(1)).refresh(Screen.RefreshType.DELTA);
        Mockito.verify(mockedScreen, Mockito.times(1)).clear();

        Mockito.verify(mockedTimer, Mockito.times(1)).isRunning();
        Mockito.verify(mockedTimer, Mockito.times(1)).update(Mockito.anyDouble());
    }

    @Test
    void testUpdate2() throws IOException {
        //Hero dead
        Mockito.when(mockedArena.isHeroDead()).thenReturn(true);
        Mockito.when(mockedArena.isArenaCompleted()).thenReturn(false);


        runningState.update(0);

        Mockito.verify(mockedGame, Mockito.times(3)).getScreen();
        Mockito.verify(mockedGame, Mockito.times(2)).getArena();
        Mockito.verify(mockedGame, Mockito.times(2)).getTransitionTimer();
        Mockito.verify(mockedGame, Mockito.times(1)).resetArena();
        Mockito.verify(mockedGame, Mockito.times(0)).nextArena();

        Mockito.verify(mockedArena, Mockito.times(1)).update(Mockito.any(TextGraphics.class), Mockito.anyDouble());
        Mockito.verify(mockedArena, Mockito.times(0)).isArenaCompleted();
        Mockito.verify(mockedArena, Mockito.times(1)).isHeroDead();

        Mockito.verify(mockedScreen, Mockito.times(1)).newTextGraphics();
        Mockito.verify(mockedScreen, Mockito.times(1)).refresh(Screen.RefreshType.DELTA);
        Mockito.verify(mockedScreen, Mockito.times(1)).clear();

        Mockito.verify(mockedTimer, Mockito.times(1)).isRunning();
        Mockito.verify(mockedTimer, Mockito.times(1)).update(Mockito.anyDouble());
    }

    @Test
    void testUpdate3() throws IOException {
        //Level cleared
        Mockito.when(mockedArena.isHeroDead()).thenReturn(false);
        Mockito.when(mockedArena.isArenaCompleted()).thenReturn(true);

        runningState.update(0);

        Mockito.verify(mockedGame, Mockito.times(3)).getScreen();
        Mockito.verify(mockedGame, Mockito.times(3)).getArena();
        Mockito.verify(mockedGame, Mockito.times(2)).getTransitionTimer();
        Mockito.verify(mockedGame, Mockito.times(0)).resetArena();
        Mockito.verify(mockedGame, Mockito.times(1)).nextArena();

        Mockito.verify(mockedArena, Mockito.times(1)).update(Mockito.any(TextGraphics.class), Mockito.anyDouble());
        Mockito.verify(mockedArena, Mockito.times(1)).isArenaCompleted();
        Mockito.verify(mockedArena, Mockito.times(1)).isHeroDead();

        Mockito.verify(mockedScreen, Mockito.times(1)).newTextGraphics();
        Mockito.verify(mockedScreen, Mockito.times(1)).refresh(Screen.RefreshType.DELTA);
        Mockito.verify(mockedScreen, Mockito.times(1)).clear();

        Mockito.verify(mockedTimer, Mockito.times(1)).isRunning();
        Mockito.verify(mockedTimer, Mockito.times(1)).update(Mockito.anyDouble());
    }

}
