package com.lly.test.guava;

import com.google.common.util.concurrent.RateLimiter;

public class RateLimiterTest {

    public void testRateLimiter(){
        RateLimiter.create(2);
    }
}
