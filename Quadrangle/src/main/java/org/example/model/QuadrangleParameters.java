package org.example.model;

import org.example.QuadrangleType;

public class QuadrangleParameters {
    private final double area;
    private final double perimeter;
    private final QuadrangleType type;
    private final boolean isConvex;

    public QuadrangleParameters(double area, double perimeter, QuadrangleType type, boolean isConvex) {
        this.area = area;
        this.perimeter = perimeter;
        this.type = type;
        this.isConvex = isConvex;
    }

    public double getArea() {
        return area;
    }

    public double getPerimeter() {
        return perimeter;
    }

    public QuadrangleType getType() {
        return type;
    }

    public boolean isConvex() {
        return isConvex;
    }

    @Override
    public String toString() {
        return "QuadrangleParameters{" +
                "area = " + area +
                ", perimeter = " + perimeter +
                ", type = " + type +
                ", isConvex = " + isConvex +
                '}';
    }

}
