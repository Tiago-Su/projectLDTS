package com.aor.game;

import com.aor.components.BoxCollider;
import com.aor.utils.Vector2d;

import java.util.List;

public interface GenericArena {
    public List<BoxCollider> detectCollision(Vector2d newPos, BoxCollider boxCollider, String searchTag);
}
