package com.github.syr0ws.bingo.api.message;

import java.util.Collection;

public interface Observable {

    void sendAll(Message message);

    void addObserver(Observer observer);

    void removeObserver(Observer observer);

    Collection<Observer> getObservers();
}
