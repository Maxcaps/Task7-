package org.example.calculators.support;

import org.example.model.MathVector;
import org.example.model.Point;
import org.example.model.Quadrangle;

import java.util.ArrayList;
import java.util.List;

public class AnglesCalculator {
    private final VectorCalculator vectorCalculator;

    public AnglesCalculator(VectorCalculator vectorCalculator) {
        this.vectorCalculator = vectorCalculator;
    }

    public List<Double> calculateAngles(Quadrangle quadrangle) {
        List<Double> angles = new ArrayList<>();
        Point[] points = quadrangle.getPoints();
        for (int i = 0; i < 4; i++) {
            MathVector v1 = new MathVector(points[i], points[(i + 1) % 4]);
            MathVector v2 = new MathVector(points[(i + 1) % 4], points[(i + 2) % 4]);
            angles.add(vectorCalculator.calculateAngle(v1, v2));
        }
        return angles;
    }

    public boolean isAnglesRight(List<Double> angles) { // проверка на квадрат
        return angles.stream().allMatch(angle -> Math.abs(angle - 90) < 1e-6);
    }

}
