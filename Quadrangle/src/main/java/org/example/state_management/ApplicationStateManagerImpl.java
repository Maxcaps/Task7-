package org.example.state_management;

import org.example.state_management.serialization.StateSerializer;

import java.io.IOException;
import java.nio.file.*;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ApplicationStateManagerImpl implements ApplicationStateManager {

    private final String stateFolderPath;
    private final StateSerializer serializer;
    private final ApplicationStateHolder stateHolder;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            .withZone(ZoneId.of("UTC"));
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH-mm-ss")
            .withZone(ZoneId.of("UTC"));

    public ApplicationStateManagerImpl(String stateFolderPath, StateSerializer serializer) {
        this.stateFolderPath = stateFolderPath;
        this.serializer = serializer;
        this.stateHolder = ApplicationStateHolder.getInstance();
    }

    @Override
    public void register(Statable<?> statable) {
        stateHolder.register(statable);
    }

    private void save(Map<StateName, List<Statable<?>>> stateToSave) {
        for (Map.Entry<StateName, List<Statable<?>>> entry : stateToSave.entrySet()) {
            StateName stateName = entry.getKey();
            List<Statable<?>> statables = entry.getValue();
            if (statables.isEmpty()) {
                continue;
            }
            String dateFolder = DATE_FORMATTER.format(Instant.now());
            String timeStamp = TIME_FORMATTER.format(Instant.now());

            Path dirPath = Paths.get(stateFolderPath, stateName.toString(), dateFolder);
            Path filePath = dirPath.resolve(timeStamp + serializer.getFileExtension());
            try {
                Files.createDirectories(dirPath);
                serializer.serializeAndSave(filePath, statables);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Map<StateName, List<Statable<?>>> download() {
        Map<StateName, List<Statable<?>>> loadedStates = new HashMap<>();
        try (Stream<Path> paths = Files.list(Paths.get(stateFolderPath))) {
            List<Path> stateDirectories = paths.filter(Files::isDirectory).collect(Collectors.toList());
            for (Path stateDir : stateDirectories) {
                String folderName = stateDir.getFileName().toString();
                StateName stateName;
                try {
                    stateName = StateName.fromFolderName(folderName);
                } catch (IllegalArgumentException e) {
                    continue; // Пропускаем неизвестные папки
                }
                Path latestFile = getLatestStateFile(stateDir);
                if (latestFile != null) {
                    Class<?> stateClass = stateName.getClassName();
                    try {
                        List<Statable<?>> stateObjects = serializer.downloadAndDeserialize(latestFile, stateName);
                        loadedStates.put(stateName, stateObjects);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loadedStates;
    }

    private Path getLatestStateFile(Path stateDir) throws IOException {
        try (Stream<Path> dateDirs = Files.list(stateDir)) {
            Optional<Path> latestDateDir = dateDirs
                    .filter(Files::isDirectory)
                    .max(Comparator.comparing(Path::getFileName));
            if (latestDateDir.isPresent()) {
                try (Stream<Path> files = Files.list(latestDateDir.get())) {
                    return files
                            .filter(Files::isRegularFile)
                            .max(Comparator.comparing(Path::getFileName))
                            .orElse(null);
                }
            }
        }
        return null;
    }
    @Override
    public void loadGlobalState() {
        stateHolder.clear();
        Map<StateName, List<Statable<?>>> loadedStates = download();
        List<Statable<?>> allStates = new ArrayList<>();
        for (List<Statable<?>> statables : loadedStates.values()) {
            allStates.addAll(statables);
        }
        stateHolder.setStateList(allStates);
        System.out.println(" Загружено " + allStates.size() + " объектов в ApplicationStateHolder");
    }

    @Override
    public void saveGlobalState() {
        Map<StateName, List<Statable<?>>> stateToSave = new HashMap<>();
        for (Statable<?> statable : stateHolder.getStateList()) {
            StateName stateName = statable.stateName();
            stateToSave.computeIfAbsent(stateName, k -> new ArrayList<>()).add(statable);
        }
        save(stateToSave);
        System.out.println(" Сохранено " + stateHolder.getStateList().size() + " объектов.");
    }
}
