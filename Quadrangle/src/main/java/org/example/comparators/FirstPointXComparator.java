package org.example.comparators;

import org.example.model.Quadrangle;

import java.util.Comparator;

public class FirstPointXComparator implements Comparator<Quadrangle> {
    @Override
    public int compare(Quadrangle o1, Quadrangle o2) {
        return Double.compare(o1.getPoints()[0].getX(), o2.getPoints()[0].getX());
    }
}
