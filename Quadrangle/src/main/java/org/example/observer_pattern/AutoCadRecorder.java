package org.example.observer_pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.QuadrangleType;
import org.example.calculators.QuadrangleCalculator;
import org.example.model.Quadrangle;
import org.example.model.QuadrangleParameters;
import org.example.state_management.Statable;
import org.example.state_management.StateName;

import java.util.*;

public class AutoCadRecorder implements Subscriber<Quadrangle>, Statable<Map<String, List<QuadrangleParameters>>> {

    private static final Logger LOGGER = LogManager.getLogger(AutoCadRecorder.class);
    private final QuadrangleCalculator quadrangleCalculator;
    private final Map<String, List<QuadrangleParameters>> subs;

    private AutoCadRecorder(QuadrangleCalculator quadrangleCalculator,
                            Map<String, List<QuadrangleParameters>> subs) {
        this.quadrangleCalculator = quadrangleCalculator;
        this.subs = subs;
    }

    public static AutoCadRecorder createInstance(QuadrangleCalculator quadrangleCalculator) {
        return new AutoCadRecorder(quadrangleCalculator, new HashMap<>());
    }

    public static AutoCadRecorder createInstanceFromState(QuadrangleCalculator quadrangleCalculator,
                                                          Map<String, List<QuadrangleParameters>> subs) {
        return new AutoCadRecorder(quadrangleCalculator, subs);
    }

    public Optional<List<QuadrangleParameters>> getQuadrangleParameters(String id) {
        return Optional.ofNullable(subs.get(id));
    }

    public boolean isQuadranglePublisher(String id) {
        return subs.containsKey(id);
    }

    public void updateQuadrangleNumericParameters(String id, double perimeter, double area) {
        if (id == null || perimeter == 0 || area == 0) {
            throw new IllegalArgumentException("Wrong argument received");
        }
        if (!subs.containsKey(id)) {
            throw new IllegalArgumentException("No quadrangle found with this given ID");
        }
        List<QuadrangleParameters> currentParameters = subs.get(id);
        for (int i = 0; i < currentParameters.size(); i++) {
            QuadrangleParameters oldParameters = currentParameters.get(i);
            // Логирование текущих параметров
            LOGGER.info("Current parameters: id={}, area={}, perimeter={}",
                    id, oldParameters.getArea(), oldParameters.getPerimeter());
            // Создание обновленного объекта
            QuadrangleParameters newParameters = new QuadrangleParameters(
                    area, perimeter, oldParameters.getType(), oldParameters.isConvex()
            );
            // Логирование обновленных параметров
            LOGGER.info("Updating parameters: id={}, new area={}, new perimeter={}",
                    id, area, perimeter);
            // Заменяем старый объект новым
            currentParameters.set(i, newParameters);
        }
        // Обновляем лист в мапе (если он уже есть, этого делать не нужно)
        subs.put(id, currentParameters);
    }

    @Override
    public void update(Quadrangle quadrangle) {
        String id = quadrangle.getId();
        if (!subs.containsKey(id)) {
            LOGGER.info("New quadrangle detected, adding to subs: id={}", id);
            subs.put(id, new ArrayList<>()); // Создаем пустой список параметров
        }
        List<QuadrangleParameters> currentParameters = subs.get(id);
        double newArea = quadrangleCalculator.calculateArea(quadrangle);
        double newPerimeter = quadrangleCalculator.calculatePerimeter(quadrangle);
        QuadrangleType newType = quadrangleCalculator.findQuadrangleType(quadrangle);
        boolean newIsConvex = quadrangleCalculator.isConvex(quadrangle);
        LOGGER.info("Updating parameters: id={}, area={}, perimeter={}, type={}, isConvex={}",
                id, newArea, newPerimeter, newType, newIsConvex);

        QuadrangleParameters newParameters = new QuadrangleParameters(
                newArea, newPerimeter, newType, newIsConvex
        );
        if (!currentParameters.isEmpty()) {
            // Обновляем последний элемент, если он уже существует
            currentParameters.set(currentParameters.size() - 1, newParameters);
        } else {
            // Если данных еще нет, просто добавляем
            currentParameters.add(newParameters);
        }
        // Обновляем `subs` с новым списком
        subs.put(id, currentParameters);
    }




    //   STATABLE INTERFACE
        @Override
        public void uploadState (Map<String,List<QuadrangleParameters>> state){
            if (state == null) {
                LOGGER.warn("Received null state, ignoring upload.");
                return;
            }
            subs.clear();
            Iterator<Map.Entry<String, List<QuadrangleParameters>>> iterator = state.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, List<QuadrangleParameters>> entry = iterator.next();
                subs.put(entry.getKey(), new ArrayList<>(entry.getValue())); // Глубокая копия списка
            }
            LOGGER.info("State upload successfully");
        }

        @Override
        public Map<String, List<QuadrangleParameters>> retrieveState () {
            Map<String,List<QuadrangleParameters>> copy = new HashMap<>();
            Iterator<Map.Entry<String,List<QuadrangleParameters>>> iterator = subs.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String,List<QuadrangleParameters>> entry = iterator.next();
                copy.put(entry.getKey(), new ArrayList<>(entry.getValue()));
            }
            return copy;
        }

        @Override
        public Class<Map<String, List<QuadrangleParameters>>> stateClass () {
            return (Class<Map<String, List<QuadrangleParameters>>>) (Class<?>) Map.class;
        }

        @Override
        public StateName stateName () {
            return StateName.AUTOCAD_RECORDER;
        }

    }
