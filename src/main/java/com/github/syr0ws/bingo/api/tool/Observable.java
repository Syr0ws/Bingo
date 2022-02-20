package com.github.syr0ws.bingo.api.tool;

import java.util.Collection;

public interface Observable {

    void notifyChange(ChangeType changeType);

    void addObserver(Observer observer);

    void removeObserver(Observer observer);

    Collection<Observer> getObservers();
}
