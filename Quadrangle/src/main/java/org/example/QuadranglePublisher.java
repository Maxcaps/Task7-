package org.example;

import org.example.model.Point;
import org.example.model.Quadrangle;

import java.util.*;

public class QuadranglePublisher extends Quadrangle implements Publisher<Quadrangle> {
    private final Set<Subscriber<Quadrangle>> subscribers = new HashSet<>();

    public QuadranglePublisher(Point[] points) {
        super(points);
    }

    public QuadranglePublisher(Point[] points, UUID id) {
        super(points, id);
    }

    public void updatePoints(List<Point> points) {
        Point[] array = points.toArray(new Point[0]);
        this.setPoints(array);
        this.notifySubscribers();
    }

    @Override
    public void addSubscriber(Subscriber<Quadrangle> subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void removeSubscriber(Subscriber<Quadrangle> subscriber) {
        subscribers.remove(subscriber);
    }

    @Override
    public void notifySubscribers() {
        Iterator<Subscriber<Quadrangle>> subscriberIterator = subscribers.iterator();
        while (subscriberIterator.hasNext()) {
            Subscriber<Quadrangle> subscriber = subscriberIterator.next();
            subscriber.update(this);
        }
//        for (Subscriber<Quadrangle> subscriber : subscribers) {
//            subscriber.update(this);
//        }
    }

}
