package org.example.model;

public class MathVector {
    private final double x;
    private final double y;

    public MathVector(Point from, Point to) {
        this.x = to.getX() - from.getX();
        this.y = to.getY() - from.getY();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
