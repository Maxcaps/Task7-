package org.example.parser;

import org.example.model.Point;
import org.example.model.Quadrangle;

import java.util.List;

public class QuadrangleParser {
    public Quadrangle parseQuadrangle(List<Point> points) {
        // Создаём массив нужного размера
        Point[] pointsArray = new Point[points.size()];

        // Копируем элементы списка в массив
        for (int i = 0; i < points.size(); i++) {
            pointsArray[i] = points.get(i);
        }

        // Создаём объект Quadrangle из массива
        return new Quadrangle(pointsArray);
    }
}

// для зумерков
// public Quadrangle parseQuadrangle(List<Point> points) {
//        return new Quadrangle(points.toArray(new Point[0]));
//    }

// Метод toArray(T[] a) преобразует список в массив. Он делает следующее:
//Если переданный массив (new Point[0]) достаточно большой, элементы из списка копируются в этот массив.
//Если переданный массив слишком маленький, создаётся новый массив нужного размера и типа.
// В этом случае Java использует тип массива, указанный в new Point[0]
