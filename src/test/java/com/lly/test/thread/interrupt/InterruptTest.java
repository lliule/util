package com.lly.test.thread.interrupt;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class InterruptTest {
    public static void main(String[] args) {
        try {
            testInterrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void testInterrupt() throws InterruptedException {
        Interrupt interrupt = new Interrupt();
        interrupt.start();
//        interrupt.interrupt();
        MILLISECONDS.sleep(100);
        Thread.currentThread().interrupt();
        if(Thread.currentThread().isInterrupted()){
//            Thread.interrupted();
            System.out.println("main continue.");
        }
        System.out.println("main");
        if (interrupt.isInterrupted()){
            System.out.println("interrupt continue");
        }
    }

}
