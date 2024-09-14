package com.lxrww.designpatterns.creational.singleton;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Slf4j
class DcSingletonTest {

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
        Set<Integer> instanceSet = new HashSet<>();
        try (var exr = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 1000; i++) {
                int finalI = i;
                exr.submit(() -> {
                    DoubleCheckSingleton singleton = DoubleCheckSingleton.getInstance(finalI);
                    instanceSet.add(singleton.getValue());
                });
            }
        }

        log.info("instance number is: {}", instanceSet.size());

        assertEquals(1, instanceSet.size());
    }
}