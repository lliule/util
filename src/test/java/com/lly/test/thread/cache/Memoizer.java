package com.lly.test.thread.cache;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Memoizer<A,V> implements Computable<A,V> {
    private Map<A,FutureTask<V>> cache = new ConcurrentHashMap<>();
    private Computable<A,V> c;

    public Memoizer(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public V compute(final A arg) throws ExecutionException, InterruptedException {
        while (true){
            FutureTask<V> task = cache.get(arg);
            if(task == null){
                Callable<V> callable = ()->c.compute(arg);
                task = new FutureTask<>(callable);
                // 使用ConcurrentHashMap.putIfAbsent()方法
                //规避了检查在执行，保证了原子性
                cache.putIfAbsent(arg,task);
                task.run();
            }
            return task.get();
        }
    }
}
