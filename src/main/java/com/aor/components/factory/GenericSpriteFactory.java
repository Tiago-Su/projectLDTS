package com.aor.components.factory;

import com.aor.components.sprites.GenericSprite;
import com.aor.utils.Vector2d;

import java.io.IOException;

public interface  GenericSpriteFactory {
    GenericSprite newSprite(Vector2d position, String filePath) throws IOException;
    GenericSprite newSprite(Vector2d position, Vector2d offset, String filePath) throws IOException;
}
