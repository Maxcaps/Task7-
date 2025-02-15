package org.example.model;

public class Quadrangle {
    private final String id;
    private Point[] points;

    public Quadrangle(Point[] points) {
        if (points.length != 4) {
            throw new IllegalArgumentException("The quadrangle must contain exactly 4 points.");
        }
        this.id = IdGenerator.generateId();
        this.points = points;
    }

    public Quadrangle(Point[] points, String id) {
        if (points.length != 4) {
            throw new IllegalArgumentException("The quadrangle must contain exactly 4 points.");
        }
        this.points = points;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Point[] getPoints() {
        return points;
    }

    public void setPoints(Point[] points) {
        if (points.length != 4) {
            throw new IllegalArgumentException("The quadrangle must contain exactly 4 points.");
        }
        this.points = points;
    }
}
