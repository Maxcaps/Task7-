package parser;

import org.example.parser.PointParser;
import org.example.model.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointParserTest {

    @Test
    void testParsePoint_ShouldReturnCorrectPoint() {
        // Дано
        String input = "3.0,4.0";
        PointParser parser = new PointParser();

        // Когда
        Point point = parser.parsePoint(input);

        // Тогда
        assertEquals(3.0, point.getX(), 1e-6, "X-координата должна быть 3.0");
        assertEquals(4.0, point.getY(), 1e-6, "Y-координата должна быть 4.0");
    }

    @Test
    void testParsePoint_ShouldThrowExceptionForInvalidInput() {
        // Дано
        String input = "invalid,input";
        PointParser parser = new PointParser();

        // Когда/Тогда
        assertThrows(NumberFormatException.class, () -> parser.parsePoint(input), "Ожидается исключение для некорректного ввода");
    }
}

