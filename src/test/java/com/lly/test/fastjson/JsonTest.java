package com.lly.test.fastjson;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

public class JsonTest {
    @Test
    public void testToString(){
        // 不会抛出异常
        System.out.println(JSON.toJSONString(null));
    }

}
