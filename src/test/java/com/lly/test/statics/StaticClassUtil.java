package com.lly.test.statics;

import com.google.common.collect.Maps;
import com.lly.test.domain.User;

import java.util.HashMap;

public class StaticClassUtil {

    static HashMap<String, User> map = Maps.newHashMap();
    private static final String A = "a";
    private static final String B = "b";
    private static final String C = "c";
    static {
        System.out.println("---");
        map.put(A, new User("a","female",11));
        map.put(B, new User("b","male",12));
        map.put(C, new User("c","female",13));
    }

    public static User get(String key){
        return map.get(key);
    }

}
