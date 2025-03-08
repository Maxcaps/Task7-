package org.example;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import javiki.course.state_management.serialization.*;
import org.example.calculators.QuadrangleCalculator;
import org.example.calculators.support.AnglesCalculator;
import org.example.calculators.support.PointsCalculator;
import org.example.calculators.support.VectorCalculator;
import org.example.model.Point;
import org.example.model.QuadrangleParameters;
import org.example.observer_pattern.AutoCadRecorder;
import org.example.observer_pattern.QuadranglePublisher;
import javiki.course.state_management.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();


        JavaType listType = objectMapper.getTypeFactory()
                .constructCollectionType(List.class, QuadrangleParameters.class);
        JavaType mapType = objectMapper.getTypeFactory()
                .constructMapType(Map.class, objectMapper.getTypeFactory().constructType(String.class), listType);
        StateName state = new StateName(mapType, "AUTOCAD_RECORDER", "State for map of QuadrangleParameters");
        StateName.register(state);
        StateName foundState = StateName.fromFolderName("AUTOCAD_RECORDER");
        System.out.println("Найденный StateName: " + foundState.getDescription());

        ApplicationStateManager manager = new ApplicationStateManagerImpl("saved_states", new BinarySerializer());

        PointsCalculator pointsCalculator = new PointsCalculator();
        VectorCalculator vectorCalculator = new VectorCalculator();
        AnglesCalculator anglesCalculator = new AnglesCalculator(vectorCalculator);
        QuadrangleCalculator calculator = new QuadrangleCalculator(pointsCalculator, anglesCalculator);

        AutoCadRecorderPool autoCadRecorderPool = new AutoCadRecorderPool(manager, calculator);
        StateManagerMapper.MAPPERS.put(
                StateName.get("AUTOCAD_RECORDER"), stateValue -> autoCadRecorderPool.createAutoCadRecorder(
                        (Map<String, List<QuadrangleParameters>>) stateValue));

        List<Statable<?>> arrayList = new ArrayList<>();
        for (int i = 0; i < 50; i++){
            AutoCadRecorder autoCadRecorder = autoCadRecorderPool.createAutoCadRecorder();
            generateTestQuadrangles(autoCadRecorder);
            arrayList.add(autoCadRecorder);
        }

        System.out.println("💾 Saving state...");
        manager.saveGlobalState();
        System.out.println("✅ State saved successfully!");
        manager.loadGlobalState();
        System.out.println("✅ State loaded successfully!");
        System.out.println(autoCadRecorderPool.getAutoCadRecorders());

        // Benchmark tests
        Path jsonFile = Paths.get("benchmark/autocad_recorder.json");
        Path xmlFile = Paths.get("benchmark/autocad_recorder.xml");
        Path binFile = Paths.get("benchmark/autocad_recorder.bin");
        Path gzipFile = Paths.get("benchmark/autocad_recorder.gzip");
        Path lz4File = Paths.get("benchmark/autocad_recorder.lz4");

        StateSerializer jsonSerializer = new JsonSerializer();
        StateSerializer xmlSerializer = new XmlSerializer();
        StateSerializer binSerializer = new BinarySerializer();
        StateSerializer binGzipSerializer = new BinaryGZIPSerializer();
        StateSerializer binLz4Serializer = new LZ4BinarySerializer();

        benchmark("JSON", jsonSerializer, jsonFile, arrayList);
        benchmark("XML", xmlSerializer, xmlFile, arrayList);
        benchmark("Binary", binSerializer, binFile, arrayList);
        benchmark("GZIP", binGzipSerializer, gzipFile, arrayList);
        benchmark("LZ4", binLz4Serializer, lz4File, arrayList);
    }

    private static void benchmark(String format, StateSerializer serializer,
                                  Path filePath, List<Statable<?>> array) {
        long start, end;
        start = System.nanoTime();
        try {
            serializer.serializeAndSave(filePath, array);
        } catch (Exception e) {
            e.printStackTrace();
        }
        end = System.nanoTime();
        System.out.println(format + " Serialization Time: " + (end - start) / 1_000_000.0 + " ms");
        start = System.nanoTime();
        try {

                List<Statable<?>> deserializedData = serializer.downloadAndDeserialize(filePath, array.get(0).stateName());
                AutoCadRecorder restoredRecorder = (AutoCadRecorder) deserializedData.get(0);
                System.out.println(format + " Restored subs count: " + restoredRecorder.retrieveState().size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        end = System.nanoTime();
        System.out.println(format + " Deserialization Time: " + (end - start) / 1_000_000.0 + " ms");
        try {
            long fileSize = Files.size(filePath);
            System.out.println(format + " File Size: " + fileSize + " bytes");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void generateTestQuadrangles(AutoCadRecorder recorder) {
        Random random = new Random();
        for (int i = 0; i < 10000; i++) { // Генерируем 1000 случайных квадранглов

            // Создаём случайные точки для квадрангла
            Point[] points = {
                    new Point(random.nextDouble() * 10, random.nextDouble() * 10),
                    new Point(random.nextDouble() * 10, random.nextDouble() * 10),
                    new Point(random.nextDouble() * 10, random.nextDouble() * 10),
                    new Point(random.nextDouble() * 10, random.nextDouble() * 10)
            };
            // Создаём объект квадрангла
            QuadranglePublisher quadrangle = new QuadranglePublisher(points);
            // Получаем фактический ID, который сгенерировался
            String id = quadrangle.getId();
            // Подписываем AutoCadRecorder
            quadrangle.addSubscriber(recorder);
            quadrangle.notifySubscribers(); // Генерируем начальные данные
            // 🔍 Проверяем, добавился ли ID
            if (!recorder.isQuadranglePublisher(id)) {
                System.err.println("ERROR: Quadrangle ID not found in subs: " + id);
                continue;
            }
            // ✅ Теперь можно обновлять параметры
            recorder.updateQuadrangleNumericParameters(
                    id, random.nextDouble() * 100,
                    random.nextDouble() * 50);
        }
    }



}
