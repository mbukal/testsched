package hr.unizg.fer.hmo.ts.util;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class Event<T> {	
    private Set<Consumer<T>> listeners = new HashSet<Consumer<T>>();

    public void addListener(Consumer<T> listener) {
        listeners.add(listener);
    }

    public void broadcast(T args) {
        listeners.forEach(x -> x.accept(args));
    }
}