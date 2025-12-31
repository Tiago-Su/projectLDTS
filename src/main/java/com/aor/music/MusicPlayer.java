package com.aor.music;

import javax.sound.sampled.*;

public class MusicPlayer implements AutoCloseable {
    private final Clip clip;

    public MusicPlayer(Clip clip, boolean loop, boolean autoStart) {
        this.clip = clip;

        if (loop) clip.loop(Clip.LOOP_CONTINUOUSLY);
        if (autoStart) start();
        else stop();
    }

    public void start(){
        clip.setFramePosition(0);
        clip.start();
    }

    public void stop(){
        clip.stop();
    }

    @Override
    public void close() {
        if (clip.isRunning()) clip.stop();
        clip.close();
    }
}



