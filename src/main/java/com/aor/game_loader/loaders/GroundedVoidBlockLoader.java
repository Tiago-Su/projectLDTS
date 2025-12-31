package com.aor.game_loader.loaders;

import com.aor.elements.GroundVoidBlock;
import com.aor.utils.Vector2d;

import java.io.IOException;

public class GroundedVoidBlockLoader implements GameElementLoader {
    @Override
    public GroundVoidBlock parse(String data, Vector2d pos) throws IOException {
        return new GroundVoidBlock(pos);
    }
}
