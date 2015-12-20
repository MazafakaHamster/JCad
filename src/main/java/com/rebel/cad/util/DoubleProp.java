package com.rebel.cad.util;

import javafx.beans.property.DoublePropertyBase;

/**
 * Created by Slava on 16.12.2015.
 */
public class DoubleProp extends DoublePropertyBase {

    private String name;

    public DoubleProp() {
    }

    public DoubleProp(String name) {
        this.name = name;
    }

    @Override
    public void invalidated() {
    }

    @Override
    public Object getBean() {
        return this;
    }

    @Override
    public String getName() {
        return name;
    }
}
