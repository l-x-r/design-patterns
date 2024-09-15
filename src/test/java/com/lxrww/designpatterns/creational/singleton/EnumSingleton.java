package com.lxrww.designpatterns.creational.singleton;

import java.util.concurrent.atomic.AtomicInteger;

public enum EnumSingleton {
    INSTANCE;

    AtomicInteger value;

    public AtomicInteger getValue() {
        return value;
    }

    public void setValue(AtomicInteger value) {
        this.value = value;
    }
}
