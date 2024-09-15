package com.lxrww.designpatterns.creational.singleton;

import java.io.Serial;
import java.io.Serializable;

public class DoubleCheckSingleton implements Serializable {

    private volatile static DoubleCheckSingleton INSTANCE = null;

    public int value;

    public int getValue() {
        return value;
    }

    public void setValue(int i) {
        this.value = i;
    }

    private DoubleCheckSingleton(int value) {
        if (INSTANCE != null) {
            throw new RuntimeException("This is singleton~");
        }

        this.value = value;
    }

    public static DoubleCheckSingleton getInstance(int value) {
        if (INSTANCE == null) {
            synchronized (DoubleCheckSingleton.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DoubleCheckSingleton(value);
                }
            }
        }

        return INSTANCE;
    }

    @Serial
    protected Object readResolve() {
        return INSTANCE;
    }
}
