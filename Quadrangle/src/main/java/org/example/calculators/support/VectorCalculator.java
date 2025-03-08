package org.example.calculators.support;

import org.example.model.MathVector;

public class VectorCalculator {
    public double calculateAngle(MathVector v1, MathVector v2) {
        double scalarProduct = calculateScalarProduct(v1, v2);
        double magnitudeProduct = calculateVectorLength(v1) * calculateVectorLength(v2);
        return Math.toDegrees(Math.acos(scalarProduct / magnitudeProduct));
    }

    public double calculateScalarProduct(MathVector v1, MathVector v2) {
        return v1.getX() * v2.getX() + v1.getY() * v2.getY();
    }

    public double calculateVectorLength(MathVector vector) {
        return Math.sqrt(vector.getX() * vector.getX() + vector.getY() * vector.getY());
    }
}
