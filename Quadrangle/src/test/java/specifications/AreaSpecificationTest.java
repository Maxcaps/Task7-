package specifications;

import org.example.calculators.QuadrangleCalculator;
import org.example.model.Point;
import org.example.model.Quadrangle;
import org.example.specifications.AreaSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class AreaSpecificationTest {
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
        when(mockCalculator.calculateArea(quadrangle)).thenReturn(12.0);

        AreaSpecification specification = new AreaSpecification(10.0, 15.0, mockCalculator);

        // when
        boolean isSpecified = specification.specified(quadrangle);

        // then
        Assertions.assertTrue(isSpecified, "Expected the quadrangle to satisfy the area range.");
    }

    @Test
    void testSpecifiedOutsideRange() {
        // given
        Quadrangle quadrangle = new Quadrangle(new Point[]{
                new Point(0, 0), new Point(4, 0), new Point(4, 3), new Point(0, 3)
        });
        when(mockCalculator.calculateArea(quadrangle)).thenReturn(20.0);

        AreaSpecification specification = new AreaSpecification(10.0, 15.0, mockCalculator);

        // when
        boolean isSpecified = specification.specified(quadrangle);

        // then
        Assertions.assertFalse(isSpecified, "Expected the quadrangle not to satisfy the area range.");
    }
}


