package org.example.model;

import java.util.UUID;

public class IdGenerator {
    public static UUID generateId() {
        return UUID.randomUUID();
    }
}
