package com.lly.test.thread.parallel;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

public class ParallelCompute {

    private ExecutorService executorService = Executors.newFixedThreadPool(10);

    private ArrayList<List<Integer>> list = new ArrayList<List<Integer>>(){
        {
            ArrayList<Integer> arrayList = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                arrayList.add(i);
            }
            add(arrayList);
            add(arrayList);
            add(arrayList);
            add(arrayList);
            add(arrayList);
        }
    };
    /**
     * 串行计算
     */
    @Test
    public void testSyncCompute(){
        Long result = syncCompute(list, 0L);
        System.out.println(result);
    }

    private Long syncCompute(List<List<Integer>> params,Long result){
        for (List<Integer> param : params) {
            result = compute(param,result);
            System.out.println(result);
        }
        return result;
    }

    private Long compute(List<Integer> param, Long result) {
        for (Integer i : param) {
            result += i;
        }
        return result;
    }


    /**
     * 并行计算并统计最终结果
     */
    @Test
    public void asyncCompute(){
        AtomicLong result  = new AtomicLong(0L);
        ArrayList<Future<Long>> futures = new ArrayList<>();
        list.forEach((params)->{
            Future<Long> submit = executorService.submit(() -> {
                return compute(params, 0L);
            });
            futures.add(submit);
        });
        futures.forEach((future)->{
            try {
                Long a = future.get();
                result.addAndGet(a);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE,TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(result.get());
    }

}
