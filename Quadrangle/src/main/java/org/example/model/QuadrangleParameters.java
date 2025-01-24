package org.example.model;

import org.example.QuadrangleType;
import org.example.calculators.QuadrangleCalculator;

public class QuadrangleParameters {
    private final QuadrangleCalculator quadrangleCalculator;
    private final double area;
    private final double perimeter;
    private final QuadrangleType type;
    private final boolean isConvex;

    public QuadrangleParameters(double area, double perimeter, QuadrangleType type, boolean isConvex, QuadrangleCalculator quadrangleCalculator) {
        this.quadrangleCalculator = quadrangleCalculator;
        this.area = area;
        this.perimeter = perimeter;
        this.type = type;
        this.isConvex = isConvex;
    }

    public QuadrangleParameters(Quadrangle quadrangle, QuadrangleCalculator quadrangleCalculator) {
        this.quadrangleCalculator = quadrangleCalculator;
        this.area = quadrangleCalculator.calculateArea(quadrangle);
        this.perimeter = quadrangleCalculator.calculatePerimeter(quadrangle);
        this.type = quadrangleCalculator.findQuadrangleType(quadrangle);
        this.isConvex = quadrangleCalculator.isConvex(quadrangle);
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
