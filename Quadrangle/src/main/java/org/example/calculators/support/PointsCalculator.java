package org.example.calculators.support;

import org.example.model.Point;

public class PointsCalculator {
    public double calculateDistanceBetweenPoints(Point p1, Point p2) {
        return Math.sqrt(Math.pow(p2.getX() - p1.getX(), 2) + Math.pow(p2.getY() - p1.getY(), 2));
    }

    public boolean isCollinearPoints(Point p1, Point p2, Point p3) {
        return (p2.getY() - p1.getY()) * (p3.getX() - p2.getX()) == (p3.getY() - p2.getY()) * (p2.getX() - p1.getX());
    }

}
