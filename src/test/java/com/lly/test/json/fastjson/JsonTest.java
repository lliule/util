package com.lly.test.json.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lly.test.domain.User;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

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


    @Test
    public void testJSONArray(){
        String json = "{\"taskId\":\"e4f56bcd-74dd-402a-9783-4228d4c29e9f\",\"type\":\"tfbAnswerTask_dev\",\"userTaskIds\":[\"8e2ddb6b-4485-449d-b5bd-4f42ad876c45\"]}";
        JSONObject object = JSON.parseObject(json, JSONObject.class);
        List<Integer> test = (List<Integer>) object.get("userTaskIds");
        System.out.println(Arrays.toString(test.toArray()));
    }

}
