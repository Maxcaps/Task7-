package org.example.specifications;

import org.example.model.Quadrangle;

public class IdSpecification implements QuadrangleSpecification {
    private final String id;

    public IdSpecification(String id) {
        this.id = id;
    }

    @Override
    public boolean specified(Quadrangle object) {
        return object.getId() == id;
    }
}
