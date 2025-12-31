package com.aor.components;

import com.aor.game.Arena;
import com.aor.utils.Vector2d;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

class TestRigidBody {
    RigidBody rb;
    Vector2d initialPos;
    Arena stubArena;

    @BeforeEach
    void setUp() {
        //Rectangle with topLeft (0, 0) and size (5, 5)
        initialPos = new Vector2d(0, 0);
        Vector2d velocity = new Vector2d(0, 0);
        stubArena = Mockito.mock(Arena.class);
        BoxCollider boxCollider = new BoxCollider(initialPos, new Vector2d(0, 0), 5, 5, "player");

        rb = new RigidBody(initialPos, new Vector2d(0, 0), velocity, stubArena, boxCollider);
    }

    @Test
    void testMove1() {
        //There is no collisions - with positive velocity
        Mockito.when(stubArena.detectCollision(Mockito.any(Vector2d.class), Mockito.any(BoxCollider.class), Mockito.any(String.class))).thenReturn(new ArrayList<>());
        Vector2d newVelocity = new Vector2d(5, 5);
        rb.setVelocity(newVelocity);
        Vector2d newPos = rb.move(1);

        Assertions.assertEquals(new Vector2d(5, 5), newPos);
    }

    @Test
    void testMove2() {
        //There is no collisions - with negative velocity
        Mockito.when(stubArena.detectCollision(Mockito.any(Vector2d.class), Mockito.any(BoxCollider.class), Mockito.any(String.class))).thenReturn(new ArrayList<>());
        Vector2d newVelocity = new Vector2d(-5, -5);
        rb.setVelocity(newVelocity);

        Vector2d newPos = rb.move(1);
        Assertions.assertEquals(new Vector2d(-5, -5), newPos);
    }

    @Test
    void testMove3() {
        //There is no collisions - with zero velocity
        Mockito.when(stubArena.detectCollision(Mockito.any(Vector2d.class), Mockito.any(BoxCollider.class), Mockito.any(String.class))).thenReturn(new ArrayList<>());
        Vector2d newVelocity = new Vector2d(0, 0);
        rb.setVelocity(newVelocity);

        Vector2d newPos = rb.move(1);
        Assertions.assertEquals(new Vector2d(0, 0), newPos);
    }

    @Test
    void testMove4() {
        //Moving to the left and there is a wall in position (7, 0) with size (2, 10)
        Vector2d wallPosition = new Vector2d(7, 0);
        List<BoxCollider> colliders = new ArrayList<>();
        colliders.add(new BoxCollider(wallPosition, new Vector2d(0, 0), 2, 10, "tile"));
        Mockito.when(stubArena.detectCollision(Mockito.any(Vector2d.class), Mockito.any(BoxCollider.class), Mockito.any(String.class))).thenReturn(colliders);

        Vector2d newVelocity = new Vector2d(3, 0);
        rb.setVelocity(newVelocity);

        //Should move and his topleft should be (2, 0)
        Vector2d newPos = rb.move(1);
        Assertions.assertEquals(new Vector2d(7 - 5, 0), newPos);
    }

    @Test
    void testMove5() {
        //Moving to the right and there is a wall in position (-5, 0) with size (3, 10)
        Vector2d wallPosition = new Vector2d(-5, 0);
        List<BoxCollider> colliders = new ArrayList<>();
        colliders.add(new BoxCollider(wallPosition, new Vector2d(0, 0), 3, 10, "tile"));
        Mockito.when(stubArena.detectCollision(Mockito.any(Vector2d.class), Mockito.any(BoxCollider.class), Mockito.any(String.class))).thenReturn(colliders);

        Vector2d newVelocity = new Vector2d(-3, 0);
        rb.setVelocity(newVelocity);

        //Should move and his topleft should (-2, 0)
        Vector2d newPos = rb.move(1);
        Assertions.assertEquals(new Vector2d(-5 + 3, 0), newPos);
    }

    @Test
    void testMove6() {
        //Moving upwards and there is a wall in position (0, -5) with size (5, 3)
        Vector2d wallPosition = new Vector2d(0, -5);
        List<BoxCollider> colliders = new ArrayList<>();
        colliders.add(new BoxCollider(wallPosition, new Vector2d(0, 0), 5, 3, "tile"));
        Mockito.when(stubArena.detectCollision(Mockito.any(Vector2d.class), Mockito.any(BoxCollider.class), Mockito.any(String.class))).thenReturn(colliders);

        Vector2d newVelocity = new Vector2d(0, -3);
        rb.setVelocity(newVelocity);

        //Should move and his topleft should (0, -2)
        Vector2d newPos = rb.move(1);
        Assertions.assertEquals(new Vector2d(0, -5 + 3), newPos);
    }

    @Test
    void testMove7() {
        //Moving downwards and there is a wall in position (0, 7) with size (5, 3)
        Vector2d wallPosition = new Vector2d(0, 7);
        List<BoxCollider> colliders = new ArrayList<>();
        colliders.add(new BoxCollider(wallPosition, new Vector2d(0, 0), 5, 3, "tile"));
        Mockito.when(stubArena.detectCollision(Mockito.any(Vector2d.class), Mockito.any(BoxCollider.class), Mockito.any(String.class))).thenReturn(colliders);

        Vector2d newVelocity = new Vector2d(0, 3);
        rb.setVelocity(newVelocity);

        //Should move and his topleft should (0, 2)
        Vector2d newPos = rb.move(1);
        Assertions.assertEquals(new Vector2d(0, 7 - 5), newPos);
    }

    @Test
    void testUpdate() {
        Vector2d v = new Vector2d(10, 10);
        rb.update(v);

        Assertions.assertEquals(v, rb.getPosition());
        Assertions.assertEquals(v, rb.getBoxCollider().getPosition());
    }

    @Test
    void testGetters() {
        Vector2d v = new Vector2d(5, 5);
        rb.setVelocity(v);

        Assertions.assertEquals(v, rb.getVelocity());
    }
}
