package org.example.specifications;

import org.example.model.Quadrangle;

import java.util.UUID;

public class IdSpecification implements QuadrangleSpecification {
    private final UUID id;

    public IdSpecification(UUID id) {
        this.id = id;
    }

    @Override
    public boolean specified(Quadrangle object) {
        return object.getId() == id;
    }
}
