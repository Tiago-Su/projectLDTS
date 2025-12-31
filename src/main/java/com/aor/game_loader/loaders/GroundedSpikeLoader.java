package com.aor.game_loader.loaders;

import com.aor.elements.GroundedSpike;
import com.aor.utils.Vector2d;

import java.io.IOException;

public class GroundedSpikeLoader implements GameElementLoader {
    @Override
    public GroundedSpike parse(String data, Vector2d pos) throws IOException {// format
        return new GroundedSpike(pos);
    }
}
