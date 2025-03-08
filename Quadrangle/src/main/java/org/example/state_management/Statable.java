package org.example.state_management;

public interface Statable<T> {
    void uploadState(T state);   // Загружает новое состояние
    T retrieveState();           // Возвращает текущее состояние (копию)
    Class<T> stateClass();       // Возвращает класс состояния
    StateName stateName();       // Возвращает имя состояния
}

