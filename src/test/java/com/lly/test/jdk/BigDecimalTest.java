package com.lly.test.jdk;

import org.junit.Test;

import java.math.BigDecimal;

public class BigDecimalTest {

    @Test
    public void scaleTest(){
        BigDecimal decimal = new BigDecimal(78);
        float value = decimal.setScale(3, BigDecimal.ROUND_HALF_UP).floatValue();
        System.out.println(value);
    }

}
