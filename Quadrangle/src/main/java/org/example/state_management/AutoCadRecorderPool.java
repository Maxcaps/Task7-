package org.example.state_management;

import org.example.calculators.QuadrangleCalculator;
import org.example.observer_pattern.AutoCadRecorder;

public class AutoCadRecorderPool {
    private final ApplicationStateManager applicationStateManager;
    private final QuadrangleCalculator calculator;

    public AutoCadRecorderPool(ApplicationStateManager stateManager, QuadrangleCalculator calculator) {
        this.applicationStateManager = stateManager;
        this.calculator = calculator;
    }

    public AutoCadRecorder createAutoCadRecorder() {
        AutoCadRecorder newRecorder = AutoCadRecorder.createInstance(calculator);
        applicationStateManager.register(newRecorder);
        return newRecorder;
    }
}

