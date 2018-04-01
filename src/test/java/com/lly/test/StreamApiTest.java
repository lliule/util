package com.lly.test;

import com.lly.test.domain.User;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dana on 2018/4/1.
 *
 * @author dana
 */
public class StreamApiTest {

    /**
     * @return :
     * map    limit    count    min    max    concat    toArray    toArray
    * collect    collect    forEach    skip    peek    of    of    empty
    * filter    reduce    reduce    reduce    allMatch    anyMatch    distinct
    * findAny    findFirst    flatMap    flatMapToDouble    flatMapToInt    flatMapToLong
    * forEachOrdered    generate    iterate    mapToDouble    mapToInt    mapToLong
    * noneMatch    sorted    sorted    builder
     * @throws ClassNotFoundException
     */
    @Test
    public void testGetStreamApi() throws ClassNotFoundException {
        Class<?> name = Class.forName("java.util.stream.Stream");
        Method[] methods = name.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(method.getName());
        }
    }

    /**
     * map 函数 ： 遍历每一个元素，做出相关操作，然后返回操作后的数据流
     */
    @Test
    public void testMap(){
        List<String> strings = Arrays.asList("a", "b", "c");
        List<String> collect = strings.stream().map(String::toUpperCase).collect(Collectors.toList());
        System.out.println(Arrays.toString(collect.toArray()));

        ArrayList<User> users = new ArrayList<User>() {
            {
                add(new User("dana", "男", 28));
                add(new User("lily", "女", 26));
                add(new User("figo", "男", 35));
                add(new User("vic", "男", 35));
            }
        };

        List<String> nameList = users.stream().map(User::getName).collect(Collectors.toList());
        System.out.println(Arrays.toString(nameList.toArray()));

    }

    @Test
    public void testLimit(){

    }

}
