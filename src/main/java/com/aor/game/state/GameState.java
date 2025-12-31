package com.aor.game.state;

import java.io.IOException;

public interface GameState {
    void update(double delta) throws IOException;
    void transition(double delta);
}
