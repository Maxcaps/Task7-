package org.example.calculators;

import org.example.QuadrangleType;
import org.example.calculators.support.AnglesCalculator;
import org.example.calculators.support.PointsCalculator;
import org.example.model.MathVector;
import org.example.model.Point;
import org.example.model.Quadrangle;

import java.util.Arrays;
import java.util.List;

public class QuadrangleCalculator {

    private final PointsCalculator pointsCalculator;
    private final AnglesCalculator anglesCalculator;

    public QuadrangleCalculator(PointsCalculator pointsCalculator, AnglesCalculator anglesCalculator) {
        this.pointsCalculator = pointsCalculator;
        this.anglesCalculator = anglesCalculator;
    }

    public double calculatePerimeter(Quadrangle quadrangle) {
        Point[] points = quadrangle.getPoints();
        double perimeter = 0;
        for (int i = 0; i < 4; i++) {
            perimeter += pointsCalculator.calculateDistanceBetweenPoints(points[i], points[(i + 1) % 4]);
        }
        return perimeter;
    }

    public double calculateArea(Quadrangle quadrangle) {
        Point[] points = quadrangle.getPoints();
        if (points.length != 4) {
            throw new IllegalArgumentException("Quadrangle must have exactly 4 points.");
        }
        PointsCalculator pointsCalculator = new PointsCalculator();
        double a = pointsCalculator.calculateDistanceBetweenPoints(points[0], points[1]);
        double b = pointsCalculator.calculateDistanceBetweenPoints(points[1], points[2]);
        double c = pointsCalculator.calculateDistanceBetweenPoints(points[2], points[3]);
        double d = pointsCalculator.calculateDistanceBetweenPoints(points[3], points[0]);
        double diagonal = pointsCalculator.calculateDistanceBetweenPoints(points[0], points[2]);
        double semiPerimeter1 = (a + b + diagonal) / 2;
        double area1 = Math.sqrt(semiPerimeter1 * (semiPerimeter1 - a) * (semiPerimeter1 - b) * (semiPerimeter1 - diagonal));
        double semiPerimeter2 = (c + d + diagonal) / 2;
        double area2 = Math.sqrt(semiPerimeter2 * (semiPerimeter2 - c) * (semiPerimeter2 - d) * (semiPerimeter2 - diagonal));
        return area1 + area2;
    }

    public boolean isQuadrangle(Quadrangle quadrangle) {
        Point[] points = quadrangle.getPoints();
        return !pointsCalculator.isCollinearPoints(points[0], points[1], points[2]) &&
                !pointsCalculator.isCollinearPoints(points[1], points[2], points[3]);
    }

    public QuadrangleType findQuadrangleType(Quadrangle quadrangle) {
        List<Double> angles = anglesCalculator.calculateAngles(quadrangle);
        boolean rightAngles = anglesCalculator.isAnglesRight(angles);
        Point[] points = quadrangle.getPoints();

        // Длины сторон
        double d1 = pointsCalculator.calculateDistanceBetweenPoints(points[0], points[1]);
        double d2 = pointsCalculator.calculateDistanceBetweenPoints(points[1], points[2]);
        double d3 = pointsCalculator.calculateDistanceBetweenPoints(points[2], points[3]);
        double d4 = pointsCalculator.calculateDistanceBetweenPoints(points[3], points[0]);

        // Векторы для проверки параллельности сторон
        MathVector v1 = new MathVector(points[0], points[1]);
        MathVector v2 = new MathVector(points[1], points[2]);
        MathVector v3 = new MathVector(points[2], points[3]);
        MathVector v4 = new MathVector(points[3], points[0]);

        // Проверка на квадрат или ромб
        if (d1 == d2 && d2 == d3 && d3 == d4) {
            return rightAngles ? QuadrangleType.SQUARE : QuadrangleType.RHOMBUS;
        }
        // Проверка на прямоугольник
        else if (rightAngles) {
            return QuadrangleType.RECTANGLE;
        }
        // Проверка на параллелограмм
        else if (d1 == d3 && d2 == d4 && isParallel(v1, v3) && isParallel(v2, v4)) {
            return QuadrangleType.PARALLELOGRAM;
        }
        // Проверка на трапецию (одна пара сторон параллельна)
        else if (isParallel(v1, v3) || isParallel(v2, v4)) {
            return QuadrangleType.TRAPEZOID;
        }
        // Неизвестный тип
        else {
            return QuadrangleType.NO_TYPE;
        }
    }

    public boolean isConvex(Quadrangle quadrangle) {
        List<Double> angles = anglesCalculator.calculateAngles(quadrangle);
        return angles.stream().allMatch(angle -> angle < 180.0);
    }

    private boolean isParallel(MathVector v1, MathVector v2) {
        return Math.abs(v1.getX() * v2.getY() - v1.getY() * v2.getX()) < 1e-6;
    }

    public double calculateMaxDistanceFromOrigin(Quadrangle quadrangle) {
        Point[] points = quadrangle.getPoints();
        double maxDistance = 0;

        for (Point point : points) {
            double distance = Math.sqrt(Math.pow(point.getX(), 2) + Math.pow(point.getY(), 2));
            if (distance > maxDistance) {
                maxDistance = distance;
            }
        }

        return maxDistance;
    }


}
