package parser;

import org.example.parser.QuadrangleParser;
import org.example.model.Point;
import org.example.model.Quadrangle;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuadrangleParserTest {

    @Test
    void testParseQuadrangle_ShouldReturnCorrectQuadrangle() {
        // Дано
        List<Point> points = Arrays.asList(
                new Point(0, 0),
                new Point(1, 1),
                new Point(2, 0),
                new Point(1, -1)
        );
        QuadrangleParser parser = new QuadrangleParser();

        // Когда
        Quadrangle quadrangle = parser.parseQuadrangle(points);

        // Тогда
        assertNotNull(quadrangle, "Четырехугольник не должен быть null");
        assertEquals(4, quadrangle.getPoints().length, "Четырехугольник должен содержать 4 точки");
    }
}

