package com.lly.test.log.logback;

import ch.qos.logback.classic.Level;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogbackExample {

    @Test
    public void test(){
        ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger)LoggerFactory.getLogger("com.lly");
        logger.setLevel(Level.DEBUG);
        Logger log = LoggerFactory.getLogger("com.lly.test");
        logger.trace("low fuel level");

        logger.debug("starting ");

        log.info("---com.lly.test---");
        log.debug("=====com.lly.test====");
        //ch.qos.logback.classic.Logger
        System.out.println(log.getClass().getName());

        log.info("--------{},{}",11,22);

    }
}
