package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.calculators.QuadrangleCalculator;
import org.example.model.Quadrangle;
import org.example.model.QuadrangleParameters;

import java.util.*;

public class AutoCADRecorder extends AbstractSubscriber<Quadrangle> {
    private static final Logger LOGGER = LogManager.getLogger(AutoCADRecorder.class);
    private final QuadrangleCalculator quadrangleCalculator;
    private final Map<UUID, QuadrangleParameters> subs = new HashMap<>();
    private final Map<UUID, List<QuadrangleParameters>> reserveCopyOfSubs = new HashMap<>();

    public AutoCADRecorder(QuadrangleCalculator quadrangleCalculator) {
        this.quadrangleCalculator = quadrangleCalculator;
    }

    public Optional<QuadrangleParameters> getQuadrangleParameters(UUID id) {
        return Optional.ofNullable(subs.get(id));
    }

    public boolean isQuadranglePublisher(UUID id) {
        return subs.containsKey(id);
    }

    public void updateQuadrangleNumericParameters(UUID id, double perimeter, double area) {
        if (id == null || perimeter == 0 || area == 0) {
            throw new IllegalArgumentException( "Wrong argument received");
        }
        if (!subs.containsKey(id)) {
            throw new IllegalArgumentException("No quadrangle found with this given ID");
        }

        // Логирование текущих параметров
        QuadrangleParameters currentParameters = subs.get(id);
        LOGGER.info("Current parameters: id={}, area={}, perimeter={}",
                id, currentParameters.getArea(), currentParameters.getPerimeter());

        // Создание новых параметров
        QuadrangleParameters newParameters = new QuadrangleParameters(
                area, perimeter, currentParameters.getType(), currentParameters.isConvex());

        // Логирование новых параметров перед сохранением
        LOGGER.info("Updating parameters: id={}, new area={}, new perimeter={}",
                id, newParameters.getArea(), newParameters.getPerimeter());

        subs.put(id, newParameters);
    }



    @Override
    public void handleUpdate(Quadrangle quadrangle) {
        UUID id = quadrangle.getId();
        if (isQuadranglePublisher(id)){
            QuadrangleParameters oldParameters = subs.get(id);
            List<QuadrangleParameters> backupList = reserveCopyOfSubs.computeIfAbsent(id, k -> new ArrayList<>());
            int MAX_BACKUP_SIZE = 5;
            if (backupList.size() >= MAX_BACKUP_SIZE) {
                backupList.remove(0); // Удаляем первый (самый старый) элемент
            }
            backupList.add(oldParameters);
        }
        //            computeIfAbsent() проверяет, есть ли id в reserveCopyOfSubs.
//            Если да → добавляет объект в список.
//            Если нет → создает new ArrayList<>(), а затем добавляет объект.
//--------------------------------------------------------------------------------------------------------------------------
        QuadrangleParameters quadrangleParameters = new QuadrangleParameters(quadrangle,quadrangleCalculator);
        subs.put(quadrangle.getId(), quadrangleParameters);
        LOGGER.info("Added to subs: id={}, area={}, perimeter={}, type={}",
                quadrangle.getId(),
                quadrangleParameters.getArea(),
                quadrangleParameters.getPerimeter(),
                quadrangleParameters.getType());
    }

}
