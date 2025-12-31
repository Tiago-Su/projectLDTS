package com.aor.game_loader.loaders;

import com.aor.tile.*;
import com.aor.game.GameData;
import com.aor.utils.Vector2d;

import java.io.IOException;

public class TileLoader implements GameElementLoader {
    @Override
    public Tile parse(String data, Vector2d pos) throws IOException {
        String id = 'T' + data;
        return new Tile(pos, GameData.getTileImgPath().get(id), GameData.TILE_TAG);
    }
}
