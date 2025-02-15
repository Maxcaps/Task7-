package org.example.observer_pattern;

public interface Subscriber<T> {
    void update(T object);
}
