package calculators;

import org.example.model.Point;
import org.example.calculators.support.PointsCalculator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PointsCalculatorTest {

    private final PointsCalculator pointsCalculator = new PointsCalculator();

    @Test
    public void testCalculateDistanceBetweenPoints() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(3, 4);
        double distance = pointsCalculator.calculateDistanceBetweenPoints(p1, p2);
        assertEquals(5.0, distance, 1e-6, "Расстояние между точками (0,0) и (3,4) должно быть 5.0");
    }

    @Test
    public void testCalculateDistanceBetweenPoints_SamePoints() {
        Point p1 = new Point(1, 1);
        PointsCalculator pointsCalculator = new PointsCalculator();
        double distance = pointsCalculator.calculateDistanceBetweenPoints(p1, p1);
        assertEquals(0.0, distance, 1e-6, "Расстояние между одинаковыми точками должно быть 0.0");
    }

    @Test
    public void testCalculateDistanceBetweenPoints_HorizontalPoints() {
        Point p1 = new Point(2, 5);
        Point p2 = new Point(7, 5);
        PointsCalculator pointsCalculator = new PointsCalculator();
        double distance = pointsCalculator.calculateDistanceBetweenPoints(p1, p2);
        assertEquals(5.0, distance, 1e-6, "Расстояние между точками (2,5) и (7,5) должно быть 5.0");
    }

    @Test
    public void testCalculateDistanceBetweenPoints_VerticalPoints() {
        Point p1 = new Point(4, 2);
        Point p2 = new Point(4, 7);
        PointsCalculator pointsCalculator = new PointsCalculator();
        double distance = pointsCalculator.calculateDistanceBetweenPoints(p1, p2);
        assertEquals(5.0, distance, 1e-6, "Расстояние между точками (4,2) и (4,7) должно быть 5.0");
    }

    @Test
    public void testIsCollinearPoints_Collinear() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(2, 2);
        Point p3 = new Point(4, 4);
        PointsCalculator pointsCalculator = new PointsCalculator();
        boolean isCollinear = pointsCalculator.isCollinearPoints(p1, p2, p3);
        assertTrue(isCollinear, "Точки (0,0), (2,2) и (4,4) должны быть коллинеарными");
    }

    @Test
    public void testIsCollinearPoints_NotCollinear() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(1, 2);
        Point p3 = new Point(2, 1);
        PointsCalculator pointsCalculator = new PointsCalculator();
        boolean isCollinear = pointsCalculator.isCollinearPoints(p1, p2, p3);
        assertFalse(isCollinear, "Точки (0,0), (1,2) и (2,1) не должны быть коллинеарными");
    }

    @Test
    public void testIsCollinearPoints_SamePoints() {
        Point p1 = new Point(1, 1);
        Point p2 = new Point(1, 1);
        Point p3 = new Point(1, 1);
        PointsCalculator pointsCalculator = new PointsCalculator();
        boolean isCollinear = pointsCalculator.isCollinearPoints(p1, p2, p3);
        assertTrue(isCollinear, "Одинаковые точки должны считаться коллинеарными");
    }


}
