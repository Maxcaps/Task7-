package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.calculators.QuadrangleCalculator;
import org.example.model.Quadrangle;
import org.example.model.QuadrangleParameters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class AutoCADRecorder extends AbstractSubscriber<Quadrangle> {
    private static final Logger LOGGER = LogManager.getLogger(AutoCADRecorder.class);
    private final QuadrangleCalculator quadrangleCalculator;
    private final Map<UUID, QuadrangleParameters> subs = new HashMap<>();

    public AutoCADRecorder(QuadrangleCalculator quadrangleCalculator) {
        this.quadrangleCalculator = quadrangleCalculator;
    }

    public Optional<QuadrangleParameters> getQuadrangleParameters(UUID id) {
        return Optional.ofNullable(subs.get(id));
    }

    public boolean isQuadranglePublisher(UUID id) {
        return subs.containsKey(id);
    }

    public void updateQuadrangleNumericParameters(UUID id, double perimeter, double area) {
        if (id == null || perimeter == 0 || area == 0) {
            throw new IllegalArgumentException("Wrong argument received");
        }
        if (!subs.containsKey(id)) {
            throw new IllegalArgumentException("No quadrangle found with this given ID");
        }

        // Логирование текущих параметров
        QuadrangleParameters currentParameters = subs.get(id);
        LOGGER.info("Current parameters: id={}, area={}, perimeter={}",
                id, currentParameters.getArea(), currentParameters.getPerimeter());

        // Создание новых параметров
        QuadrangleParameters newParameters = new QuadrangleParameters(
                area, perimeter, currentParameters.getType(), currentParameters.isConvex(), quadrangleCalculator);

        // Логирование новых параметров перед сохранением
        LOGGER.info("Updating parameters: id={}, new area={}, new perimeter={}",
                id, newParameters.getArea(), newParameters.getPerimeter());

        subs.put(id, newParameters);
    }
//


    @Override
    public void handleUpdate(Quadrangle quadrangle) {
        QuadrangleParameters quadrangleParameters = new QuadrangleParameters(
                quadrangleCalculator.calculateArea(quadrangle),
                quadrangleCalculator.calculatePerimeter(quadrangle),
                quadrangleCalculator.findQuadrangleType(quadrangle),
                quadrangleCalculator.isConvex(quadrangle),
                quadrangleCalculator
        );

        subs.put(quadrangle.getId(), quadrangleParameters);

        LOGGER.info("Added to subs: id={}, area={}, perimeter={}, type={}",
                quadrangle.getId(),
                quadrangleParameters.getArea(),
                quadrangleParameters.getPerimeter(),
                quadrangleParameters.getType());
    }

}
