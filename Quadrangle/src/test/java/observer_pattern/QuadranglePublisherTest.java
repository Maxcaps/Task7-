package observer_pattern;

import org.example.AbstractSubscriber;
import org.example.QuadranglePublisher;
import org.example.model.Point;
import org.example.model.Quadrangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuadranglePublisherTest {

    private QuadranglePublisher quadranglePublisher;
    private AbstractSubscriber<Quadrangle> mockSubscriber;
    private AbstractSubscriber<Quadrangle> mockSubscriber2;

    @SuppressWarnings("unchecked")
    @BeforeEach
    void setUp() {
        Point[] points = new Point[]{
                new Point(0, 0), new Point(0, 4),
                new Point(4, 4), new Point(4, 0)
        };
        quadranglePublisher = new QuadranglePublisher(points);
        mockSubscriber = Mockito.mock(AbstractSubscriber.class);
        mockSubscriber2 = Mockito.mock(AbstractSubscriber.class);
    }

    @Test
    void testAddSubscriber() {
        // Act
        quadranglePublisher.addSubscriber(mockSubscriber);

        // Assert
        quadranglePublisher.notifySubscribers(); // Проверяем, вызывается ли метод update
        verify(mockSubscriber, times(1)).update(quadranglePublisher);
    }
//
    @Test
    void testRemoveSubscriber() {
        // Arrange
        quadranglePublisher.addSubscriber(mockSubscriber);

        // Act
        quadranglePublisher.removeSubscriber(mockSubscriber);

        // Assert
        quadranglePublisher.notifySubscribers(); // Проверяем, что метод update не вызывается
        verify(mockSubscriber, times(0)).update(quadranglePublisher);
    }

    @Test
    void testNotifySubscribers() {
        // Arrange
        quadranglePublisher.addSubscriber(mockSubscriber);
        quadranglePublisher.addSubscriber(mockSubscriber2);

        // Act
        quadranglePublisher.notifySubscribers();

        // Assert
        verify(mockSubscriber, times(1)).update(quadranglePublisher);
        verify(mockSubscriber2, times(1)).update(quadranglePublisher);
    }

    @Test
    void testUpdatePoints_ShouldNotifySubscribers() {
        // Arrange
        quadranglePublisher.addSubscriber(mockSubscriber);
        List<Point> newPoints = Arrays.asList(
                new Point(1, 1), new Point(1, 5),
                new Point(5, 5), new Point(5, 1)
        );

        // Act
        quadranglePublisher.updatePoints(newPoints);

        // Assert
        verify(mockSubscriber, times(1)).update(quadranglePublisher);

        // Проверяем, что точки обновлены
        Point[] updatedPoints = quadranglePublisher.getPoints();
        assertEquals(1, updatedPoints[0].getX());
        assertEquals(1, updatedPoints[0].getY());
        assertEquals(5, updatedPoints[1].getY());
    }

    @Test
    void testSubscribersSetUniqueness() {
        // Act
        quadranglePublisher.addSubscriber(mockSubscriber);
        quadranglePublisher.addSubscriber(mockSubscriber); // Добавляем дважды одного и того же подписчика
        quadranglePublisher.notifySubscribers();

        // Assert
        // Проверяем, что `update` вызван только один раз
        verify(mockSubscriber, times(1)).update(quadranglePublisher);
    }
}
