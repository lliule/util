package com.lly.test;

import org.junit.Test;

import java.util.Arrays;

import static java.lang.System.out;

public class SystemTest {

    @Test
    public void testNonaTime(){
        out.println(System.nanoTime());
    }

    @Test
    public void testCopyArr(){
        int[] i = {1,2,3};
        int[] j = {4,5,6};
        System.arraycopy(i, 1, j, 1, 2);
        out.println("list="+ Arrays.toString(i)+"list2"+Arrays.toString(j));
    }

    @Test
    public void testPropes(){
//        out.println(System.getProperties());
//        out.println('a'+System.lineSeparator()+'b');
        out.println(System.getenv("java_home"));
    }
}
