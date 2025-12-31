package com.aor.game;

import java.util.ArrayList;
import java.util.List;

import com.aor.components.BoxCollider;
import com.aor.elements.*;
import com.aor.hero.mvc.Hero;
import com.aor.tile.Tile;
import com.aor.utils.Vector2d;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Arena implements GenericArena {
    private Hero hero;

    private boolean isHeroDead = false;
    private boolean isArenaCompleted = false;

    private List<Tile> generalTiles = new ArrayList<>();
    private final List<BoxCollider> boxColliders = new ArrayList<>();

    private final List<EndBlock> endBlocks;
    private final List<GroundedSpike> spikes;

    private final List<GroundVoidBlock> voids;
    private final List<BackgroundBlock> background;

    public Arena(Hero hero, List<Tile> tiles, List<Strawberry> strawberries, List<EndBlock> endBlocks, List<GroundedSpike> spikes, List<GroundVoidBlock> voids, List<BackgroundBlock> background) {
        this.hero = hero;
        this.endBlocks = endBlocks;
        this.spikes = spikes;
        this.voids = voids;
        this.background = background;

        generalTiles = new ArrayList<>();

        for(Strawberry s : strawberries) {
            generalTiles.add(s);
            boxColliders.add(s.getBoxCollider());
        }

        for(Tile t : tiles) {
            generalTiles.add(t);
            boxColliders.add(t.getBoxCollider());
        }

        for(GroundedSpike s : spikes) {
            generalTiles.add(s);
            boxColliders.add(s.getBoxCollider());
        }

        for (EndBlock e: endBlocks){
            generalTiles.add(e);
            boxColliders.add(e.getBoxCollider());
        }

        for (GroundVoidBlock v:voids ){
            generalTiles.add(v);
            boxColliders.add(v.getBoxCollider());
        }

        BoxCollider leftInvWall = new BoxCollider(new Vector2d(-10, 0), 10, GameData.SCREEN_HEIGHT, GameData.INV_WALL_TAG);
        BoxCollider rightInvWall = new BoxCollider(new Vector2d(GameData.SCREEN_WIDTH, 0), 10, GameData.SCREEN_HEIGHT, GameData.INV_WALL_TAG);
        boxColliders.add(leftInvWall);
        boxColliders.add(rightInvWall);
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    private boolean checkIfHeroIsDead() {
        for (Tile tile : spikes) {
            if (tile.getBoxCollider().getCollidedOnce()) return false;
        }

        for (Tile tile : voids) {
            if (tile.getBoxCollider().getCollidedOnce()) return false;
        }

        return true;
    }

    private boolean checkIfLevelComplete() {
        for (Tile tile : endBlocks) {
            if (tile.getBoxCollider().getCollidedOnce()) return true;
        }

        return false;
    }

    public List<BoxCollider> detectCollision(Vector2d newPos, BoxCollider boxCollider, String searchTag) {
        List<BoxCollider> colliders = new ArrayList<>();
        Vector2d oldPos = new Vector2d(boxCollider.getPosition().getX(), boxCollider.getPosition().getY());
        boxCollider.setPosition(newPos);

        for (BoxCollider tileBoxCollider : boxColliders) {
            if (!boxCollider.isColliding(tileBoxCollider)) continue;
            if (searchTag.equals(GameData.ALL_COLLIDERS) || searchTag.equals(tileBoxCollider.getTag())) {
                tileBoxCollider.firstCollide();
                colliders.add(tileBoxCollider);
            }
        }

        boxCollider.setPosition(oldPos);
        return colliders;
    }

    public void update(TextGraphics graphics, double delta) {
        for (Tile tile : background) {
            tile.update(graphics, delta);
        }

        for (Tile t : generalTiles) {
            t.update(graphics, delta);
        }

        if (hero != null) {
            hero.update(graphics, delta);
        }

        isHeroDead = !checkIfHeroIsDead();
        isArenaCompleted = checkIfLevelComplete();
    }

    public boolean isHeroDead() {
        return isHeroDead;
    }
    public boolean isArenaCompleted() {
        return isArenaCompleted;
    }
}
