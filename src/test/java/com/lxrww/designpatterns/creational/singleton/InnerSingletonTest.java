package com.lxrww.designpatterns.creational.singleton;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class InnerSingletonTest {


    @BeforeEach
    void beforeEach(TestInfo testInfo, RepetitionInfo repetitionInfo) {
        int currentRepetition = repetitionInfo.getCurrentRepetition();
        int totalRepetitions = repetitionInfo.getTotalRepetitions();
        String methodName = testInfo.getTestMethod().get().getName();
        log.info("About to execute repetition {} of {} for {}", currentRepetition, totalRepetitions, methodName);
    }

    /**
     * Tests that multiple threads do not result in the same Singleton instance when use non-thread safe singleton.
     */
    @RepeatedTest(10)
    void multiThreadShouldBeDiff() {
        Set<InnerSingleton> instanceSet = new HashSet<>();
        try (var exr = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 1000; i++) {
                exr.submit(() -> {
                    InnerSingleton singleton = InnerSingleton.getInstance();
                    instanceSet.add(singleton);
                });
            }
        }

        log.info("instance number is: {}", instanceSet.size());

        assertEquals(1, instanceSet.size());
    }

    @RepeatedTest(1)
    public void testGetInstance_SameInstance() {
        InnerSingleton firstInstance = InnerSingleton.getInstance();
        InnerSingleton secondInstance = InnerSingleton.getInstance();

        assertSame(firstInstance, secondInstance, "Instances aren't the same. Singleton property violated.");
    }

    @RepeatedTest(1)
    public void testGetInstance_NonNullInstance() {
        InnerSingleton instance = InnerSingleton.getInstance();

        assertNotNull(instance, "Instance is null");
    }
}