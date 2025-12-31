package com.aor.game_loader.loaders;

import com.aor.elements.EndBlock;
import com.aor.utils.Vector2d;

import java.io.IOException;

public class EndBlockLoader implements GameElementLoader {

    @Override
    public EndBlock parse(String data, Vector2d pos) throws IOException {
        return new EndBlock(pos);
    }
}
