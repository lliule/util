package com.lly.test.baseTest;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SortTest {

    @Test
    public void test(){
        List<String> stringList = Arrays.asList("9", "13", "7");
        Collections.sort(stringList,(str1,str2)->{
            return Integer.parseInt(str1) - Integer.parseInt(str2);
        });
        System.out.println(Arrays.toString(stringList.toArray()));
    }
}
