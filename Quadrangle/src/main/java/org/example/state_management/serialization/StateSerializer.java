package org.example.state_management.serialization;

import org.example.state_management.Statable;
import org.example.state_management.StateName;

import java.io.*;
import java.nio.file.*;
import java.util.List;

public interface StateSerializer {
    void serializeAndSave(Path filePath, List<Statable<?>> statables) throws IOException;
    List<Statable<?>> downloadAndDeserialize(Path filePath, StateName stateName) throws IOException;
    String getFileExtension();
}
