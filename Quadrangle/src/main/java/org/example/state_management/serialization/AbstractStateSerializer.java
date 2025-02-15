package org.example.state_management.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.state_management.Statable;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public abstract class AbstractStateSerializer implements StateSerializer {
    protected final String fileExtension;

    protected AbstractStateSerializer(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    @Override
    public final String getFileExtension() { // Делаем final, чтобы потомки не переопределяли
        return fileExtension;
    }

    protected abstract String serialize(List<Statable<?>> statables) throws IOException;

    @Override
    public void serializeAndSave(Path filePath, List<Statable<?>> statables) throws IOException {
        Files.createDirectories(filePath.getParent()); // Создание директорий, если их нет

        String serializedValue = serialize(statables);

        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            writer.write(serializedValue);
        }
    }

}
