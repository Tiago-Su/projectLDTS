package com.aor.components.sprites;

import com.aor.components.Node;
import com.aor.utils.Utils;
import com.aor.utils.Vector2d;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class TransparentSprite extends Node implements GenericSprite {
    private final Integer width;
    private final Integer height;

    private List<List<Pixel>> currentImg;
    private final List<List<Pixel>> xFlippedImg;
    private final List<List<Pixel>> normalImg;

    private final String filePath;

    public TransparentSprite(Vector2d position, String filePath) throws IOException {
        super(position);

        this.normalImg = Utils.loadTransparentImage(filePath);
        xFlippedImg = Utils.loadXFlippedTransparentImage(filePath);
        currentImg = normalImg;

        this.width = currentImg.getFirst().size();
        this.height = currentImg.size();
        this.filePath = filePath;
    }


    public TransparentSprite(Vector2d position, Vector2d offset, String filePath) throws IOException {
        super(position, offset);

        this.normalImg = Utils.loadTransparentImage(filePath);
        xFlippedImg = Utils.loadXFlippedTransparentImage(filePath);
        currentImg = normalImg;

        this.width = currentImg.getFirst().size();
        this.height = currentImg.size();
        this.filePath = filePath;
    }

    public void draw(TextGraphics graphics){
        for (int y = 0; y < currentImg.size(); y++) {
            for (int x = 0; x < currentImg.getFirst().size(); x++) {
                Pixel pixel = currentImg.get(y).get(x);
                if (pixel.getAlpha() != 0){
                    Vector2d pixelPos = new Vector2d(x, y).addVector(getPosition());
                    graphics.setBackgroundColor(TextColor.Factory.fromString(pixel.toString()));
                    graphics.setCharacter(new TerminalPosition((int) pixelPos.getX(), (int) pixelPos.getY()), ' ');
                }
            }
        }
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

    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null) return false;
        if (getClass() != o.getClass()) return false;

        TransparentSprite p = (TransparentSprite) o;
        return this.hashCode() == p.hashCode();
    }
}
