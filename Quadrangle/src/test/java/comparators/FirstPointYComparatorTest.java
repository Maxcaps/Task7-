package comparators;

import org.example.comparators.FirstPointYComparator;
import org.example.model.Point;
import org.example.model.Quadrangle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FirstPointYComparatorTest {

    @Test
    void testCompare_ShouldReturnNegativeWhenFirstIsSmaller() {
        // Дано
        Quadrangle q1 = new Quadrangle(new Point[]{new Point(0, 1), new Point(2, 2), new Point(3, 1), new Point(0, 0)});
        Quadrangle q2 = new Quadrangle(new Point[]{new Point(0, 3), new Point(2, 2), new Point(1, 1), new Point(0, 0)});
        FirstPointYComparator comparator = new FirstPointYComparator();

        // Когда
        int result = comparator.compare(q1, q2);

        // Тогда
        assertTrue(result < 0, "q1 должен быть меньше q2 по Y-координате");
    }

    @Test
    void testCompare_ShouldReturnZeroWhenEqual() {
        // Дано
        Quadrangle q1 = new Quadrangle(new Point[]{new Point(0, 2), new Point(2, 2), new Point(3, 1), new Point(0, 0)});
        Quadrangle q2 = new Quadrangle(new Point[]{new Point(0, 2), new Point(2, 2), new Point(1, 1), new Point(0, 0)});
        FirstPointYComparator comparator = new FirstPointYComparator();

        // Когда
        int result = comparator.compare(q1, q2);

        // Тогда
        assertEquals(0, result, "Координаты Y первых точек должны быть равны");
    }

    @Test
    void testCompare_ShouldReturnPositiveWhenFirstIsLarger() {
        // Дано
        Quadrangle q1 = new Quadrangle(new Point[]{new Point(0, 3), new Point(2, 2), new Point(3, 1), new Point(0, 0)});
        Quadrangle q2 = new Quadrangle(new Point[]{new Point(0, 1), new Point(2, 2), new Point(1, 1), new Point(0, 0)});
        FirstPointYComparator comparator = new FirstPointYComparator();

        // Когда
        int result = comparator.compare(q1, q2);

        // Тогда
        assertTrue(result > 0, "q1 должен быть больше q2 по Y-координате");
    }
}

