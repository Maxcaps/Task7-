package specifications;

import org.example.calculators.QuadrangleCalculator;
import org.example.model.Point;
import org.example.model.Quadrangle;
import org.example.specifications.PerimeterSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class PerimeterSpecificationTest {
    private QuadrangleCalculator mockCalculator;

    @BeforeEach
    void setUp() {
        mockCalculator = mock(QuadrangleCalculator.class);
    }

    @Test
    void testSpecifiedWithinRange() {
        // given
        Quadrangle quadrangle = new Quadrangle(new Point[]{
                new Point(0, 0), new Point(4, 0), new Point(4, 3), new Point(0, 3)
        });
        when(mockCalculator.calculatePerimeter(quadrangle)).thenReturn(14.0);

        PerimeterSpecification specification = new PerimeterSpecification(10.0, 15.0, mockCalculator);

        // when
        boolean isSpecified = specification.specified(quadrangle);

        // then
        Assertions.assertTrue(isSpecified, "Expected the quadrangle to satisfy the perimeter range.");
    }

    @Test
    void testSpecifiedOutOfRange() {
        // given
        Quadrangle quadrangle = new Quadrangle(new Point[]{
                new Point(0, 0), new Point(4, 0), new Point(4, 3), new Point(0, 3)
        });
        when(mockCalculator.calculatePerimeter(quadrangle)).thenReturn(20.0);

        PerimeterSpecification specification = new PerimeterSpecification(10.0, 15.0, mockCalculator);

        // when
        boolean isSpecified = specification.specified(quadrangle);

        // then
        Assertions.assertFalse(isSpecified, "Expected the quadrangle not to satisfy the perimeter range.");
    }
}

