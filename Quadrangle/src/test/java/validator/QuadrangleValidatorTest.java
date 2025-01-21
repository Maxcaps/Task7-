package validator;

import org.example.model.Point;
import org.example.validator.QuadrangleValidator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuadrangleValidatorTest {

    private final QuadrangleValidator quadrangleValidator = new QuadrangleValidator();

    @Test
    void testValidate_ShouldReturnTrueForValidQuadrangle() {
        // Дано: список из 4 уникальных точек
        List<Point> points = Arrays.asList(
                new Point(0, 0),
                new Point(3, 0),
                new Point(3, 4),
                new Point(0, 4)
        );

        // Когда: проверяем метод validate
        boolean result = quadrangleValidator.validate(points);

        // Тогда: результат должен быть true
        assertTrue(result, "Должно возвращаться true для валидного четырёхугольника");
    }

    @Test
    void testValidate_ShouldReturnFalseForDuplicatePoints() {
        // Дано: список с дублирующимися точками
        List<Point> points = Arrays.asList(
                new Point(0, 0),
                new Point(3, 0),
                new Point(3, 0), // Дубликат
                new Point(0, 4)
        );

        // Когда: проверяем метод validate
        boolean result = quadrangleValidator.validate(points);

        // Тогда: результат должен быть false
        assertFalse(result, "Должно возвращаться false для списка с дублирующимися точками");
    }

    @Test
    void testValidate_ShouldReturnFalseForNullList() {
        // Дано: null
        List<Point> points = null;

        // Когда: вызываем метод validate
        boolean result = quadrangleValidator.validate(points);

        // Тогда: результат должен быть false
        assertFalse(result, "Должно возвращаться false для null");
    }

    @Test
    void testValidate_ShouldReturnFalseForLessThanFourPoints() {
        // Дано: список из 3 точек
        List<Point> points = Arrays.asList(
                new Point(0, 0),
                new Point(3, 0),
                new Point(3, 4)
        );

        // Когда: проверяем метод validate
        boolean result = quadrangleValidator.validate(points);

        // Тогда: результат должен быть false
        assertFalse(result, "Должно возвращаться false для списка из менее чем 4 точек");
    }

    @Test
    void testValidate_ShouldReturnFalseForMoreThanFourPoints() {
        // Дано: список из 5 точек
        List<Point> points = Arrays.asList(
                new Point(0, 0),
                new Point(3, 0),
                new Point(3, 4),
                new Point(0, 4),
                new Point(1, 1)
        );

        // Когда: проверяем метод validate
        boolean result = quadrangleValidator.validate(points);

        // Тогда: результат должен быть false
        assertFalse(result, "Должно возвращаться false для списка из более чем 4 точек");
    }

    @Test
    void testValidate_ShouldReturnFalseForEmptyList() {
        // Дано: пустой список
        List<Point> points = new ArrayList<>();

        // Когда: проверяем метод validate
        boolean result = quadrangleValidator.validate(points);

        // Тогда: результат должен быть false
        assertFalse(result, "Должно возвращаться false для пустого списка");
    }
}

