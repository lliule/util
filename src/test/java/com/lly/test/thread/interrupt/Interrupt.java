package com.lly.test.thread.interrupt;

public class Interrupt extends Thread{

    @Override
    public void run() {
        System.out.println("interrupt start.");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        System.out.println("interrupt end.");
    }
}
