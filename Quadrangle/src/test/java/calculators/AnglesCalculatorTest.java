package calculators;

import org.example.calculators.support.AnglesCalculator;
import org.example.calculators.support.VectorCalculator;
import org.example.model.MathVector;
import org.example.model.Point;
import org.example.model.Quadrangle;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AnglesCalculatorTest {

    // Поля для хранения мока и экземпляра тестируемого класса
    private final VectorCalculator vectorCalculatorMock = mock(VectorCalculator.class);
    private final AnglesCalculator anglesCalculator = new AnglesCalculator(vectorCalculatorMock);

    @Test
    void calculateAngles_ShouldReturnCorrectAngles() {
        // Дано: создаём квадрат с известными точками
        Point[] points = {
                new Point(0, 0),
                new Point(1, 0),
                new Point(1, 1),
                new Point(0, 1)
        };
        Quadrangle quadrangle = new Quadrangle(points);

        // Настраиваем мок: метод calculateAngle всегда возвращает 90 градусов
        when(vectorCalculatorMock.calculateAngle(any(MathVector.class), any(MathVector.class))).thenReturn(90.0);

        // Когда: вызываем метод calculateAngles
        List<Double> angles = anglesCalculator.calculateAngles(quadrangle);

        // Тогда: проверяем результат
        assertEquals(4, angles.size(), "Должно быть 4 угла"); // Проверяем, что углов ровно 4
        assertTrue(angles.stream().allMatch(angle -> angle == 90.0), "Все углы должны быть 90 градусов"); // Проверяем, что все углы равны 90
        verify(vectorCalculatorMock, times(4)).calculateAngle(any(MathVector.class), any(MathVector.class)); // Проверяем, что метод вызывался 4 раза
    }

    @Test
    void calculateAngles_ShouldHandleNonRightAngles() {
        // Дано: создаём произвольный четырёхугольник
        Point[] points = {
                new Point(0, 0),
                new Point(2, 0),
                new Point(3, 2),
                new Point(0, 3)
        };
        Quadrangle quadrangle = new Quadrangle(points);

        // Настраиваем мок: последовательность возвращаемых углов 90, 75, 105, 90
        when(vectorCalculatorMock.calculateAngle(any(MathVector.class), any(MathVector.class)))
                .thenReturn(90.0, 75.0, 105.0, 90.0);

        // Когда: вызываем метод calculateAngles
        List<Double> angles = anglesCalculator.calculateAngles(quadrangle);

        // Тогда: проверяем результат
        assertEquals(4, angles.size(), "Должно быть 4 угла"); // Проверяем, что углов ровно 4
        assertIterableEquals(Arrays.asList(90.0, 75.0, 105.0, 90.0), angles, "Углы должны совпадать с ожидаемыми значениями"); // Проверяем последовательность углов
    }

    @Test
    void isAnglesRight_ShouldReturnTrueForRightAngles() {
        // Дано: передаём список углов, все равны 90
        List<Double> angles = Arrays.asList(90.0, 90.0, 90.0, 90.0);

        // Когда: вызываем метод isAnglesRight
        boolean result = anglesCalculator.isAnglesRight(angles);

        // Тогда: результат должен быть true
        assertTrue(result, "Все углы 90 градусов — должно вернуть true");
    }

    @Test
    void isAnglesRight_ShouldReturnFalseForNonRightAngles() {
        // Дано: передаём список углов, где есть углы не равные 90
        List<Double> angles = Arrays.asList(90.0, 75.0, 105.0, 90.0);

        // Когда: вызываем метод isAnglesRight
        boolean result = anglesCalculator.isAnglesRight(angles);

        // Тогда: результат должен быть false
        assertFalse(result, "Не все углы 90 градусов — должно вернуть false");
    }
}
