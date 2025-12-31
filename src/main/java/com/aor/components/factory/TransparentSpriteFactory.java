package com.aor.components.factory;

import com.aor.components.sprites.GenericSprite;
import com.aor.components.sprites.TransparentSprite;
import com.aor.utils.Vector2d;

import java.io.IOException;

public class TransparentSpriteFactory implements GenericSpriteFactory {
    @Override
    public GenericSprite newSprite(Vector2d position, String filePath) throws IOException {
        return new TransparentSprite(position, filePath);
    }

    @Override
    public GenericSprite newSprite(Vector2d position, Vector2d offset, String filePath) throws IOException {
        return new TransparentSprite(position, offset, filePath);
    }
}
