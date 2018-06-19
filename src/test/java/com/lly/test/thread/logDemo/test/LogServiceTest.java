package com.lly.test.thread.logDemo.test;

import com.lly.test.thread.logDemo.LogService;

import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LogServiceTest {
    private static ExecutorService service = Executors.newFixedThreadPool(110);

    public static void main(String[] args) throws InterruptedException {
        LogService logService = new LogService(new PrintWriter(System.out));
        logService.start();
        System.out.println("main start");
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            service.execute(()-> {
                try {
                    logService.log("dana" + finalI);
                } catch (InterruptedException e) {
                    System.out.println("22 L is error");
                }
            });
        }

        Thread.sleep(500);
        System.out.println("main stop ");
        logService.stop();
        for (int i = 0; i < 10; i++) {
            logService.log("second" + i);
        }

        if(!service.isShutdown()){
            service.shutdown();
        }
    }
}
