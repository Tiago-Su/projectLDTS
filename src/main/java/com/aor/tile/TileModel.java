package com.aor.tile;

import com.aor.game.GameData;
import com.aor.mvc.GenericModel;
import com.aor.utils.Vector2d;

import java.util.Objects;


public class TileModel implements GenericModel {
    private final int width;
    private final int height;

    private Vector2d position;
    private final Vector2d offset;

    private final String filePath;
    private final String tag;

    public TileModel(Vector2d position, Vector2d offset, int width, int height, String filePath, String tag){
        this.position = position;
        this.offset = offset;

        this.filePath = filePath;
        this.tag = tag;

        this.width = width;
        this.height = height;
    }

    public TileModel(Vector2d position, String filePath, String tag){
        this.position = position;
        this.offset = new Vector2d(0, 0);

        this.filePath = filePath;
        this.tag = tag;

        width = GameData.TILE_WIDTH;
        height = GameData.TILE_HEIGHT;
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }

    public String getFilePath() {
        return filePath;
    }
    public Vector2d getPosition(){
        return position;
    }
    public int getWidth(){return width;}

    public int getHeight(){return height;}

    public Vector2d getOffset() {
        return offset;
    }

    public String getTag() {
        return tag;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if(obj.getClass() != this.getClass())return false;
        if(obj == this)return true;
        TileModel o = (TileModel) obj;

        return o.hashCode() == hashCode();
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, offset, width,  height, filePath, tag);
    }
}
