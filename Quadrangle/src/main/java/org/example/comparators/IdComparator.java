package org.example.comparators;

import org.example.model.Quadrangle;

import java.util.Comparator;

public class IdComparator implements Comparator<Quadrangle> {
    @Override
    public int compare(Quadrangle o1, Quadrangle o2) {
        return o1.getId().compareTo(o2.getId());
    }
}