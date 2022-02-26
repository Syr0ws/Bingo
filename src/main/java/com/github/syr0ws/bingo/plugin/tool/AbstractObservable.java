package com.github.syr0ws.bingo.plugin.tool;

import com.github.syr0ws.bingo.api.message.Message;
import com.github.syr0ws.bingo.api.message.Observable;
import com.github.syr0ws.bingo.api.message.Observer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class AbstractObservable implements Observable {

    private final List<Observer> observers = new ArrayList<>();

    @Override
    public void sendAll(Message message) {
        // Using a copy to avoid ConcurrentModificationException because observers
        // can be removed because of a message.
        new ArrayList<>(this.observers).forEach(observer -> observer.onMessageReceiving(message));
    }

    @Override
    public void addObserver(Observer observer) {

        if(observer == null)
            throw new IllegalArgumentException("Observer cannot be null.");

        if(!this.observers.contains(observer))
            this.observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {

        if(observer == null)
            throw new IllegalArgumentException("Observer cannot be null.");

        this.observers.remove(observer);
    }

    @Override
    public Collection<Observer> getObservers() {
        return Collections.unmodifiableCollection(this.observers);
    }
}
