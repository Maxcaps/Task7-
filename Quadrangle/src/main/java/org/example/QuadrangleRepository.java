package org.example;

import org.example.exceptions.RepositoryDataException;
import org.example.model.Quadrangle;
import org.example.specifications.Specification;

import java.util.Comparator;
import java.util.List;

public interface QuadrangleRepository {
    void add(Quadrangle quadrangle) throws RepositoryDataException;

    void remove(Quadrangle quadrangle) throws RepositoryDataException;

    void update(Quadrangle quadrangle) throws RepositoryDataException;

    List<Quadrangle> query(Specification<Quadrangle> specification) throws RepositoryDataException;

    List<Quadrangle> query(Specification<Quadrangle> specification, Comparator<Quadrangle> comparator) throws RepositoryDataException;
}