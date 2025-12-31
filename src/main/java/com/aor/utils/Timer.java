package com.aor.utils;

public class Timer {
    private double remainingTime;
    private final double duration;
    private boolean running = false;

    public Timer(double duration, boolean autoStart) {
        this.duration = duration;
        if (autoStart) start();
    }

    public void start() {
        running = true;
        remainingTime = duration;
    }

    public boolean isRunning() {
        return running;
    }

    public void interrupt() {
        running = false;
        remainingTime = 0f;
    }

    public void update(double delta) {
        if (running) remainingTime -= delta;
        if (remainingTime <= 0) interrupt();
    }

    public double getRemainingTime() {
        return remainingTime;
    }
}
