package org.example;

import org.example.calculators.QuadrangleCalculator;
import org.example.model.Quadrangle;
import org.example.model.QuadrangleParameters;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class AutoCADRecorder implements Subscriber<Quadrangle> {
    private final QuadrangleCalculator quadrangleCalculator;
    private final Map<UUID, QuadrangleParameters> subs = new HashMap<>();

    public AutoCADRecorder(QuadrangleCalculator quadrangleCalculator) {
        this.quadrangleCalculator = quadrangleCalculator;
    }

    Optional<QuadrangleParameters> getQuadrangleParameters(UUID id) {
        return Optional.ofNullable(subs.get(id));
    }

    public boolean isQuadranglePublisher(UUID id) {
        return subs.containsKey(id);
    }

    void updateQuadrangleNumericParameters(UUID id, double perimter, double area) {
        if (id == null || perimter == 0 || area == 0) {
            throw new IllegalArgumentException("Wrong argument recevied");
        }
        if (!subs.containsKey(id)) {
            throw new IllegalArgumentException("No quadrangle found with this given ID");
        }
        QuadrangleParameters currentParameters = subs.get(id);
        QuadrangleParameters newParameters = new QuadrangleParameters(
                area, perimter, currentParameters.getType(), currentParameters.isConvex(), quadrangleCalculator);
        subs.put(id, newParameters);
    }


    @Override
    public void update(Quadrangle quadrangle) {
        QuadrangleParameters quadrangleParameters = new QuadrangleParameters(
                quadrangleCalculator.calculateArea(quadrangle),
                quadrangleCalculator.calculatePerimeter(quadrangle),
                quadrangleCalculator.findQuadrangleType(quadrangle),
                quadrangleCalculator.isConvex(quadrangle),
                quadrangleCalculator
        );
        subs.put(quadrangle.getId(), quadrangleParameters);
    }
}
