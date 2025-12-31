package com.aor.game;

import com.aor.components.BoxCollider;
import com.aor.elements.BackgroundBlock;
import com.aor.hero.mvc.Hero;
import com.aor.tile.Tile;
import com.aor.utils.Vector2d;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

class TestArena {
    Hero hero;

    @BeforeEach
    void setup() {
        hero = Mockito.mock(Hero.class);
        Mockito.doNothing().when(hero).update(Mockito.any(TextGraphics.class), Mockito.anyDouble());
    }

    @Test
    void testDetectCollision1() {
        //No collisions
        List<Tile> colliders = new ArrayList<>();
        BoxCollider boxCollider = Mockito.mock(BoxCollider.class);
        Mockito.when(boxCollider.isColliding(Mockito.any(BoxCollider.class))).thenReturn(false);
        Mockito.when(boxCollider.getPosition()).thenReturn(new Vector2d(0, 0));

        BoxCollider mockedBox = Mockito.mock(BoxCollider.class);
        Tile mockedTile = Mockito.mock(Tile.class);
        Mockito.when(mockedTile.getBoxCollider()).thenReturn(mockedBox);
        Mockito.when(mockedBox.getTag()).thenReturn("tile");
        colliders.add(mockedTile);

        Arena arena = new Arena(hero, colliders, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Assertions.assertTrue(arena.detectCollision(new Vector2d(0, 0), boxCollider, "tile").isEmpty());
    }

    @Test
    void testDetectCollision2() {
        //Colliding with a tile
        List<Tile> colliders = new ArrayList<>();
        BoxCollider boxCollider = Mockito.mock(BoxCollider.class);
        Mockito.when(boxCollider.isColliding(Mockito.any(BoxCollider.class))).thenReturn(true);
        Mockito.when(boxCollider.getPosition()).thenReturn(new Vector2d(0, 0));

        BoxCollider mockedBox = Mockito.mock(BoxCollider.class);
        Tile mockedTile = Mockito.mock(Tile.class);
        Mockito.when(mockedTile.getBoxCollider()).thenReturn(mockedBox);
        Mockito.when(mockedBox.getTag()).thenReturn("tile");
        colliders.add(mockedTile);

        Arena arena = new Arena(hero, colliders, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Assertions.assertFalse(arena.detectCollision(new Vector2d(0, 0), boxCollider, "tile").isEmpty());
    }

    @Test
    void testDetectCollision3() {
        //Colliding with background (no collisions)
        List<BackgroundBlock> colliders = new ArrayList<>();
        BoxCollider boxCollider = Mockito.mock(BoxCollider.class);
        Mockito.when(boxCollider.isColliding(Mockito.any(BoxCollider.class))).thenReturn(true);
        Mockito.when(boxCollider.getPosition()).thenReturn(new Vector2d(0, 0));

        BoxCollider mockedBox = Mockito.mock(BoxCollider.class);
        BackgroundBlock mockedTile = Mockito.mock(BackgroundBlock.class);
        Mockito.when(mockedTile.getBoxCollider()).thenReturn(mockedBox);
        Mockito.when(mockedBox.getTag()).thenReturn("tile");
        colliders.add(mockedTile);

        Arena arena = new Arena(hero, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), colliders);
        Assertions.assertTrue(arena.detectCollision(new Vector2d(0, 0), boxCollider, "tile").isEmpty());
    }

    @Test
    void testUpdate() {
        //Only testing for tile and background because the rest are cast to tile
        List<BackgroundBlock> background = new ArrayList<>();
        List<Tile> tiles = new ArrayList<>();

        BoxCollider mockedBox = Mockito.mock(BoxCollider.class);
        TextGraphics textGraphics = Mockito.mock(TextGraphics.class);

        Tile tile = Mockito.mock(Tile.class);
        Mockito.when(tile.getBoxCollider()).thenReturn(mockedBox);
        Mockito.doNothing().when(tile).update(Mockito.any(TextGraphics.class), Mockito.anyDouble());

        BackgroundBlock backgroundBlock = Mockito.mock(BackgroundBlock.class);
        Mockito.when(backgroundBlock.getBoxCollider()).thenReturn(mockedBox);
        Mockito.doNothing().when(backgroundBlock).update(Mockito.any(TextGraphics.class), Mockito.anyDouble());

        tiles.add(tile);
        tiles.add(tile);
        background.add(backgroundBlock);
        Arena arena = new Arena(hero, tiles, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), background);

        //Only dummies values
        arena.update(textGraphics, 0);
        Mockito.verify(tile, Mockito.times(2)).update(Mockito.any(TextGraphics.class), Mockito.anyDouble());
        Mockito.verify(backgroundBlock, Mockito.times(1)).update(Mockito.any(TextGraphics.class), Mockito.anyDouble());
        Mockito.verify(hero, Mockito.times(1)).update(Mockito.any(TextGraphics.class), Mockito.anyDouble());
    }
}