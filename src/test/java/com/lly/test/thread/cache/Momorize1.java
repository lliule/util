package com.lly.test.thread.cache;

import java.util.HashMap;

/**
 * hashMap不是线程安全的，所以compute使用了synchronized来同步方法，
 * 但是如果多个线程都等待耗时的计算时，就会出现排队耗时。用这种方法做
 * 缓存不能带来多线程和缓存的优势。
 * @param <A>
 * @param <V>
 */
public class Momorize1<A,V> implements Computable<A,V> {
    private HashMap<A,V> cache = new HashMap<>();
    private Computable<A,V> c;

    public Momorize1(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public synchronized V compute(A arg) {
        V result = cache.get(arg);
        if(result == null){
           result = c.compute(arg);
           cache.put(arg,result);
        }
        return result;
    }
}
