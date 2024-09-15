package com.lxrww.designpatterns.creational.singleton;

import java.io.Serial;
import java.io.Serializable;

public class Singleton implements Serializable {

    public static volatile Singleton INSTANCE = null;

    private Singleton() {
        if (INSTANCE != null) {
            throw new RuntimeException("This is singleton~");
        }
    }

    public static Singleton getInstance() {
        if (INSTANCE == null) {
            synchronized (Singleton.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Singleton();
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
