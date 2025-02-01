package org.example.model;

public class Papyrus {
    private final StringBuilder content = new StringBuilder();
    public void write(String entry) {
        content.append(entry);
    }

    public String read() {
        return content.toString();
    }
}
