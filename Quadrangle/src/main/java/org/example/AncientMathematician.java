package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.calculators.QuadrangleCalculator;
import org.example.model.Papyrus;
import org.example.model.Quadrangle;
import org.example.model.QuadrangleParameters;

import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AncientMathematician extends AbstractSubscriber<Quadrangle> {
    private final QuadrangleCalculator quadrangleCalculator;
    private final Papyrus papyrus;
    private final Logger LOGGER = LogManager.getLogger(AncientMathematician.class);

    public AncientMathematician(QuadrangleCalculator quadrangleCalculator, Papyrus papyrus) {
        this.quadrangleCalculator = quadrangleCalculator;
        this.papyrus = papyrus;
    }
//
    private void writeToPapyrus(UUID id, QuadrangleParameters quadrangleParameters, int version) {
        String entry = String.format(
                java.util.Locale.ENGLISH, // Указываем локаль
                "quadrangleId=%s v%d, type=%s, area=%.2f, perimeter=%.2f, convex=%b",
                id, version, quadrangleParameters.getType(),
                quadrangleParameters.getArea(),
                quadrangleParameters.getPerimeter(),
                quadrangleParameters.isConvex()
        );
        papyrus.write(entry);
        LOGGER.info("New string added to Papyrus: {}", entry);
    }


    public Optional<QuadrangleParameters> getQuadrangleParameters(UUID id) {
        String papyrusContent = papyrus.read();
        Pattern pattern = Pattern.compile("quadrangleId=" + id + " v\\d+, type=(.+?), area=(\\d+\\.\\d+),"
                + " perimeter=(\\d+\\.\\d+), convex=(true|false)");
        Matcher matcher = pattern.matcher(papyrusContent);
        if (matcher.find()) {
            QuadrangleType quadrangleType = QuadrangleType.valueOf(matcher.group(1));
            double area = Double.parseDouble(matcher.group(2));
            double perimeter = Double.parseDouble(matcher.group(3));
            boolean isConvex = Boolean.parseBoolean(matcher.group(4));
            return Optional.of(new QuadrangleParameters(area, perimeter, quadrangleType,
                    isConvex, quadrangleCalculator));
        }
        return Optional.empty();
    }

    public boolean isQuadranglePublisher(UUID id) {
        return papyrus.read().contains("quadrangleId=" + id);
    }

    public void updateQuadrangleNumericParameters(UUID id, double areaMultiplyValue, double perimeterMultiplyValue) {
        String papyrusContent = papyrus.read();
        Pattern pattern = Pattern.compile("quadrangleId=" + id + " v(\\d+), type=(.+?), area=(\\d+\\.\\d+),"
                + " perimeter=(\\d+\\.\\d+), convex=(true|false)");
        Matcher matcher = pattern.matcher(papyrusContent);
        if (matcher.find()) {
            int version = Integer.parseInt(matcher.group(1)) + 1;
            QuadrangleType quadrangleType = QuadrangleType.valueOf(matcher.group(2));
            double currentArea = Double.parseDouble(matcher.group(3)); // Текущая площадь
            double currentPerimeter = Double.parseDouble(matcher.group(4)); // Текущий периметр
            boolean isConvex = Boolean.parseBoolean(matcher.group(5)); // Выпуклость
            double updatedArea = currentArea * areaMultiplyValue;
            double updatedPerimeter = currentPerimeter * perimeterMultiplyValue;
            QuadrangleParameters params = new QuadrangleParameters(updatedArea, updatedPerimeter, quadrangleType,
                    isConvex, quadrangleCalculator);
            writeToPapyrus(id, params, version);
        } else {
            throw new IllegalArgumentException("No quadrangle found with this given ID in papyrus");
        }
    }

    @Override
    public void handleUpdate(Quadrangle quadrangle) {
        String papyrusContent = papyrus.read();
        if (papyrusContent == null || papyrusContent.isEmpty()) {
            papyrusContent = ""; // Если содержимое пустое или null, используем пустую строку
        }
        Pattern pattern = Pattern.compile("quadrangleId=" + quadrangle.getId() + " v(\\d+),");
        Matcher matcher = pattern.matcher(papyrusContent);
        int currentVersion = 0;
        while (matcher.find()) {
            currentVersion = Math.max(currentVersion, Integer.parseInt(matcher.group(1)));
        }
        int newVersion = currentVersion + 1;
        QuadrangleParameters quadrangleParameters = new QuadrangleParameters(
                quadrangleCalculator.calculateArea(quadrangle),
                quadrangleCalculator.calculatePerimeter(quadrangle),
                quadrangleCalculator.findQuadrangleType(quadrangle),
                quadrangleCalculator.isConvex(quadrangle),
                quadrangleCalculator
        );
        writeToPapyrus(quadrangle.getId(), quadrangleParameters, newVersion);
    }

}
