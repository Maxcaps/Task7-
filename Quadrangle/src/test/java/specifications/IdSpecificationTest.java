package specifications;

import org.example.model.Quadrangle;
import org.example.specifications.IdSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class IdSpecificationTest {

    @Test
    void testSpecifiedIdMatches() {
        // given
        UUID id = UUID.randomUUID();
        Quadrangle quadrangle = mock(Quadrangle.class);
        when(quadrangle.getId()).thenReturn(id);

        IdSpecification specification = new IdSpecification(id);

        // when
        boolean isSpecified = specification.specified(quadrangle);

        // then
        Assertions.assertTrue(isSpecified, "Expected the quadrangle ID to match the specification.");
    }

    @Test
    void testSpecifiedIdDoesNotMatch() {
        // given
        UUID id = UUID.randomUUID();
        Quadrangle quadrangle = mock(Quadrangle.class);
        when(quadrangle.getId()).thenReturn(UUID.randomUUID());

        IdSpecification specification = new IdSpecification(id);

        // when
        boolean isSpecified = specification.specified(quadrangle);

        // then
        Assertions.assertFalse(isSpecified, "Expected the quadrangle ID not to match the specification.");
    }
}

