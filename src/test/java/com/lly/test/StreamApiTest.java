package com.lly.test;

import com.lly.test.domain.User;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by dana on 2018/4/1.
 *
 * @author dana
 */
public class StreamApiTest {

    List<String> stringList;
    List<Integer> integerList;
    @Before
    public void init(){
        stringList = Arrays.asList("a","b","c","d");
        integerList = Arrays.asList(1,2,3,4,5);
    }
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
        List<String> list = Arrays.asList("a", "b", "c");
        List<String> collect = list.stream().limit(2).collect(Collectors.toList());
        System.out.println(Arrays.toString(collect.toArray()));
        // [a,b]
    }

    @Test
    public void testCount(){
        List<Integer> list = Arrays.asList(1, 2, 3, 4);
        long count = list.stream().count(); // 计算总数
        boolean match = list.stream().allMatch(i -> i > 1);
        System.out.println(match);
        System.out.println(count);
    }

    @Test
    public void testMinAndMax(){
        List<Integer> list = Arrays.asList(1, 2, 3, 4);
        Integer minValue = list.stream().min(Comparator.comparing(Integer::longValue)).get();
        System.out.println(minValue);
        Integer maxValue = list.stream().max(Integer::compareTo).get();
        System.out.println(maxValue);
    }

    @Test
    public void testConcat(){
        List<? extends Serializable> collect = Stream.concat(stringList.stream(), integerList.stream()).collect(Collectors.toList());
        //[a, b, c, d, 1, 2, 3, 4, 5]
        System.out.println(Arrays.toString(collect.toArray()));
    }

}
