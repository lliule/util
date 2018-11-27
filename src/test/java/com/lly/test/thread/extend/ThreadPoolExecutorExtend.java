package com.lly.test.thread.extend;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 自己实现一个ThreadPoolExecutor，
 * 可以复写 beforeExecute，afterExecute，terminated
 * beforeExecute ： 线程执行前的操作，相当于前置拦截
 * afterExecute：后置拦截
 * terminated：关闭时处理
 */
public class ThreadPoolExecutorExtend extends ThreadPoolExecutor {
    public ThreadPoolExecutorExtend(int corePoolSize, int maximumPoolSize,
                                    long keepAliveTime, TimeUnit unit,
                                    BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        System.out.println("before execute [" + t.getName() + "]");
        super.beforeExecute(t, r);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        System.out.println("after execute [" + Thread.currentThread().getName() + "]");
        super.afterExecute(r, t);
    }

    @Override
    protected void terminated() {
        System.out.println("terminated");
        super.terminated();
    }
}
