package org.example.specifications;

public interface Specification<T> {
    boolean specified(T object);
}
