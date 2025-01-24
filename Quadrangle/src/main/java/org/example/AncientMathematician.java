package org.example;

import org.example.calculators.QuadrangleCalculator;
import org.example.model.Papyrus;
import org.example.model.Quadrangle;
import org.example.model.QuadrangleParameters;

public class AncientMathematician implements Subscriber<Quadrangle> {
    private final QuadrangleCalculator quadrangleCalculator;
    private final QuadrangleParameters quadrangleParameters;
    private Papyrus papyrus;

    public AncientMathematician(QuadrangleCalculator quadrangleCalculator, QuadrangleParameters quadrangleParameters, Papyrus papyrus) {
        this.quadrangleCalculator = quadrangleCalculator;
        this.quadrangleParameters = quadrangleParameters;
        this.papyrus = papyrus;
    }

    @Override
    public void update(Quadrangle quadrangle) {
        QuadrangleParameters currentParams = new QuadrangleParameters(
                quadrangleCalculator.calculateArea(quadrangle),
                quadrangleCalculator.calculatePerimeter(quadrangle),
                quadrangleCalculator.findQuadrangleType(quadrangle),
                quadrangleCalculator.isConvex(quadrangle),
                quadrangleCalculator
        );
        papyrus.write(currentParams.toString());
    }
}