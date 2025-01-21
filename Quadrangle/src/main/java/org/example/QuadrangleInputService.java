package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.Point;
import org.example.model.Quadrangle;
import org.example.parser.PointParser;
import org.example.parser.QuadrangleParser;
import org.example.reader.ConsoleReader;
import org.example.reader.FileReader;
import org.example.reader.Reader;
import org.example.validator.PointValidator;
import org.example.validator.QuadrangleValidator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QuadrangleInputService {
    private final Reader fileReader = new FileReader();
    private final Reader consoleReader = new ConsoleReader();
    private final PointValidator pointValidator = new PointValidator();
    private final QuadrangleValidator quadrangleValidator = new QuadrangleValidator();
    private final PointParser pointParser = new PointParser();
    private final QuadrangleParser quadrangleParser = new QuadrangleParser();
    private static final Logger LOGGER = LogManager.getLogger(QuadrangleInputService.class);

    public List<Quadrangle> processInput(boolean useFile) throws IOException {
        Reader reader = useFile ? fileReader : consoleReader;
        List<String> lines = reader.read();
        List<Point> points = new ArrayList<>();
        List<Quadrangle> quadrangles = new ArrayList<>();
        for (String line : lines) {
            if (pointValidator.validate(line)) {
                points.add(pointParser.parsePoint(line));
            } else {
                LOGGER.info("Incorrect input{}", line);
            }

            if (points.size() == 4) {
                if (quadrangleValidator.validate(points)) {
                    Quadrangle quadrangle = quadrangleParser.parseQuadrangle(points);
                    quadrangles.add(quadrangle);
                } else {
                    LOGGER.info("It is impossible to create a quadrilateral from points: {}", points);
                }
                points.clear();
            }
        }
        return quadrangles;
    }
}
//