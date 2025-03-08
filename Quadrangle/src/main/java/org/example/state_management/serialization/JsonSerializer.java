package org.example.state_management.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.state_management.Statable;
import org.example.state_management.StateName;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class JsonSerializer extends AbstractStateSerializer {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public JsonSerializer() {
        super(".json");
    }

    @Override
    public String serialize(List<Statable<?>> statables) throws IOException {
        List<Object> stateObjects = new ArrayList<>();
        for (Statable<?> statable : statables) {
            stateObjects.add(statable.retrieveState());
        }
        return objectMapper.writeValueAsString(stateObjects);
    }

    @Override
    public List<Statable<?>> downloadAndDeserialize(Path filePath, StateName stateName) throws IOException {
        List<?> stateResult = objectMapper.readValue(
                Files.newBufferedReader(filePath),
                objectMapper.getTypeFactory().constructCollectionType(List.class, stateName.getClassName())
        );
        Function<Object, Statable<?>> stateMapper = StateManagerMapper.MAPPERS.get(stateName);

        return stateResult.stream()
                .map(stateMapper)
                .collect(Collectors.toList());
    }
}
