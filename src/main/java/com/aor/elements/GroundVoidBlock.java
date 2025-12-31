package com.aor.elements;

import com.aor.game.GameData;
import com.aor.utils.Vector2d;

import java.io.IOException;

public class GroundVoidBlock extends EmptyBlock {
    public GroundVoidBlock(Vector2d position) throws IOException {
        super(position, new Vector2d(0, 8), 8, 8, GameData.VOID_TILE_TAG);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if( this.getClass()!=o.getClass()){return false;}
        return super.equals(o);
    }
}

