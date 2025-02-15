package org.example.observer_pattern;

public interface Publisher<T> {
    void addSubscriber(Subscriber<T> subscriber);

    void removeSubscriber(Subscriber<T> subscriber);

    void notifySubscribers();
}
