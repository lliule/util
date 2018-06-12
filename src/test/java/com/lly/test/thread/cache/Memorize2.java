package com.lly.test.thread.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ConcurrentHashMap是线程同步的，使用它就不用对compute加synchronized
 * 了，但是这样做也有缺陷：两个线程可能会计算相同的值。即：如果两个线程都要
 * 计算f(27)，而且这个函数非常耗时,那么我们肯定希望只计算一次，第二次等待第
 * 一次计算完直接从缓存获取
 * @param <A>
 * @param <V>
 */
public class Memorize2<A,V> implements Computable<A,V> {
    private Map<A,V> cache = new ConcurrentHashMap<>();
    private Computable<A,V> c ;

    public Memorize2(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public V compute(A arg) throws Exception {
        V result = cache.get(arg);
        if(result == null){
            result = c.compute(arg);
            cache.put(arg,result);
        }
        return result;
    }
}
