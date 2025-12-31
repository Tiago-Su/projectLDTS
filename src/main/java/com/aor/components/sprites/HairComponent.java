package com.aor.components.sprites;

import com.aor.utils.Vector2d;
import com.googlecode.lanterna.graphics.TextGraphics;

import java.io.IOException;

public class HairComponent implements Positionable {
    TransparentSprite sprite;
    HairPosition position;
    public HairComponent(Vector2d position, Vector2d relativePosition, Positionable chainedComponent, Vector2d relTarget, Vector2d xLimits, Vector2d yLimits, String filePath) throws IOException {
        sprite = new TransparentSprite(position, filePath);
        this.position = new HairPosition(position, relativePosition, chainedComponent, relTarget, xLimits, yLimits);

    }
    public void update( double delta){
        position.update(delta);
    }

    public void draw(TextGraphics graphics){
        sprite.update(position.getPosition());
        sprite.draw(graphics);
    }

    @Override
    public Vector2d getPosition() {
        return position.getPosition();
    }
}
