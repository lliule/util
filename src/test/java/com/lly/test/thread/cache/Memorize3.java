package com.lly.test.thread.cache;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 本段代码大大优化了<code>{Memorize2}</code>中的
 * 并发性，但是有且仅有一个漏洞：
 *   由于Compute.compute是一个检查再运行的非原子代码块，
 *   那么就有可能：两个线程几乎在同一时间调用compute计算相同
 *   的值，双方都没有找到并都开始计算
 * @param <A>
 * @param <V>
 */
public class Memorize3<A,V> implements Computable<A,V> {
    private Map<A,FutureTask<V>> cache = new ConcurrentHashMap<>();
    private Computable<A,V> c;

    public Memorize3(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public V compute(A arg) throws ExecutionException, InterruptedException {
        FutureTask<V> task = cache.get(arg);
        if(task == null){
            Callable<V> callable = () -> c.compute(arg);
            FutureTask<V> futureTask = new FutureTask<>(callable);
            task = futureTask;
            cache.put(arg,futureTask);
            futureTask.run(); // 调用 c.compute()
        }
        return task.get();
    }
}
