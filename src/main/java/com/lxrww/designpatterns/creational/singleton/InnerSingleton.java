package com.lxrww.designpatterns.creational.singleton;

public class InnerSingleton {

    private InnerSingleton() {
        this.value = 13579;
    }

    public int value;

    public int getValue() {
        return value;
    }

    public static InnerSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * This class leverages the Initialization-on-demand holder idiom to instantiate
     * a singleton instance of the InnerSingleton class.
     * <p>
     * The SingletonHolder is a nested static class that contains a static final
     * instance of InnerSingleton.
     * The instance is created only when the SingletonHolder class is referenced, ensuring lazy initialization.
     * <p>
     * This method ensures that the InnerSingleton instance is created in a
     * thread-safe manner without requiring synchronized blocks.
     */
    private static class SingletonHolder {
        private static final InnerSingleton INSTANCE = new InnerSingleton();
    }
}

