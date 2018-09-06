package com.lly.test.jdk;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class IteratorTest {

    @Test
    public void test(){
        List<Integer> list = Arrays.asList(1, 2, 3, 4);
        list.removeIf(next -> next == 2);
        System.out.println(Arrays.toString(list.toArray()));
    }


}
