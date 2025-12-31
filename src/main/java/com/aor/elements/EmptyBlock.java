package com.aor.elements;

import com.aor.game.GameData;
import com.aor.utils.Vector2d;
import com.aor.tile.*;
import com.googlecode.lanterna.graphics.TextGraphics;

import java.io.IOException;

public class EmptyBlock extends Tile {
    public EmptyBlock(Vector2d position, Vector2d offset, int width, int height, String tag) throws IOException {
        super(position, offset, width, height, GameData.getTileImgPath().get(GameData.SPIKE_TAG), GameData.SPIKE_TAG);
        super.getBoxCollider().setTag(tag);
    }

    @Override
    public void update(TextGraphics graphics, double delta) {}

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if( this.getClass()!=o.getClass()){return false;}
        return super.equals(o);
    }
}
