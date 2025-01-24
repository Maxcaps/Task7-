package org.example.model;

public class Papyrus {
    private String content = "";

    public void write(String entry) {
        content += entry;
    }

    public String read() {
        return content;
    }
}
