package com.lly.test.thread.cancel.test;

import com.lly.test.thread.cancel.PrimeGenerator;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class PrimeGeneratorTest {
    private static ExecutorService service = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
//        cancel();
        aSecondCancel();
    }

    private static void cancel(){
        PrimeGenerator generator = new PrimeGenerator();
        service.execute(generator);
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        generator.cancel();
        System.out.println(Arrays.toString(generator.get().toArray()));
        if(!service.isShutdown()){
            service.shutdownNow();
        }
    }

    private static void aSecondCancel(){
        PrimeGenerator generator = new PrimeGenerator();
        new Thread(generator).start();
        try {
            MILLISECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            generator.cancel();
        }
        System.out.println(Arrays.toString(generator.get().toArray()));
    }
}
