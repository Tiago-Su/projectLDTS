package com.aor.music;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.sound.sampled.*;

class TestMusicPlayer {
    MusicPlayer musicPlayer;
    Clip mockedClip;

    @BeforeEach
    void setup() {
        mockedClip = Mockito.mock(Clip.class);
        Mockito.doNothing().when(mockedClip).loop(Mockito.anyInt());
        Mockito.doNothing().when(mockedClip).setFramePosition(Mockito.anyInt());
        Mockito.doNothing().when(mockedClip).start();
        Mockito.doNothing().when(mockedClip).stop();

        musicPlayer = new MusicPlayer(mockedClip, true, false);
    }

    @Test
    void testInitialState() {
        Mockito.verify(mockedClip, Mockito.times(1)).loop(Mockito.anyInt());
    }

    @Test
    void testStart() {
        musicPlayer.start();
        Mockito.verify(mockedClip, Mockito.times(1)).setFramePosition(Mockito.anyInt());
        Mockito.verify(mockedClip, Mockito.times(1)).stop();
    }

    @Test
    void testStop() {
        musicPlayer.stop();
        Mockito.verify(mockedClip, Mockito.times(2)).stop();
    }

    @Test
    void testClose() {
        musicPlayer.close();
        Mockito.verify(mockedClip, Mockito.times(1)).close();
    }

}
