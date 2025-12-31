package com.aor.components.factory;

import com.aor.components.sprites.GenericSprite;
import com.aor.components.sprites.SolidSprite;
import com.aor.utils.Vector2d;

import java.io.IOException;

public class SolidSpriteFactory implements GenericSpriteFactory {
    @Override
    public GenericSprite newSprite(Vector2d position, String filePath) throws IOException {
        return new SolidSprite(position, filePath);
    }

    @Override
    public GenericSprite newSprite(Vector2d position, Vector2d offset, String filePath) throws IOException {
        return new SolidSprite(position, offset, filePath);
    }
}
