package org.example.state_management;

import java.util.List;
import java.util.Map;

public interface ApplicationStateManager {
    void saveGlobalState();
    void loadGlobalState();
    void register(Statable<?> statable);
}

