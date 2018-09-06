package com.lly.test.statics;

import com.lly.test.domain.User;
import org.junit.Test;

import java.util.HashMap;

public class UtilTest {

    @Test
    public void test1(){
        User a = StaticClassUtil.get("a");
        HashMap<String, String> map = new HashMap<>();
    }

}
