package observer_pattern;

import org.example.AutoCADRecorder;
import org.example.QuadrangleType;
import org.example.calculators.QuadrangleCalculator;
import org.example.model.Point;
import org.example.model.Quadrangle;
import org.example.model.QuadrangleParameters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AutoCADRecorderTest {

    @Mock
    private QuadrangleCalculator mockCalculator;

    private AutoCADRecorder autoCADRecorder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        autoCADRecorder = new AutoCADRecorder(mockCalculator);
    }

    @Test
    void testHandleUpdate_ShouldAddQuadrangleToSubs() {
        // Arrange
        UUID mockId = UUID.randomUUID();
        Point[] points = new Point[]{
                new Point(0, 0), new Point(0, 4),
                new Point(4, 4), new Point(4, 0)
        };
        Quadrangle quadrangle = new Quadrangle(points, mockId);

        when(mockCalculator.calculateArea(quadrangle)).thenReturn(16.0);
        when(mockCalculator.calculatePerimeter(quadrangle)).thenReturn(16.0);
        when(mockCalculator.findQuadrangleType(quadrangle)).thenReturn(QuadrangleType.SQUARE);
        when(mockCalculator.isConvex(quadrangle)).thenReturn(true);

        // Act
        autoCADRecorder.handleUpdate(quadrangle);

        // Assert
        Optional<QuadrangleParameters> result = autoCADRecorder.getQuadrangleParameters(mockId);
        assertTrue(result.isPresent());
        assertEquals(16.0, result.get().getArea());
        assertEquals(16.0, result.get().getPerimeter());
        assertEquals(QuadrangleType.SQUARE, result.get().getType());
        assertTrue(result.get().isConvex());
    }
    @Test
    void testGetQuadrangleParameters_Found() {
        // Arrange
        UUID mockId = UUID.randomUUID();
        Point[] points = new Point[]{
                new Point(0, 0), new Point(0, 4),
                new Point(4, 4), new Point(4, 0)
        };
        Quadrangle quadrangle = new Quadrangle(points, mockId);

        // Настраиваем мок для расчёта параметров
        when(mockCalculator.calculateArea(quadrangle)).thenReturn(20.0);
        when(mockCalculator.calculatePerimeter(quadrangle)).thenReturn(18.0);
        when(mockCalculator.findQuadrangleType(quadrangle)).thenReturn(QuadrangleType.RECTANGLE);
        when(mockCalculator.isConvex(quadrangle)).thenReturn(true);

        // Вызываем handleUpdate, чтобы добавить параметры
        autoCADRecorder.handleUpdate(quadrangle);

        // Act
        Optional<QuadrangleParameters> result = autoCADRecorder.getQuadrangleParameters(mockId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(20.0, result.get().getArea()); // Проверяем площадь
        assertEquals(18.0, result.get().getPerimeter()); // Проверяем периметр
        assertEquals(QuadrangleType.RECTANGLE, result.get().getType()); // Проверяем тип
        assertTrue(result.get().isConvex()); // Проверяем выпуклость
    }


    @Test
    void testGetQuadrangleParameters_NotFound() {
        // Arrange
        UUID mockId = UUID.randomUUID();

        // Act
        Optional<QuadrangleParameters> result = autoCADRecorder.getQuadrangleParameters(mockId);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void testIsQuadranglePublisher_True() {
        // Arrange
        UUID mockId = UUID.randomUUID();
        Quadrangle quadrangle = new Quadrangle(new Point[]{
                new Point(0, 0), new Point(0, 4),
                new Point(4, 4), new Point(4, 0)
        }, mockId);

        autoCADRecorder.handleUpdate(quadrangle);

        // Act
        boolean result = autoCADRecorder.isQuadranglePublisher(mockId);

        // Assert
        assertTrue(result);
    }

    @Test
    void testIsQuadranglePublisher_False() {
        // Arrange
        UUID mockId = UUID.randomUUID();

        // Act
        boolean result = autoCADRecorder.isQuadranglePublisher(mockId);

        // Assert
        assertFalse(result);
    }

    @Test
    void testUpdateQuadrangleNumericParameters_ShouldUpdateExistingParameters() {
        // Arrange
        UUID mockId = UUID.randomUUID();
        Point[] points = new Point[]{
                new Point(0, 0), new Point(0, 4),
                new Point(4, 4), new Point(4, 0)
        };
        Quadrangle quadrangle = new Quadrangle(points, mockId);

        when(mockCalculator.calculateArea(quadrangle)).thenReturn(16.0);
        when(mockCalculator.calculatePerimeter(quadrangle)).thenReturn(16.0);
        when(mockCalculator.findQuadrangleType(quadrangle)).thenReturn(QuadrangleType.SQUARE);
        when(mockCalculator.isConvex(quadrangle)).thenReturn(true);

        autoCADRecorder.handleUpdate(quadrangle);

        // Act
        autoCADRecorder.updateQuadrangleNumericParameters(mockId, 24.0, 20.0);

        // Assert
        Optional<QuadrangleParameters> updatedParams = autoCADRecorder.getQuadrangleParameters(mockId);
        assertTrue(updatedParams.isPresent());
        assertEquals(20.0, updatedParams.get().getArea());
        assertEquals(24.0, updatedParams.get().getPerimeter());
    }

    @Test
    void testUpdateQuadrangleNumericParameters_ThrowsExceptionIfNotFound() {
        // Arrange
        UUID mockId = UUID.randomUUID();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                autoCADRecorder.updateQuadrangleNumericParameters(mockId, 24.0, 20.0));
    }

    @Test
    void testUpdateQuadrangleNumericParameters_ThrowsExceptionForInvalidInputs() {
        // Arrange
        UUID mockId = UUID.randomUUID();
        Quadrangle quadrangle = new Quadrangle(new Point[]{
                new Point(0, 0), new Point(0, 4),
                new Point(4, 4), new Point(4, 0)
        }, mockId);

        autoCADRecorder.handleUpdate(quadrangle);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                autoCADRecorder.updateQuadrangleNumericParameters(mockId, 0, 20.0));

        assertThrows(IllegalArgumentException.class, () ->
                autoCADRecorder.updateQuadrangleNumericParameters(null, 24.0, 20.0));
    }
}

