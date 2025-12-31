package com.aor.components;

import com.aor.utils.Vector2d;

public class BoxCollider extends Node {
    private final int width;
    private final int height;
    private String tag;
    private boolean collidedOnce = false;

    public BoxCollider(Vector2d position, int width, int height, String tag) {
        super(position);
        this.width = width;
        this.height = height;
        this.tag = tag;
    }

    public BoxCollider(Vector2d position, Vector2d offset, int width, int height, String tag) {
        super(position, offset);
        this.width = width;
        this.height = height;
        this.tag = tag;
    }

    public void firstCollide() {
        this.collidedOnce = true;
    }
    public boolean getCollidedOnce(){
        return collidedOnce;
    }

    public boolean isColliding(BoxCollider other) {
        if (other.equals(this)) return false;

        double x = getPosition().getX();
        double y = getPosition().getY();

        double otherX = other.getPosition().getX();
        double otherY = other.getPosition().getY();
        double otherWidth = other.getWidth();
        double otherHeight = other.getHeight();

        return x < (otherX + otherWidth) && (x + width) > otherX && y < (otherY + otherHeight) && (y + height) > otherY;

    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public String getTag() {
        return tag;
    }
    public void setTag(String tag) {
         this.tag = tag;
    }
}

