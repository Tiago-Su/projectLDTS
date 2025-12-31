package com.aor.hero.mvc;

import com.aor.components.sprites.Positionable;
import com.aor.hero.states.GroundedState;
import com.aor.hero.states.HeroState;
import com.aor.mvc.GenericModel;
import com.aor.utils.Vector2d;

public class HeroModel implements GenericModel, Positionable {
    protected Vector2d position;
    private Vector2d velocity;
    private Vector2d direction;
    private HeroState state;

    private boolean canDash = true;
    private boolean isFacingRight = true;

    public HeroModel(Vector2d position, Vector2d velocity, Vector2d direction) {
        this.position = position;
        this.velocity = velocity;
        this.direction = direction;

        state = new GroundedState(this);
    }

    public Vector2d getPosition() {
        return position;
    }
    public Vector2d getVelocity() {
        return velocity;
    }
    public Vector2d getDirection() {
        return direction;
    }
    public HeroState getState() {
        return state;
    }
    public boolean canDash() {
        return canDash;
    }
    public boolean isFacingRight() {
        return isFacingRight;
    }

    public void setCanDash(boolean canDash) {
        this.canDash = canDash;
    }
    public void setFacingRight(boolean facingRight) {
        isFacingRight = facingRight;
    }
    public void setState(HeroState state) {
        this.state = state;
    }
    public void setPosition(Vector2d position) {
        this.position = position;
    }
    public void setVelocity(Vector2d velocity) {
        this.velocity = velocity;
    }
    public void setDirectionX(int newDirX) {
        this.direction.setX(newDirX);
    }
    public void setDirectionY(int newDirY) {
        this.direction.setY(newDirY);
    }
}