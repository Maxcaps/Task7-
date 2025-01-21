package org.example.specifications;

import org.example.calculators.QuadrangleCalculator;
import org.example.model.Quadrangle;

public class AreaSpecification implements QuadrangleSpecification {
    private final double minArea;
    private final double maxArea;
    private final QuadrangleCalculator calculator;

    public AreaSpecification(double minArea, double maxArea, QuadrangleCalculator calculator) {
        this.minArea = minArea;
        this.maxArea = maxArea;
        this.calculator = calculator;
    }

    @Override
    public boolean specified(Quadrangle object) {
        double area = calculator.calculateArea(object);
        return area >= minArea && area <= maxArea;
    }
}
