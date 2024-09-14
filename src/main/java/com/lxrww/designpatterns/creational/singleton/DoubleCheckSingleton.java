package com.lxrww.designpatterns.creational.singleton;

public class DoubleCheckSingleton {

    private volatile static DoubleCheckSingleton instance;

    public int value;

    public int getValue() {
        return value;
    }

    private DoubleCheckSingleton(int value) {
        this.value = value;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static DoubleCheckSingleton getInstance(int value) {
        if (instance == null) {
            synchronized (DoubleCheckSingleton.class) {
                if (instance == null) {
                    instance = new DoubleCheckSingleton(value);
                }
            }
        }

        return instance;
    }
}
