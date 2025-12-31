package com.aor.elements;

import com.aor.game.GameData;
import com.aor.tile.Tile;
import com.aor.utils.Vector2d;

import java.io.IOException;

public class GroundedSpike extends Tile {
    public GroundedSpike(Vector2d position) throws IOException {
        super(position, new Vector2d(0, 3), 6, 5, GameData.getTileImgPath().get("spike"), GameData.SPIKE_TAG);
        super.getBoxCollider().setTag(GameData.SPIKE_TAG);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if( this.getClass()!=o.getClass()){return false;}
        return super.equals(o);
    }
}
