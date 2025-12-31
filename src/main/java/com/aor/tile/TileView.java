package com.aor.tile;

import com.aor.components.sprites.TransparentSprite;
import com.aor.mvc.GenericModel;
import com.aor.mvc.GenericView;
import com.googlecode.lanterna.graphics.TextGraphics;

import java.io.IOException;

public class TileView implements GenericView {
    private final TileModel tileModel;
    private final TransparentSprite sprite;

    public TileView(GenericModel tileModel) throws IOException {
        this.tileModel = (TileModel) tileModel;
        sprite = new TransparentSprite(this.tileModel.getPosition(), this.tileModel.getOffset(), this.tileModel.getFilePath());
    }

    public TileView(GenericModel tileModel,TransparentSprite sprite) {
        this.tileModel = (TileModel) tileModel;
        this.sprite = sprite;
    }

    @Override
    public void update(TextGraphics graphics, double delta) {
        sprite.update(tileModel.getPosition());
        sprite.draw(graphics);
    }

    public TileModel getTileModel(){
        return tileModel;
    }
}