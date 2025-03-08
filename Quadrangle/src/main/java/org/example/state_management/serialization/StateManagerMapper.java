package org.example.state_management.serialization;

import org.example.calculators.QuadrangleCalculator;
import org.example.calculators.support.AnglesCalculator;
import org.example.calculators.support.PointsCalculator;
import org.example.calculators.support.VectorCalculator;
import org.example.model.QuadrangleParameters;
import org.example.observer_pattern.AutoCadRecorder;
import org.example.state_management.Statable;
import org.example.state_management.StateName;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class StateManagerMapper {
    public static final Map<StateName, Function<Object, Statable<?>>> MAPPERS =
            Map.of(
                    StateName.AUTOCAD_RECORDER, new Function<Object, Statable<?>>() {
                        @Override
                        public Statable<?> apply(Object stateValue) {
                            return AutoCadRecorder.createInstanceFromState(
                                    new QuadrangleCalculator(new PointsCalculator(), new AnglesCalculator(new VectorCalculator())),
                                    (Map<String, List<QuadrangleParameters>>) stateValue
                            );
                        }
                    }
            );
}
