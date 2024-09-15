package com.lxrww.designpatterns.creational.singleton;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EnumSingletonTest {

    @Test
    void enum_shouldHasOneInstance() {
        EnumSingleton singleton = EnumSingleton.INSTANCE;

        try (var exr = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 1000; i++) {
                AtomicInteger atomicInteger = new AtomicInteger(i);
                exr.submit(() -> {
                    singleton.setValue(atomicInteger);
                    assertEquals(atomicInteger, singleton.getValue());
                });
            }
        }
    }
}