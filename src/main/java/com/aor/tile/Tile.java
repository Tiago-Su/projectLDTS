package com.aor.tile;

import java.io.IOException;
import java.util.Objects;

import com.aor.elements.GameElement;
import com.aor.components.BoxCollider;
import com.aor.utils.Vector2d;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Tile implements GameElement {
    private Vector2d position;
    private String filePath;
    private String tag;
    TileView tileView;
    TileModel tileModel;
    TileController tileController;

    public Tile(Vector2d position, Vector2d offset, int width, int height, String filePath, String tag) throws IOException {
        this.position=position;
        this.tag=tag;
        this.filePath=filePath;
        tileModel = new TileModel(position, offset, width, height, filePath, tag);
        tileView = new TileView(tileModel);
        tileController = new TileController(tileModel, this.tileModel.getTag());
    }

    public Tile(Vector2d position, String filePath, String tag) throws IOException {
        this.position=position;
        this.tag=tag;
        this.filePath=filePath;
        tileModel = new TileModel(position, filePath, tag);
        tileView = new TileView(tileModel);
        tileController = new TileController(tileModel, this.tileModel.getTag());
    }
    public Tile(TileView tileView,TileModel tileModel, TileController tileController) {
        this.tileModel =tileModel ;
        this.tileView = tileView;
        this.tileController = tileController;
    }
    public void update(TextGraphics graphics, double delta) {
        tileController.update(delta);
        tileView.update(graphics, delta);
    }

    public BoxCollider getBoxCollider() {
        return tileController.getBoxCollider();
    }

    public void setSpeed(Vector2d speed) {
        tileController.setSpeed(speed);
    }

    public Vector2d getPosition() {
        return position;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return tile.position.equals(position) && tile.filePath.equals(filePath) && tile.tag.equals(tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position,tag,filePath);
    }
}
