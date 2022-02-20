package com.github.syr0ws.bingo.plugin.tool;

import com.github.syr0ws.bingo.api.tool.Change;
import com.github.syr0ws.bingo.api.tool.ChangeType;
import com.github.syr0ws.bingo.api.tool.Observable;
import com.github.syr0ws.bingo.api.tool.Observer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class AbstractObservable implements Observable {

    private final List<Observer> observers = new ArrayList<>();

    @Override
    public void notifyChange(Change change) {
        this.observers.forEach(observer -> observer.onChange(change));
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
