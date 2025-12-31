package com.aor.elements;

import com.aor.game.GameData;
import com.aor.utils.Vector2d;

import java.io.IOException;

public class EndBlock extends EmptyBlock {
    public EndBlock(Vector2d position) throws IOException {
        super(position, new Vector2d(0, 0), 8, 1, GameData.END_BLOCK_TAG);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if( this.getClass()!=o.getClass()){return false;}
        return super.equals(o);
    }
}
