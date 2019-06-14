package com.lly.test.thread.countdownlatch.test;

import com.lly.test.thread.countdownlatch.Legendary;
import org.junit.Test;

public class LegendaryTest {
    @Test
    public void testReady() throws InterruptedException {
        Legendary legendary = new Legendary();
        legendary.ready(new Runnable() {
            @Override
            public void run() {
                System.out.println("readying ...");
            }
        });
    }
}
