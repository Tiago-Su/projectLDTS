package com.aor.utils;

import java.util.Objects;

public class Vector2d implements Cloneable {
    private double x;
    private double y;

    public Vector2d(Vector2d other) {
        this.x = other.getX();
        this.y = other.getY();
    }

    public Vector2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    //Some useful methods
    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null) return false;
        if (getClass() != other.getClass()) return false;

        Vector2d o = (Vector2d) other;
        return this.hashCode() == o.hashCode();
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public Vector2d clone() {
        return new Vector2d(x, y);
    }

    public Vector2d addVector(Vector2d other) {
        this.x += other.getX();
        this.y += other.getY();

        return this;
    }

    public Vector2d scalarProduct(double k) {
        this.x *= k;
        this.y *= k;

        return this;
    }

    public Vector2d subVector(Vector2d other) {
        this.x -= other.getX();
        this.y -= other.getY();

        return this;
    }

    public double module(){
        return Math.sqrt(x * x + y * y);
    }

    public Vector2d normalize() {
        double module = module();
        if (module == 0) return this;

        scalarProduct(1 / module);
        return this;
    }

    //To do: setters and getters
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}
