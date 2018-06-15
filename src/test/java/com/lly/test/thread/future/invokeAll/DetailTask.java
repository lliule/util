package com.lly.test.thread.future.invokeAll;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.SECONDS;

public class DetailTask {
    private static ExecutorService exec = Executors.newFixedThreadPool(5);

    public static void main(String[] args) {
        List<String> detail = detail();
        System.out.println(Arrays.toString(detail.toArray()));
    }

    /**
     * 采用 invokeAll 能简单的处理批量任务的并发处理，
     * 返回一个 Future的集合，对应每个任务的结果。
     *
     * @return
     */
    private static  List<String> detail(){
        ArrayList<DoSomethingTask> tasks = new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            DoSomethingTask task = new DoSomethingTask(i + "");
            tasks.add(task);
        }
        ArrayList<String> result = new ArrayList<>(tasks.size());
        try {
            List<Future<String>> futures = exec.invokeAll(tasks, 1, SECONDS);
            Iterator<DoSomethingTask> taskIterator = tasks.iterator();
            for (Future<String> future : futures) {
                DoSomethingTask next = taskIterator.next();
                try {
                    result.add(future.get());
                } catch (ExecutionException e) {
                    result.add(e.getMessage());
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }

}
