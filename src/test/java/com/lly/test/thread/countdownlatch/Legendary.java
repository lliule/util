package com.lly.test.thread.countdownlatch;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Legendary {

    public void ready(final Runnable task) throws InterruptedException {
        final CountDownLatch readyGate = new CountDownLatch(5);

        readyGame(task, readyGate);
        long startTime = System.nanoTime();
        readyGate.await();
        System.out.println("game starting");
        long endTime = System.nanoTime();
        System.out.println(endTime - startTime);
        System.out.println("winner");

    }

    private void readyGame(final Runnable task,final CountDownLatch readyGate) {
        List<String> ls = Arrays.asList("寒冰", "瑞文", "流浪", "龙龟", "猫咪");
        for(int i=0; i< 5;i++){
            final String k = ls.get(i);
            new Thread(){
                @Override
                public void run() {
                    try {
                        task.run();
                        System.out.println( k + " is ready");
                    } finally {
                        readyGate.countDown();
                    }
                }
            }.start();
        }
    }
}
