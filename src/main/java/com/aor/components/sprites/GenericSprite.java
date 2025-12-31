package com.aor.components.sprites;

import com.aor.utils.Vector2d;
import com.googlecode.lanterna.graphics.TextGraphics;


public interface GenericSprite {
    void draw(TextGraphics graphics);
    int getWidth();
    int getHeight();

    void xFlip();
    void normal();

    Vector2d getPosition();
    void setPosition( Vector2d pos);
    int hashCode();

    boolean equals(Object o);
}
