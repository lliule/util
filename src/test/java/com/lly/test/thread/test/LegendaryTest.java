package com.lly.test.thread.test;

import com.lly.test.thread.Legendary;
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
        }, new Runnable() {
            @Override
            public void run() {
                System.out.println("kill the person");
            }
        });
    }
}
