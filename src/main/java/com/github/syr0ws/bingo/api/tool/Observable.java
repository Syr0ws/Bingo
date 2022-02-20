package com.github.syr0ws.bingo.api.tool;

import java.util.Collection;

public interface Observable {

    void notifyChange(Change change);

    void addObserver(Observer observer);

    void removeObserver(Observer observer);

    Collection<Observer> getObservers();
}
