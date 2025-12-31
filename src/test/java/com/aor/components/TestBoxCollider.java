package com.aor.components;

import com.aor.utils.Vector2d;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestBoxCollider {
    BoxCollider boxCollider;

    @BeforeEach
    void setUp() {
        Vector2d position = new Vector2d(5, 5);
        boxCollider = new BoxCollider(position, new Vector2d(0, 0), 10, 5, "tile");
    }

    @Test
    void testIsColliding1() {
        //Check when there is no collision
        Vector2d position = new Vector2d(0, 0);
        int width = 1;
        int height = 1;

        Assertions.assertFalse(boxCollider.isColliding(new BoxCollider(position, new Vector2d(0, 0), width, height, "tile")));
    }

    @Test
    void testIsColliding2() {
        //Check when topLeft is colliding
        Vector2d position = new Vector2d(2, 2);
        int width = 5;
        int height = 5;

        Assertions.assertTrue(boxCollider.isColliding(new BoxCollider(position, new Vector2d(0, 0), width, height, "tile")));
    }

    @Test
    void testIsColliding3() {
        //Check when botRight is colliding
        Vector2d position = new Vector2d(7, 7);
        Assertions.assertTrue(boxCollider.isColliding(new BoxCollider(position, new Vector2d(0, 0), 10, 10, "tile")));
    }

    @Test
    void testIsColliding4() {
        //Check when is inside another box
        Vector2d position = new Vector2d(2, 2);
        Assertions.assertTrue(boxCollider.isColliding(new BoxCollider(position, new Vector2d(0, 0), 20, 20, "tile")));
    }

    @Test
    void testIsColliding5() {
        //Check when is only touching the left limit
        Vector2d position = new Vector2d(2, 2);
        Assertions.assertFalse(boxCollider.isColliding(new BoxCollider(position, new Vector2d(0, 0), 3, 20, "tile")));
    }

    @Test
    void testIsColliding6() {
        //Check when is only touching the right limit
        Vector2d position = new Vector2d(15, 2);
        Assertions.assertFalse(boxCollider.isColliding(new BoxCollider(position, new Vector2d(0, 0), 3, 20, "tile")));
    }

    @Test
    void testIsColliding7() {
        //Check when is only touching the top limit
        Vector2d position = new Vector2d(2, 2);
        Assertions.assertFalse(boxCollider.isColliding(new BoxCollider(position, new Vector2d(0, 0), 20, 3, "tile")));
    }

    @Test
    void testIsColliding8() {
        //Check when is only touching the bottom limit
        Vector2d position = new Vector2d(2, 10);
        Assertions.assertFalse(boxCollider.isColliding(new BoxCollider(position, new Vector2d(0, 0), 20, 5, "tile")));
    }

    @Test
    void testIsColliding9() {
        //Check collision with itself
        Assertions.assertFalse(boxCollider.isColliding(boxCollider));
    }

    @Test
    void testIsColliding10() {
        //Check collision with topleft and botleft touching
        BoxCollider otherBox = new BoxCollider(new Vector2d(4, 5), 2, 5, "tile");
        Assertions.assertTrue(boxCollider.isColliding(otherBox));
    }

    @Test
    void testIsColliding11() {
        //Check collision with topright and botright touching
        BoxCollider otherBox = new BoxCollider(new Vector2d(14, 5), 2, 5, "tile");
        Assertions.assertTrue(boxCollider.isColliding(otherBox));
    }

    @Test
    void testIsColliding12() {
        //Check collision with topleft and topright touching
        BoxCollider otherBox = new BoxCollider(new Vector2d(5, 4), 10, 2, "tile");
        Assertions.assertTrue(boxCollider.isColliding(otherBox));
    }

    @Test
    void testIsColliding13() {
        //Check collision with botleft and botright touching
        BoxCollider otherBox = new BoxCollider(new Vector2d(5, 9), 10, 2, "tile");
        Assertions.assertTrue(boxCollider.isColliding(otherBox));
    }

    @Test
    void testGetters() {
        Assertions.assertEquals(10, boxCollider.getWidth());
        Assertions.assertEquals(5, boxCollider.getHeight());
        Assertions.assertEquals("tile", boxCollider.getTag());

        boxCollider.setTag("inv");
        Assertions.assertEquals("inv", boxCollider.getTag());
    }

    @Test
    void testCollideOnce() {
        Assertions.assertFalse(boxCollider.getCollidedOnce());
        boxCollider.firstCollide();
        Assertions.assertTrue(boxCollider.getCollidedOnce());
    }
}
