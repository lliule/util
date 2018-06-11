package com.lly.test.thread.cache;

import java.math.BigInteger;

public class ExpensiveFunction implements Computable<String,BigInteger> {

    @Override
    public BigInteger compute(String arg) {
        return new BigInteger(arg);
    }
}
