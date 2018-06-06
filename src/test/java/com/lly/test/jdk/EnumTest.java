package com.lly.test.jdk;

import org.junit.Test;

import java.util.Arrays;

public class EnumTest {

    @Test
    public void enumTest(){
        System.out.println(Arrays.toString(ResType.values()));
    }
}


enum ResType{
    CLASSTYPE,
    HOMETYPE,
    COMPANYTYPE
}