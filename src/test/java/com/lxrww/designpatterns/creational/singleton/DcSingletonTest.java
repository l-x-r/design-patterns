package com.lxrww.designpatterns.creational.singleton;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.TestInfo;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
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

        testInfo.getTestMethod().ifPresent(method -> {
            String methodName = method.getName();
            log.info("About to execute repetition {} of {} for {}", currentRepetition, totalRepetitions, methodName);
        });
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

    @RepeatedTest(1)
    void serializationTest() {
        DoubleCheckSingleton singleton = DoubleCheckSingleton.getInstance(0);

        // Serialize
        try {
            FileOutputStream fileOut = new FileOutputStream("out.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(singleton);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        singleton.setValue(2);

        // Deserialize
        DoubleCheckSingleton singleton2 = null;
        try {
            FileInputStream fileIn = new FileInputStream("out.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            singleton2 = (DoubleCheckSingleton) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("singletons.DoubleCheckSingleton class not found");
            c.printStackTrace();
        }

        assertEquals(singleton.getValue(), singleton2.getValue());
    }
}