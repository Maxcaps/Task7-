package comparators;

import org.example.comparators.IdComparator;
import org.example.model.Point;
import org.example.model.Quadrangle;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IdComparatorTest {

    @Test
    void testCompare_ShouldReturnNegativeWhenFirstIdIsSmaller() {
        // Дано: два четырехугольника с разными ID
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        Quadrangle q1 = new Quadrangle(
                new Point[]{
                        new Point(0, 0),
                        new Point(1, 0),
                        new Point(1, 1),
                        new Point(0, 1)
                },
                id1
        );
        Quadrangle q2 = new Quadrangle(
                new Point[]{
                        new Point(2, 2),
                        new Point(3, 2),
                        new Point(3, 3),
                        new Point(2, 3)
                },
                id2
        );

        IdComparator comparator = new IdComparator();

        // Когда: сравниваем по ID
        int result = comparator.compare(q1, q2);

        // Тогда: результат должен соответствовать сравнению ID
        assertEquals(id1.compareTo(id2), result, "Сравнение ID должно быть корректным");
    }
}


