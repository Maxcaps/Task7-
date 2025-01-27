package org.example;
import org.example.Subscriber;
import org.example.model.Quadrangle;

public abstract class AbstractSubscriber<T> implements Subscriber<T> {
    @Override
    public final void update(T object) {
        preUpdate(object);
        handleUpdate(object);
        postUpdate(object);
    }

    protected void preUpdate(T object) {
        // Default behavior: no pre-processing
    }
    protected abstract void handleUpdate(T object);

    protected void postUpdate(T object) {
        // Default behavior: no post-processing
    }
}
