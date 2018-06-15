package com.lly.test.log;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogTest {
    private static Logger logger = LoggerFactory.getLogger(LogTest.class);

    @Test
    public void testLog(){
        NullPointerException exception = new NullPointerException("null");
        logger.error(exception.getMessage(),exception);
    }
}
