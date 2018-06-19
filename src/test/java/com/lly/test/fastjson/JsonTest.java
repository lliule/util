package com.lly.test.fastjson;

import com.alibaba.fastjson.JSON;
import com.lly.test.domain.User;
import org.junit.Test;

public class JsonTest {
    @Test
    public void testToString(){
        // 不会抛出异常
        System.out.println(JSON.toJSONString(null));
    }


    @Test
    public void testToStringIgnoreNull(){
        User user = new User();
        user.setId(1);
        user.setAge(27);
        user.setName("dana");
        String userString = JSON.toJSONString(user);
        System.out.println(userString);
    }

}
