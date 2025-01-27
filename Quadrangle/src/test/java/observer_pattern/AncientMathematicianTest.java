package observer_pattern;

import org.example.AncientMathematician;
import org.example.calculators.QuadrangleCalculator;
import org.example.model.Papyrus;
import org.example.model.Point;
import org.example.model.Quadrangle;
import org.example.QuadrangleType;
import org.example.model.QuadrangleParameters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AncientMathematicianTest {

    @Mock
    private QuadrangleCalculator mockCalculator;

    @Mock
    private Papyrus mockPapyrus;

    private AncientMathematician ancientMathematician;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ancientMathematician = new AncientMathematician(mockCalculator, mockPapyrus);
    }

    @Test
    void testHandleUpdate_NewQuadrangle() {
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
        ancientMathematician.handleUpdate(quadrangle);

        // Assert
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(mockPapyrus).write(captor.capture());

        String loggedEntry = captor.getValue();
        assertTrue(loggedEntry.contains("quadrangleId=" + mockId));
        assertTrue(loggedEntry.contains("type=SQUARE"));
        assertTrue(loggedEntry.contains("area=16.00"));
        assertTrue(loggedEntry.contains("perimeter=16.00"));
        assertTrue(loggedEntry.contains("convex=true"));
    }

    @Test
    void testGetQuadrangleParameters_Found() {
        // Arrange
        UUID mockId = UUID.randomUUID();
        String papyrusContent = String.format(
                "quadrangleId=%s v1, type=RECTANGLE, area=20.00, perimeter=18.00, convex=true",
                mockId
        );
        when(mockPapyrus.read()).thenReturn(papyrusContent);

        // Act
        Optional<QuadrangleParameters> result = ancientMathematician.getQuadrangleParameters(mockId);

        // Assert
        assertTrue(result.isPresent());
        QuadrangleParameters params = result.get();
        assertEquals(20.00, params.getArea(), 0.01);
        assertEquals(18.00, params.getPerimeter(), 0.01);
        assertEquals(QuadrangleType.RECTANGLE, params.getType());
        assertTrue(params.isConvex());
    }

    @Test
    void testGetQuadrangleParameters_NotFound() {
        // Arrange
        UUID mockId = UUID.randomUUID();
        when(mockPapyrus.read()).thenReturn("");

        // Act
        Optional<QuadrangleParameters> result = ancientMathematician.getQuadrangleParameters(mockId);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void testUpdateQuadrangleNumericParameters() {
        // Arrange
        UUID mockId = UUID.randomUUID();
        String papyrusContent = String.format(
                "quadrangleId=%s v1, type=RECTANGLE, area=20.00, perimeter=18.00, convex=true",
                mockId
        );
        when(mockPapyrus.read()).thenReturn(papyrusContent);

        // Act
        ancientMathematician.updateQuadrangleNumericParameters(mockId, 2.0, 1.5);

        // Assert
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(mockPapyrus).write(captor.capture());

        String updatedEntry = captor.getValue();
        assertTrue(updatedEntry.contains("quadrangleId=" + mockId));
        assertTrue(updatedEntry.contains("area=40.00")); // 20.00 * 2.0
        assertTrue(updatedEntry.contains("perimeter=27.00")); // 18.00 * 1.5
    }

    @Test
    void testUpdateQuadrangleNumericParameters_NoMatchingId() {
        // Arrange
        UUID mockId = UUID.randomUUID();
        when(mockPapyrus.read()).thenReturn("");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                ancientMathematician.updateQuadrangleNumericParameters(mockId, 2.0, 1.5));
    }
}

