package com.lly.test.thread.extend;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ExtendTest {

    public static void main(String[] args) {
//        testThreadPoolExecutorExtend();
        testTimingThreadExecutor();
    }

    private static void testThreadPoolExecutorExtend() {
        ThreadPoolExecutorExtend extend = new ThreadPoolExecutorExtend(3,
                15, 10, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>());

        for(int i = 0;i < 10 ;i++){
            final int temp = i;
            extend.execute(()->{
                System.out.println("running["+ temp +"]");
            });
        }

        if(!extend.isShutdown()){
            extend.shutdown();
        }
    }

    private static void testTimingThreadExecutor(){
        TimingExecutorPool pool = new TimingExecutorPool(10, 15, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        ArrayList<Future> futures = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            Future<?> submit = pool.submit(() -> {
                System.out.println(Thread.currentThread().getName() + " running");
                return finalI;
            });
            futures.add(submit);
        }
        futures.forEach((future)->{
            try {
                System.out.println(Thread.currentThread().getName() + "  :  " +future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
        if(!pool.isShutdown()){
            pool.shutdown();
        }

    }

}
