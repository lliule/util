package com.lly.test.jdk;

import com.lly.test.jdk.abstractTest.ObjectSuperTest;
import org.junit.Test;

public class ObjectTest extends ObjectSuperTest {

    @Test
    public void testGetClass(){
        System.out.println(getClass().getName());
        System.out.println(getClass().getSuperclass());
    }
}
