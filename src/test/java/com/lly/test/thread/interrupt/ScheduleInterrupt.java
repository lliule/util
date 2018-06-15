package com.lly.test.thread.interrupt;

import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduleInterrupt {
    private ScheduledExecutorService service = Executors.newScheduledThreadPool(1);

    @Test
    public void test1() {
        new ScheduleInterrupt().timedRun(()-> {
            System.out.println("main run");
            int i = 1/0;
        });
    }
    public void timedRun(Runnable r){
        class ReThrowTask extends Thread{
            volatile Exception e;
            @Override
            public void run() {
                try {
                    r.run();
                } catch (Exception e1) {
                    this.e = e1;
                }
            }
            void reTthrow(){
                if( e != null)
                    System.out.println(e.getMessage());
            }
        }


        ReThrowTask task = new ReThrowTask();
        Thread thread = new Thread(task);
        thread.start();
        service.schedule(()->task.interrupt(), 1 ,TimeUnit.SECONDS);
        try {
            task.join(TimeUnit.SECONDS.toSeconds(1));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        task.reTthrow();
        if(!service.isShutdown()){
            service.shutdownNow();
        }
    }



}
