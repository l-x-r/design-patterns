package com.lxrww.designpatterns.creational.singleton;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

@SpringBootTest
class SingletonTest {

    @Test
    void reflectionTest() {
        Singleton singleton = Singleton.getInstance();

        assertThrowsExactly(InvocationTargetException.class, () -> {
            Constructor<? extends Singleton> constructor = singleton.getClass().getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        });
    }
}