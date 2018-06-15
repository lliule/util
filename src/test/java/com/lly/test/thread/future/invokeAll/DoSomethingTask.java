package com.lly.test.thread.future.invokeAll;

import java.util.concurrent.Callable;

public class DoSomethingTask  implements Callable<String> {
    private final String param;

    public DoSomethingTask(String param) {
        this.param = param;
    }

    @Override
    public String call() {
        System.out.println(Thread.currentThread().getName() + " --->  do something ");
        return param;
    }
}
