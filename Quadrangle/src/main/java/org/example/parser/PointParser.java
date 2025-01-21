package org.example.parser;
import org.example.model.Point;

public class PointParser {
    public Point parsePoint(String input) {
        String[] coordinates = input.split(",");
        double x = Double.parseDouble(coordinates[0]);
        double y = Double.parseDouble(coordinates[1]);
        return new Point(x,y);
    }
}
// split разбивает строку на массив стрингов