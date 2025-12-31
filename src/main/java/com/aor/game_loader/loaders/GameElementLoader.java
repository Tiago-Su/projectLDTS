package com.aor.game_loader.loaders;

import com.aor.elements.GameElement;
import com.aor.utils.Vector2d;

import java.io.IOException;

public interface GameElementLoader {
    GameElement parse(String data, Vector2d pos) throws IOException;
}
