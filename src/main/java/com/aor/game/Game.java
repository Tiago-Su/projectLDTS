package com.aor.game;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;

import com.aor.game_loader.ArenaLoader;
import com.aor.game.state.*;
import com.aor.music.MusicPlayer;
import com.aor.utils.Timer;
import com.aor.utils.Utils;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFrame;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Game {
    private Terminal terminal;
    private Screen screen;

    private boolean isRunning;
    private GameState gameState;

    private Arena arena;
    private Queue<String> arenaMaps;
    private Queue<String> backgroundMaps;

    private Timer transitionTimer;
    private MusicPlayer musicPlayer;

    public void startGame() {
        setup();
        startLoop();
    }

    private void setup() {
        try {
            Font font = Utils.loadSquaredFont();
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
            terminal = Utils.setupTerminal(font);

            ((AWTTerminalFrame)terminal).getComponent(0).addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    InputHandler.getInstance().readKeys(e.getKeyCode(), true);
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    InputHandler.getInstance().readKeys(e.getKeyCode(), false);
                }
            });

            screen = new TerminalScreen(terminal);

            //Basic Setup
            screen.setCursorPosition(null);
            screen.startScreen();
            screen.doResizeIfNecessary();
            screen.refresh();

            isRunning = true;
            arenaMaps = new ArrayDeque<>();
            backgroundMaps = new ArrayDeque<>();

            resetGame();

            transitionTimer = new Timer(0.5, false);

            Clip clip = AudioSystem.getClip();
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(GameData.getSfxFiles().get("ost1"));
            clip.open(audioInputStream);

            musicPlayer = new MusicPlayer(clip, true, false);

        } catch (Exception e) {
            e.printStackTrace();
            isRunning = false;
        }
    }

    private void startLoop() {
        double frames = 60;
        double framePerNanoSecond = 1_000_000_000d / frames;
        double previousTime = System.nanoTime();

        while (isRunning) {
            double currentTime = System.nanoTime();
            double delta = currentTime - previousTime;

            checkIsUserClosedGame();

            if (delta >= framePerNanoSecond) {
                previousTime = currentTime;
                update(delta);

            } else {
                double sleepTime = ((framePerNanoSecond - delta) / 1000000);

                if (sleepTime > 0) {
                    try {
                        Thread.sleep((long) sleepTime);

                    } catch (InterruptedException e) {
                        isRunning = false;
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        close();
    }

    private void checkIsUserClosedGame() {
        try {
            KeyStroke key = screen.pollInput();
            if (key != null && key.getKeyType().equals(KeyType.EOF)) isRunning = false;
        } catch (IOException e) {
            isRunning = false;
            e.printStackTrace();
            System.exit(0);
        }
    }

    private void update(double delta) {
        try {
            gameState.update(delta);
            gameState.transition(delta);
        } catch (Exception e) {
            isRunning = false;
        }
    }

    public void resetGame() {
        for (int i = 1; i < 6; i++) {
            String mapPath = Utils.createTempFile("Maps/map" + i + ".csv");
            String backgroundPath = Utils.createTempFile("Maps/bg" + i + ".csv");
            arenaMaps.add(mapPath);
            backgroundMaps.add(backgroundPath);
        }

        try {
            arena = new ArenaLoader().arenaBuilder(arenaMaps.peek(), backgroundMaps.peek());
            gameState = new MenuState(this);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void resetArena() throws IOException {
        arena = new ArenaLoader().arenaBuilder(arenaMaps.peek(), backgroundMaps.peek());
        transitionTimer.start();
    }

    public void nextArena() throws IOException {
        arenaMaps.poll();
        backgroundMaps.poll();
        String newArena = arenaMaps.peek();

        if (newArena != null) {
            arena = new ArenaLoader().arenaBuilder(arenaMaps.peek(), backgroundMaps.peek());
            transitionTimer.start();

        } else {
            gameState = new EndState(this);
        }
    }

    private void close() {
        if (screen == null) return;
        try {
            screen.close();
            terminal.close();

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    //Getters
    public Screen getScreen() {
        return screen;
    }
    public Timer getTransitionTimer() {
        return transitionTimer;
    }
    public Arena getArena() {
        return arena;
    }
    public MusicPlayer getMusicPlayer() {
        return musicPlayer;
    }

    //Setters
    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public void setGameState(GameState newGameState) {
        gameState = newGameState;
    }
}
