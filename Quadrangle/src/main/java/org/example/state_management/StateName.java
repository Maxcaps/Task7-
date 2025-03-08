package org.example.state_management;

import org.example.model.QuadrangleParameters;
import org.example.observer_pattern.QuadranglePublisher;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public enum StateName {
    AUTOCAD_RECORDER((Class<Map<UUID, List<QuadrangleParameters>>>) (Class<?>) Map.class, "AutoCad Recorder", "Состояние AutoCad"),
    QUADRANGLE_PUBLISHER(QuadranglePublisher.class, "Quadrangle Publisher", "Состояние четырехугольников");

    private final Class<?> className;
    private final String name;
    private final String description;

    StateName(Class<?> className, String name, String description) {
        this.className = className;
        this.name = name;
        this.description = description;
    }

    public Class<?> getClassName() {
        return className;
    }

    public String getReadableName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public static StateName fromFolderName(String name) {
        for (StateName state : values()) {
            if (state.name().equalsIgnoreCase(name)) {
                return state;
            }
        }
        throw new IllegalArgumentException("Unknown state name: " + name);
    }
}
