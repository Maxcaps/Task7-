package calculators;

import org.example.calculators.support.VectorCalculator;
import org.example.model.MathVector;
import org.example.model.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VectorCalculatorTest {

    private final VectorCalculator vectorCalculator = new VectorCalculator();

    @Test
    void testCalculateScalarProduct_ShouldReturnCorrectValue() {
        // Дано: два вектора, построенных из точек
        Point p1 = new Point(0, 0);
        Point p2 = new Point(1, 2);
        Point p3 = new Point(3, 4);
        MathVector v1 = new MathVector(p1, p2);
        MathVector v2 = new MathVector(p1, p3);

        // Ожидаемое значение
        double expectedScalarProduct = 1 * 3 + 2 * 4;

        // Когда: вызываем calculateScalarProduct
        double result = vectorCalculator.calculateScalarProduct(v1, v2);

        // Тогда: результат должен совпадать с ожидаемым
        assertEquals(expectedScalarProduct, result, 1e-6, "Скалярное произведение рассчитано неверно");
    }

    @Test
    void testCalculateVectorLength_ShouldReturnCorrectLength() {
        // Дано: вектор, построенный из точек
        Point p1 = new Point(0, 0);
        Point p2 = new Point(3, 4);
        MathVector vector = new MathVector(p1, p2);

        // Ожидаемое значение длины
        double expectedLength = Math.sqrt(3 * 3 + 4 * 4);

        // Когда: вызываем calculateVectorLength
        double result = vectorCalculator.calculateVectorLength(vector);

        // Тогда: результат должен совпадать с ожидаемым
        assertEquals(expectedLength, result, 1e-6, "Длина вектора рассчитана неверно");
    }

    @Test
    void testCalculateAngle_ShouldReturn90DegreesForPerpendicularVectors() {
        // Дано: два перпендикулярных вектора, построенных из точек
        Point p1 = new Point(0, 0);
        Point p2 = new Point(1, 0);
        Point p3 = new Point(0, 1);
        MathVector v1 = new MathVector(p1, p2);
        MathVector v2 = new MathVector(p1, p3);

        // Ожидаемое значение угла между векторами (90 градусов)
        double expectedAngle = 90.0;

        // Когда: вызываем calculateAngle
        double result = vectorCalculator.calculateAngle(v1, v2);

        // Тогда: результат должен совпадать с ожидаемым
        assertEquals(expectedAngle, result, 1e-6, "Угол между перпендикулярными векторами рассчитан неверно");
    }

    @Test
    void testCalculateAngle_ShouldReturn0DegreesForParallelVectors() {
        // Дано: два параллельных вектора, построенных из точек
        Point p1 = new Point(0, 0);
        Point p2 = new Point(1, 1);
        Point p3 = new Point(2, 2);
        MathVector v1 = new MathVector(p1, p2);
        MathVector v2 = new MathVector(p1, p3);

        // Ожидаемое значение угла между векторами (0 градусов)
        double expectedAngle = 0.0;

        // Когда: вызываем calculateAngle
        double result = vectorCalculator.calculateAngle(v1, v2);

        // Тогда: результат должен совпадать с ожидаемым
        assertEquals(expectedAngle, result, 1e-6, "Угол между параллельными векторами рассчитан неверно");
    }

    @Test
    void testCalculateAngle_ShouldReturn180DegreesForOppositeVectors() {
        // Дано: два противоположных вектора, построенных из точек
        Point p1 = new Point(0, 0);
        Point p2 = new Point(1, 0);
        Point p3 = new Point(-1, 0);
        MathVector v1 = new MathVector(p1, p2);
        MathVector v2 = new MathVector(p1, p3);

        // Ожидаемое значение угла между векторами (180 градусов)
        double expectedAngle = 180.0;

        // Когда: вызываем calculateAngle
        double result = vectorCalculator.calculateAngle(v1, v2);

        // Тогда: результат должен совпадать с ожидаемым
        assertEquals(expectedAngle, result, 1e-6, "Угол между противоположными векторами рассчитан неверно");
    }
}
