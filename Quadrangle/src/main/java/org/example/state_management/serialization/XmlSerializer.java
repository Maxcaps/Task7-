package org.example.state_management.serialization;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.example.state_management.Statable;
import org.example.state_management.StateName;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class XmlSerializer extends AbstractStateSerializer {
    private final XmlMapper mapper = new XmlMapper();

    public XmlSerializer() {
        super(".xml");
    }

    protected String serialize(List<Statable<?>> statables) throws IOException {
        List<Object> stateObjects = new ArrayList<>();
        for (Statable<?> statable : statables) {
            stateObjects.add(statable.retrieveState());
        }
        return mapper.writeValueAsString(stateObjects);
    }

    public List<Statable<?>> downloadAndDeserialize(Path filePath, StateName stateName) throws IOException {
        List<?> stateResult = mapper.readValue(
                Files.newBufferedReader(filePath),
                mapper.getTypeFactory().constructCollectionType(List.class, stateName.getClassName())
        );
        Function<Object, Statable<?>> stateMapper = StateManagerMapper.MAPPERS.get(stateName);

        return stateResult.stream()
                .map(stateMapper)
                .collect(Collectors.toList());
    }
}

