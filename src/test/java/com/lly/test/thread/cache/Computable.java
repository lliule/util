package com.lly.test.thread.cache;

public interface Computable<A,V> {
    V compute(A arg) throws Exception;
}
