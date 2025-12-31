package com.aor.components.sprites;

import java.util.Objects;

public class Pixel {
    private final String argbColor;
    private final String rgbColor;
    private final Short alpha;

    public Pixel(Integer color){
        argbColor = "#".concat(String.format("%08x", color));
        rgbColor = "#".concat(argbColor.substring(3));
        alpha = (short) (color>>24 & 0xFF);
    }

    public String toString(){
        return  rgbColor;
    }
    public String getArgbColor(){
        return argbColor;
    }
    public Short getAlpha() {
        return alpha;
    }

    @Override
    public int hashCode() {
        return Objects.hash(argbColor);
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null) return false;
        if (getClass() != o.getClass()) return false;

        Pixel p = (Pixel) o;
        return this.hashCode() == p.hashCode();
    }
}
