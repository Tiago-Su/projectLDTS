package com.aor.game_loader.loaders;

import com.aor.game.GameData;
import com.aor.elements.BackgroundBlock;
import com.aor.utils.Vector2d;

import java.io.IOException;

public class FlowerLoader implements GameElementLoader {
    @Override
    public BackgroundBlock parse(String data, Vector2d position) throws IOException {
        return new BackgroundBlock(position, new Vector2d(1, 1), 5, 7, GameData.getTileImgPath().get("flower"));
    }
}