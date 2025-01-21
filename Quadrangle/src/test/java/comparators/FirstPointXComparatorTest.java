package comparators;

import org.example.comparators.FirstPointXComparator;
import org.example.model.Point;
import org.example.model.Quadrangle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FirstPointXComparatorTest {

    @Test
    void testCompare_ShouldReturnNegativeWhenFirstIsSmaller() {
        // Дано
        Quadrangle q1 = new Quadrangle(new Point[]{new Point(1, 0), new Point(2, 2), new Point(3, 1), new Point(0, 0)});
        Quadrangle q2 = new Quadrangle(new Point[]{new Point(3, 0), new Point(2, 2), new Point(1, 1), new Point(0, 0)});
        FirstPointXComparator comparator = new FirstPointXComparator();

        // Когда
        int result = comparator.compare(q1, q2);

        // Тогда
        assertTrue(result < 0, "q1 должен быть меньше q2 по X-координате");
    }

    @Test
    void testCompare_ShouldReturnZeroWhenEqual() {
        // Дано
        Quadrangle q1 = new Quadrangle(new Point[]{new Point(2, 0), new Point(2, 2), new Point(3, 1), new Point(0, 0)});
        Quadrangle q2 = new Quadrangle(new Point[]{new Point(2, 0), new Point(2, 2), new Point(1, 1), new Point(0, 0)});
        FirstPointXComparator comparator = new FirstPointXComparator();

        // Когда
        int result = comparator.compare(q1, q2);

        // Тогда
        assertEquals(0, result, "Координаты X первых точек должны быть равны");
    }

    @Test
    void testCompare_ShouldReturnPositiveWhenFirstIsLarger() {
        // Дано
        Quadrangle q1 = new Quadrangle(new Point[]{new Point(3, 0), new Point(2, 2), new Point(3, 1), new Point(0, 0)});
        Quadrangle q2 = new Quadrangle(new Point[]{new Point(1, 0), new Point(2, 2), new Point(1, 1), new Point(0, 0)});
        FirstPointXComparator comparator = new FirstPointXComparator();

        // Когда
        int result = comparator.compare(q1, q2);

        // Тогда
        assertTrue(result > 0, "q1 должен быть больше q2 по X-координате");
    }
}

