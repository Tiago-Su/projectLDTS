package com.aor.game_loader.loaders;

import com.aor.elements.Strawberry;
import com.aor.utils.Vector2d;

import java.io.IOException;

public class StraberryLoader implements GameElementLoader {
    @Override
    public Strawberry parse(String data, Vector2d pos) throws IOException {
        return new Strawberry(pos);
    }
}
