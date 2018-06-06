package com.lly.test.thread;

import java.util.concurrent.CountDownLatch;

public class Legendary {

    public void ready(final Runnable task,final Runnable kill) throws InterruptedException {
        final CountDownLatch readyGate = new CountDownLatch(5);
        final CountDownLatch endGate = new CountDownLatch(5);

        readyGame(task, readyGate);
        long startTime = System.nanoTime();
        killEach(kill, endGate);
        readyGate.await();
        System.out.println("game starting");
        endGate.await();
        long endTime = System.nanoTime();
        System.out.println(endTime - startTime);
        System.out.println("winner");

    }

    private void killEach(final Runnable kill,final CountDownLatch endGate) {
        for(int j=0;j<5;j++){
            final int k = j +1;
            Thread aceThread = new Thread() {
                @Override
                public void run() {
                    System.out.println("kill " + k +" person");
                    try {
                        kill.run();
                    } finally {
                        endGate.countDown();
                    }
                }
            };
            aceThread.start();
        }
    }

    private void readyGame(final Runnable task,final CountDownLatch readyGate) {
        for(int i=0; i< 5;i++){
            final int k = i;
            new Thread(){
                @Override
                public void run() {
                    try {
                        System.out.println( (k+1)+ " is ready");
                        task.run();
                    } finally {
                        readyGate.countDown();
                    }
                }
            }.start();
        }
    }
}
