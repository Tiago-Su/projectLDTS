package com.aor.game_loader.loaders;

import com.aor.game.GameData;
import com.aor.elements.BackgroundBlock;
import com.aor.utils.Vector2d;

import java.io.IOException;

public class GrassLoader implements GameElementLoader {
    @Override
    public BackgroundBlock parse(String data, Vector2d position) throws IOException {
        String type = "G" + data;
        Vector2d offset = new Vector2d(1, 2);
        int width = 6;
        int height = 6;

        if (data.equals("01")) {
            offset = new Vector2d(1, 4);
            width = 6;
            height = 4;
        }

        return new BackgroundBlock(position, offset, width, height, GameData.getTileImgPath().get(type));
    }
}
