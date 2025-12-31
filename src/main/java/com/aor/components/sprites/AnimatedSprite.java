package com.aor.components.sprites;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.aor.components.Node;
import com.aor.components.factory.GenericSpriteFactory;
import com.aor.utils.Vector2d;
import com.googlecode.lanterna.graphics.TextGraphics;

public class AnimatedSprite extends Node {
    private final List<GenericSprite> frames = new ArrayList<>();
    private double currentFrame;
    private final double frameSpeed;

    public AnimatedSprite(Vector2d position, List<String> imgPaths, double frameSpeed, GenericSpriteFactory factory) throws IOException {
        super(position, new Vector2d(0, 0));
        for(String ip : imgPaths){
            frames.add(factory.newSprite(position, ip));
        }

        currentFrame = 0;
        this.frameSpeed = frameSpeed;
    }

    public AnimatedSprite(Vector2d position, Vector2d offset, List<String> imgPaths, double frameSpeed, GenericSpriteFactory factory) throws IOException {
        super(position, offset);
        for(String ip : imgPaths){
            frames.add(factory.newSprite(position, offset, ip));
        }

        currentFrame = 0;
        this.frameSpeed = frameSpeed;
    }

    public double getCurrentFrame() {
        return currentFrame;
    }

    public GenericSprite getCurrentFrameSprite(){
        return frames.get((int) currentFrame);
    }

    public final void draw(TextGraphics graphics){
        frames.get((int) currentFrame).draw(graphics);
    }
    public void xFlip(){
        for (GenericSprite f:frames){
            f.xFlip();
        }
    }
    public void normal(){
        for (GenericSprite f:frames){
            f.normal();
        }
    }
    public void update(Vector2d newPos, double timeDelta){
        currentFrame += frameSpeed * timeDelta;
        if(currentFrame >= frames.size()) currentFrame = 0;

        super.update(newPos);
        frames.get((int) currentFrame).setPosition(newPos);
    }

    public int getWidth(){
        return frames.get((int) currentFrame).getWidth();
    }
    public int getHeight(){
        return frames.get((int) currentFrame).getHeight();
    }

    @Override
    public int hashCode() {
        return Objects.hash(frames, getPosition());
    }

    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null) return false;
        if (getClass() != o.getClass()) return false;

        AnimatedSprite p = (AnimatedSprite) o;
        return this.hashCode() == p.hashCode();
    }
}
