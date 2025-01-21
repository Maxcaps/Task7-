package org.example.specifications;

import org.example.calculators.QuadrangleCalculator;
import org.example.model.Quadrangle;

public class PerimeterSpecification implements QuadrangleSpecification {
    private final QuadrangleCalculator calculator;
    private final double minPerimeter;
    private final double maxPerimeter;

    public PerimeterSpecification(double minPerimeter, double maxPerimeter, QuadrangleCalculator calculator) {
        this.minPerimeter = minPerimeter;
        this.maxPerimeter = maxPerimeter;
        this.calculator = calculator;
    }

    @Override
    public boolean specified(Quadrangle object) {
        double perimeter = calculator.calculatePerimeter(object);
        return perimeter >= minPerimeter && perimeter <= maxPerimeter;
    }
}
