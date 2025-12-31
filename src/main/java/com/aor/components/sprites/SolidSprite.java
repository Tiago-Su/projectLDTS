package com.aor.components.sprites;

import java.io.IOException;
import java.util.Objects;

import com.aor.components.Node;
import com.aor.utils.Utils;
import com.aor.utils.Vector2d;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.graphics.TextImage;

public class SolidSprite extends Node implements GenericSprite {
    private final Integer width;
    private final Integer height;

    private TextImage currentImg;
    private final TextImage xFlippedImg;
    private final TextImage normalImg;

    private final String filePath;

    public SolidSprite(Vector2d position, String filePath) throws IOException {
        super(position);

        this.normalImg = Utils.loadImage(filePath);
        this.xFlippedImg = Utils.loadXFlippedImage(filePath);
        this.currentImg = normalImg;

        this.width = currentImg.getSize().getColumns();
        this.height = currentImg.getSize().getRows();

        this.filePath = filePath;
    }

    public SolidSprite(Vector2d position, Vector2d offset, String filePath) throws IOException {
        super(position, offset);

        this.normalImg = Utils.loadImage(filePath);
        this.xFlippedImg = Utils.loadXFlippedImage(filePath);
        this.currentImg = normalImg;

        this.width = currentImg.getSize().getColumns();
        this.height = currentImg.getSize().getRows();

        this.filePath = filePath;
    }

    public void draw(TextGraphics graphics){
        graphics.drawImage(new TerminalPosition((int) getPosition().getX(), (int) getPosition().getY()), currentImg);
    }
    
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }

    public void xFlip(){
        currentImg = xFlippedImg;
    }
    public void normal(){
        currentImg = normalImg;
    }

    @Override
    public int hashCode() {
        return Objects.hash(filePath, getPosition());
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null) return false;
        if (getClass() != o.getClass()) return false;

        SolidSprite p = (SolidSprite) o;
        return this.hashCode() == p.hashCode();
    }
}
