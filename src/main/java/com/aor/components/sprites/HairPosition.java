package com.aor.components.sprites;

import com.aor.components.Node;
import com.aor.game.GameData;
import com.aor.utils.Utils;
import com.aor.utils.Vector2d;

public class HairPosition extends Node implements Positionable {
    private final Positionable chainedNode;
    private final double velocity;
    private final Vector2d relTarget;
    private final Vector2d xLimits;
    private final Vector2d yLimits;

    public HairPosition(Vector2d position, Vector2d offset, Positionable chainedNode, Vector2d relativeTarget, Vector2d xLimits, Vector2d yLimits) {
        super(position, offset);
        this.chainedNode = chainedNode;
        this.relTarget = relativeTarget;
        this.velocity = GameData.HAIR_VELOCITY;
        this.xLimits = xLimits;
        this.yLimits = yLimits;
    }

    private void move(double delta){
        Vector2d position = new Vector2d(super.getPosition());
        position.addVector(super.getOffset());
        position.subVector(Utils.vectorSum(chainedNode.getPosition(), relTarget));

        double module = position.module();
        position.normalize();

        position.scalarProduct(-1 * velocity * delta);
        position.addVector(super.getPosition());

        if (module >= 1) super.setPosition(position);
    }

    private double alignedX() {
        double refX = chainedNode.getPosition().getX() + getOffset().getX();
        double actualRelX = getPosition().getX();

        double delta = actualRelX - refX;
        if (delta <= xLimits.getY() && delta >= xLimits.getX() ){
            return getPosition().getX();
        }
        if (actualRelX < refX){
            return refX + xLimits.getX();
        }
        else {
            return refX + xLimits.getY();
        }
    }

    private double alignedY() {
        double refY = chainedNode.getPosition().getY() + getOffset().getY();
        double actualRelY = getPosition().getY();

        double delta = actualRelY-refY;
        if (delta <= yLimits.getY() && delta >= yLimits.getX()){
            return getPosition().getY();
        }
        if (actualRelY < refY){
            return refY + yLimits.getX();
        }
        else {
            return refY + yLimits.getY();
        }
    }

    public void update(double delta) {
        move(delta);
        setPosition(new Vector2d(alignedX(), alignedY()));
    }
}
