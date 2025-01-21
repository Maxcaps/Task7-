package org.example.specifications;

import org.example.calculators.QuadrangleCalculator;
import org.example.model.Quadrangle;


public class MaxDistanceSpecification implements QuadrangleSpecification {
    private final double maxDistance;
    private final QuadrangleCalculator calculator;

    public MaxDistanceSpecification(double maxDistance,QuadrangleCalculator calculator) {
        this.maxDistance = maxDistance;
        this.calculator = calculator;
    }

    @Override
    public boolean specified(Quadrangle object) {
        double maxPointDistance = calculator.calculateMaxDistanceFromOrigin(object);
        return maxPointDistance <= maxDistance;
    }

}
