package calculators;

import org.example.QuadrangleType;
import org.example.calculators.QuadrangleCalculator;
import org.example.calculators.support.AnglesCalculator;
import org.example.calculators.support.PointsCalculator;
import org.example.model.Point;
import org.example.model.Quadrangle;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuadrangleCalculatorTest {

    private final PointsCalculator pointsCalculatorMock = mock(PointsCalculator.class);
    private final AnglesCalculator anglesCalculatorMock = mock(AnglesCalculator.class);
    private final QuadrangleCalculator quadrangleCalculator = new QuadrangleCalculator(pointsCalculatorMock, anglesCalculatorMock);

    @Test
    void testCalculatePerimeter_ShouldReturnCorrectValue() {
        // Дано: точки и расстояния между ними
        Point[] points = {
                new Point(0, 0),
                new Point(4, 0),
                new Point(4, 3),
                new Point(0, 3)
        };
        Quadrangle quadrangle = new Quadrangle(points);

        when(pointsCalculatorMock.calculateDistanceBetweenPoints(points[0], points[1])).thenReturn(4.0);
        when(pointsCalculatorMock.calculateDistanceBetweenPoints(points[1], points[2])).thenReturn(3.0);
        when(pointsCalculatorMock.calculateDistanceBetweenPoints(points[2], points[3])).thenReturn(4.0);
        when(pointsCalculatorMock.calculateDistanceBetweenPoints(points[3], points[0])).thenReturn(3.0);

        // Когда: вызываем метод calculatePerimeter
        double result = quadrangleCalculator.calculatePerimeter(quadrangle);

        // Тогда: результат должен быть 14.0
        assertEquals(14.0, result, 1e-6, "Периметр рассчитан неверно");
    }

    @Test
    void testCalculateArea_ShouldReturnCorrectValue() {
        // Дано: прямоугольник с известной площадью
        Point[] points = {
                new Point(0, 0),
                new Point(4, 0),
                new Point(4, 3),
                new Point(0, 3)
        };
        Quadrangle quadrangle = new Quadrangle(points);

        when(pointsCalculatorMock.calculateDistanceBetweenPoints(points[0], points[1])).thenReturn(4.0);
        when(pointsCalculatorMock.calculateDistanceBetweenPoints(points[1], points[2])).thenReturn(3.0);
        when(pointsCalculatorMock.calculateDistanceBetweenPoints(points[2], points[3])).thenReturn(4.0);
        when(pointsCalculatorMock.calculateDistanceBetweenPoints(points[3], points[0])).thenReturn(3.0);
        when(pointsCalculatorMock.calculateDistanceBetweenPoints(points[0], points[2])).thenReturn(5.0);

        // Когда: вызываем метод calculateArea
        double result = quadrangleCalculator.calculateArea(quadrangle);

        // Тогда: результат должен быть 12.0
        assertEquals(12.0, result, 1e-6, "Площадь рассчитана неверно");
    }

    @Test
    void testIsQuadrangle_ShouldReturnTrueForValidQuadrangle() {
        // Дано: неколлинеарные точки
        Point[] points = {
                new Point(0, 0),
                new Point(1, 1),
                new Point(2, 0),
                new Point(1, -1)
        };
        Quadrangle quadrangle = new Quadrangle(points);

        when(pointsCalculatorMock.isCollinearPoints(points[0], points[1], points[2])).thenReturn(false);
        when(pointsCalculatorMock.isCollinearPoints(points[1], points[2], points[3])).thenReturn(false);

        // Когда: вызываем метод isQuadrangle
        boolean result = quadrangleCalculator.isQuadrangle(quadrangle);

        // Тогда: результат должен быть true
        assertTrue(result, "Должен быть допустимый четырехугольник");
    }

    @Test
    void testFindQuadrangleType_ShouldReturnSquare() {
        // Дано: квадрат
        Point[] points = {
                new Point(0, 0),
                new Point(2, 0),
                new Point(2, 2),
                new Point(0, 2)
        };
        Quadrangle quadrangle = new Quadrangle(points);

        when(pointsCalculatorMock.calculateDistanceBetweenPoints(points[0], points[1])).thenReturn(2.0);
        when(pointsCalculatorMock.calculateDistanceBetweenPoints(points[1], points[2])).thenReturn(2.0);
        when(pointsCalculatorMock.calculateDistanceBetweenPoints(points[2], points[3])).thenReturn(2.0);
        when(pointsCalculatorMock.calculateDistanceBetweenPoints(points[3], points[0])).thenReturn(2.0);

        when(anglesCalculatorMock.calculateAngles(quadrangle)).thenReturn(Arrays.asList(90.0, 90.0, 90.0, 90.0));
        when(anglesCalculatorMock.isAnglesRight(anyList())).thenReturn(true);

        // Когда: вызываем метод findQuadrangleType
        QuadrangleType result = quadrangleCalculator.findQuadrangleType(quadrangle);

        // Тогда: результат должен быть SQUARE
        assertEquals(QuadrangleType.SQUARE, result, "Должен быть тип SQUARE");
    }

    @Test
    void testIsConvex_ShouldReturnTrueForConvexQuadrangle() {
        // Дано: углы меньше 180 градусов
        Point[] points = {
                new Point(0, 0),
                new Point(2, 0),
                new Point(2, 2),
                new Point(0, 2)
        };
        Quadrangle quadrangle = new Quadrangle(points);

        when(anglesCalculatorMock.calculateAngles(quadrangle)).thenReturn(Arrays.asList(90.0, 90.0, 90.0, 90.0));

        // Когда: вызываем метод isConvex
        boolean result = quadrangleCalculator.isConvex(quadrangle);

        // Тогда: результат должен быть true
        assertTrue(result, "Должен быть выпуклый четырехугольник");
    }
}
