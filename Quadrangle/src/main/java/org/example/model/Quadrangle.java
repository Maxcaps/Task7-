package org.example.model;

import java.util.UUID;

public class Quadrangle {
    private final UUID id;
    private final Point[] points;

    public Quadrangle(Point[] points) {
        if (points.length != 4) {
            throw new IllegalArgumentException("The quadrangle must contain exactly 4 points.");
        }
        this.id = IdGenerator.generateId();
        this.points = points;
    }

    public Quadrangle(Point[] points, UUID id) {
        if (points.length != 4) {
            throw new IllegalArgumentException("The quadrangle must contain exactly 4 points.");
        }
        this.points = points;
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public Point[] getPoints() {
        return points;
    }

}
