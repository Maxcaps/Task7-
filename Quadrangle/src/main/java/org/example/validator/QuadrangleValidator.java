package org.example.validator;

import org.example.model.Point;

import java.util.List;

public class QuadrangleValidator {
    public boolean validate(List<Point> points) {
        if (points == null || points.size() != 4) {
            return false;
        }
        return points.stream().distinct().count() == 4;
    }
}

//points.stream():
//Этот метод преобразует список points в поток данных (Stream).
// Потоки предоставляют мощные инструменты для работы с коллекциями в функциональном стиле.

//.distinct():
//Этот метод фильтрует поток, удаляя из него дубликаты.
//Он оставляет только уникальные элементы на основе метода equals() для объектов Point. Это значит, что две
//точки считаются одинаковыми, если их координаты (или любые другие поля, по которым определяется равенство)
//совпадают.

//.count():
//Этот метод подсчитывает количество оставшихся элементов в потоке после применения всех
// промежуточных операций (в данном случае — после удаления дубликатов методом distinct()).