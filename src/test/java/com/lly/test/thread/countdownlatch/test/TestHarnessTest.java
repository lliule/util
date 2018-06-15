package com.lly.test.thread.countdownlatch.test;

import com.lly.test.thread.countdownlatch.TestHarnes;
import org.junit.Test;

public class TestHarnessTest {

    @Test
    public void test() throws InterruptedException {
        TestHarnes harnes = new TestHarnes();
        long timeTasks = harnes.timeTasks(5, new Runnable() {
            @Override
            public void run() {
                System.out.println("run...");
            }
        });
        System.out.println("total time = " +timeTasks);
    }
}
