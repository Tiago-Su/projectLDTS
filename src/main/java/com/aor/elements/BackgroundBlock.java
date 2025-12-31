package com.aor.elements;

import com.aor.tile.Tile;
import com.aor.utils.Vector2d;

import java.io.IOException;

public class BackgroundBlock  extends Tile {
    public BackgroundBlock(Vector2d position, String path) throws IOException {
        super(position, path, " ");
    }

    public BackgroundBlock(Vector2d position, Vector2d offset, int width, int height, String path) throws IOException {
        super(position, offset, width, height, path, " ");
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if( this.getClass()!=o.getClass()){return false;}
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
