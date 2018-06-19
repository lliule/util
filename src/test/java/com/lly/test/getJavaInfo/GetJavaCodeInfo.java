package com.lly.test.getJavaInfo;

import java.lang.reflect.Method;

public class GetJavaCodeInfo {

    public void getInfo() throws ClassNotFoundException {
        Class<?> name = Class.forName("com.lly.test.thread.cache.Memoizer");
        Method[] methods = name.getMethods();
    }

}
