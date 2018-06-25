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


    /**
     *fastJson 对象转String能自动忽略Null属性
     * {"age":27,"id":1,"name":"dana"}
     * 没有Sex:null
     */
    @Test
    public void testToStringIgnoreNull(){
        User user = new User();
        user.setId(1);
        user.setAge(27);
        user.setName("dana");
//        user.setSex("male");
        String userString = JSON.toJSONString(user);
        System.out.println(userString);
    }

}
