package org.example;

import org.example.exceptions.RepositoryDataException;
import org.example.model.Quadrangle;
import org.example.specifications.Specification;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class QuadrangleRepositoryImpl implements QuadrangleRepository {

    private final List<Quadrangle> quadrangleList = new ArrayList<>();

    @Override
    public void add(Quadrangle quadrangle) throws RepositoryDataException {
        if (quadrangleList.contains(quadrangle)) {
            throw new RepositoryDataException("Quadrangle already exists in the repository.");
        }
        quadrangleList.add(quadrangle);
    }

    @Override
    public void remove(Quadrangle quadrangle) throws RepositoryDataException {
        if (!quadrangleList.contains(quadrangle)) {
            throw new RepositoryDataException("Quadrangle does not exist in the repository.");
        }
        quadrangleList.remove(quadrangle);
    }

    @Override
    public void update(Quadrangle quadrangle) throws RepositoryDataException {
        int index = -1;
        for (int i = 0; i < quadrangleList.size(); i++) {
            if (quadrangleList.get(i).getId() == quadrangle.getId()) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            throw new RepositoryDataException("Quadrangle with the specified ID does not exist.");
        }
        quadrangleList.set(index, quadrangle);
    }

    @Override
    public List<Quadrangle> query(Specification<Quadrangle> specification) {
        List<Quadrangle> result = new ArrayList<>();
        for (Quadrangle quadrangle : quadrangleList) {
            if (specification.specified(quadrangle)) {
                result.add(quadrangle);
            }
        }
        return result;
    }

    @Override
    public List<Quadrangle> query(Specification<Quadrangle> specification, Comparator<Quadrangle> comparator) {
        List<Quadrangle> result = this.query(specification);
        result.sort(comparator);
        return result;
    }

}
