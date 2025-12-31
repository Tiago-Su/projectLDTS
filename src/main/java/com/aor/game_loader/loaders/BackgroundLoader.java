package com.aor.game_loader.loaders;

import com.aor.game.GameData;
import com.aor.elements.BackgroundBlock;
import com.aor.utils.Vector2d;

import java.io.IOException;

public class BackgroundLoader implements GameElementLoader {
    @Override
    public BackgroundBlock parse(String data, Vector2d position) throws IOException {
        String type = "F" + data;
        return new BackgroundBlock(position, GameData.getTileImgPath().get(type));
    }
}
