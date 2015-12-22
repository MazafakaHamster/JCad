package com.rebel.cad.util;

import javax.swing.event.ChangeListener;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Slava on 22.12.2015.
 */
public class DoubleProperty implements Serializable {
    private double value;
    private Set<DoubleProperty> binded = new HashSet<>();
    private Set<DoubleChangeListener> listeners = new HashSet<>();

    public DoubleProperty(double value) {
        this.value = value;
    }

    public DoubleProperty() {
    }

    public void bind(DoubleProperty property) {
        binded.add(property);
    }

    public void unbind(DoubleProperty property) {
        binded.remove(property);
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        listeners.forEach(listener -> listener.changed(this.value, value));
        this.value = value;
        binded.forEach(binded -> binded.setValue(value));
    }

    public void addListener(DoubleChangeListener listener) {
        this.listeners.add(listener);
    }

    public void addListeners(Collection<DoubleChangeListener> listeners) {
        this.listeners.addAll(listeners);
    }
}
