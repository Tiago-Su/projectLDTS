package com.aor.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestVector2d {
    Vector2d v1;
    @BeforeEach
    void setup() {
        v1 = new Vector2d(1, 1);
    }

    @Test
    void testEquals() {
        Vector2d v2 = new Vector2d(1, 1);

        Assertions.assertEquals(v1, v2);
    }

    @Test
    void testAddVector() {
        Vector2d v2 = new Vector2d(5, 2);

        v1.addVector(v2);
        Assertions.assertEquals(new Vector2d(6, 3), v1);
    }

    @Test
    void testScalarProduct() {
        int scalar = 5;

        v1.scalarProduct(scalar);
        Assertions.assertEquals(new Vector2d(5, 5), v1);
    }

    @Test
    void testSetters() {
        v1.setX(0);
        v1.setY(5);

        Assertions.assertTrue(v1.getX() == 0 && v1.getY() == 5);
    }

    @Test
    void testToString() {
        String s = "(1.0, 1.0)";
        Assertions.assertEquals(s, v1.toString());
    }

    @Test
    void testNormalize1() {
        Vector2d normalizedVector = new Vector2d(1 / Math.sqrt(2), 1 / Math.sqrt(2));
        Assertions.assertEquals(normalizedVector, v1.normalize());
    }

    @Test
    void testNormalize2() {
        Vector2d v2 = new Vector2d(0, 0);
        v2.normalize();
        Assertions.assertEquals(new Vector2d(0, 0), v2.normalize());
    }

    @Test
    void testCloneConstructor() {
        Vector2d otherVector = new Vector2d(v1);
        Assertions.assertEquals(v1, otherVector);
    }

    @Test
    void testClone() {
        Vector2d cloneVector = v1.clone();
        Assertions.assertEquals(v1, cloneVector);
    }

    @Test
    void testSubVector() {
        Vector2d v2 = new Vector2d(1, 2);
        v1.subVector(v2);

        Assertions.assertEquals(new Vector2d(0, -1), v1);
    }

    @Test
    void testModule() {
        Assertions.assertEquals(Math.sqrt(2), v1.module());
    }

    @Test
    void testEquals1() {
        Assertions.assertEquals(v1, v1);
    }

    @Test
    void testEquals2() {
        Assertions.assertNotEquals(null, v1);
    }

    @Test
    void testEquals3() {
        Assertions.assertNotEquals(new Object(), v1);
    }
}
