package com.lly.test.thread.extend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class TimingExecutorPool extends ThreadPoolExecutor {
    private ThreadLocal<Long> startTime = new ThreadLocal<>();
    private AtomicLong totalTime = new AtomicLong(0L);
    private static Logger log = LoggerFactory.getLogger(TimingExecutorPool.class);
    public TimingExecutorPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        log.info(String.format("Thread %s run %s", t, r));
        startTime.set(System.nanoTime());
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        Long startTime = this.startTime.get();
        long endTime = System.nanoTime();
        long total = endTime - startTime;
        totalTime.addAndGet(total);
        log.info(String.format("Thread %s use %s ns",Thread.currentThread().getName(),total));
        super.afterExecute(r, t);
    }

    @Override
    protected void terminated() {
        log.info(String.format("Threads use total time %s",totalTime.get()));
        super.terminated();
    }
}
