package com.lly.test.thread.schedule;

import com.lly.util.TimeUtil;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduleExcutorTest {

    private static final ScheduledExecutorService pool = Executors.newScheduledThreadPool(10);
    private static final ScheduledExecutorService singlePool = Executors.newSingleThreadScheduledExecutor();

    public static void main(String[] args) {
        run();
//        testSchedule();
//        testScheduleAtFixedRate();
//        testSingleShedule();
//        testSingelShcedulePeriod();
        testSingleSchedulePeriodDelay();
    }

    /**
     * 延时10s执行一次
     */
    public static void testSchedule(){
        pool.schedule(ScheduleExcutorTest::run
            ,1,TimeUnit.SECONDS);
    }

    /**
     * 延时3s执行，以后每隔1s执行一次
     */
    public static void testScheduleAtFixedRate(){
        pool.scheduleAtFixedRate(()-> System.out.println(TimeUtil.format(new Date(System.currentTimeMillis()))),3,1,TimeUnit.SECONDS);
    }

    public static void testSingleShedule(){
        singlePool.schedule(()-> System.out.println(TimeUtil.format(new Date(System.currentTimeMillis()))), 1,TimeUnit.SECONDS);
    }

    /**
     * 第二个Runnable在第一个完成后紧接着执行。
     * 如果线程耗时小于 period，则它和scheduleWithFixedDelay没有区别
     * 如果线程的耗时大于 Period，则它的执行间隔就是线程耗时的间隔。而
     * scheduleWithFixedDelay 的间隔是 线程耗时+ period 时间
     */
    public static void testSingelShcedulePeriod(){
        singlePool.scheduleAtFixedRate(ScheduleExcutorTest::print, 2,1,TimeUnit.SECONDS);
    }

    public static void testSingleSchedulePeriodDelay(){
        //TimeUtil.format(new Date(System.currentTimeMillis()))
        singlePool.scheduleWithFixedDelay(ScheduleExcutorTest::print, 2, 1 ,TimeUnit.SECONDS);
    }

    private static void print(){
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis());
    }

    private static void run() {
        System.out.println(System.currentTimeMillis());
    }
}
