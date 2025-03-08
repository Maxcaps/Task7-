package org.example.model;

import java.util.UUID;

public class IdGenerator {
    public static String generateId() {
        String uuid = UUID.randomUUID().toString();
        return "uuid_" + uuid;
    }
}
