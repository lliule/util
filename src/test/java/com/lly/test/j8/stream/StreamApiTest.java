package com.lly.test.j8.stream;

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
    List<User> userList ;
    @Before
    public void init(){
        stringList = Arrays.asList("a","b","c","d","1");
        integerList = Arrays.asList(1,2,3,4,5,1);
        userList = new ArrayList<User>() {
            {
                add(new User("dana", "male", 28));
                add(new User("dana", "male", 28));
                add(new User("ll", "male", 28));
                add(new User("vic", "male", 35));
                add(new User("candice", "female", 26));
                add(new User("breath", "female", 24));
                add(new User("breath", "female", 24));
            }
        };
    }
    /**
     * @return :
     * map    limit    count    min    max    concat    toArray    toArray
    * collect    collect    forEach    skip    peek    of    of    empty
    * filter    reduce    reduce    reduce    allMatch    anyMatch    distinct noneMatch
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

    /**
     * 将两个流合并，并且按照顺序，不会将流元素混在一起。
     */
    @Test
    public void testConcat(){
        List<? extends Serializable> collect = Stream.concat(stringList.stream(), integerList.stream()).collect(Collectors.toList());
        //[a, b, c, d, 1, 2, 3, 4, 5]
        System.out.println(Arrays.toString(collect.toArray()));
    }

    /**
     * 将stream转化成数组对象
     * 和 collect 对比：
     *  toArray 是转成数组，collect是转成集合。
     *  toArray无法根据结果类型转化,但是可以自定义结果的数据类型，collect根据结果的数据类型转化
     */
    @Test
    public void testToArray(){
        // 不声明结果类型，使用Object
        Object[] toArray = stringList.stream().map(String::toUpperCase).toArray();
        //声明结果类型
        String[] array = stringList.stream().map(String::toUpperCase).toArray(String[]::new);
        System.out.println(Arrays.toString(array));
//        List<Integer> collect = stringList.stream().map(Integer::valueOf).collect(Collectors.toList());
//        System.out.println(Arrays.toString(collect.toArray()));
    }

    @Test
    public void testSkip(){
        String[] array = stringList.stream().skip(1L).toArray(String[]::new);
        System.out.println(Arrays.toString(array));
    }

    //不改变原始值，只做中间数据处理
    @Test
    public void testPeek(){
        stringList.stream().peek(s-> System.out.println(s+"2")).forEach(System.out::println);
    }

    /**
     * 返回一个新的流
     */
    @Test
    public void testOf(){
        Stream.of(stringList).forEach(System.out::println);
    }

    /**
     * 返回一个空的流
     */
    @Test
    public void testEmpty(){
        Object[] toArray = Stream.empty().toArray();
        System.out.println(Arrays.toString(toArray));// []
    }

    /**
     * reduce 的以一个参数是恒等值，之后的所有操作都是在此值的基础上的。
     */
    @Test
    public void testReduce(){
        Integer reduce = integerList.stream().reduce(0, Integer::sum);
        System.out.println(reduce);
        String s = stringList.stream().reduce("", String::concat); // abcd
        String s1 = stringList.stream().reduce("prefix", String::concat);// prefixabcd
        System.out.println(s + "----------"+ s1);
    }

    /**
     * 匹配流api
     */
    @Test
    public void testMatch(){
        // 全部匹配成功则返回true
        boolean allMatch = userList.stream().allMatch(user -> user.getAge() > 30);
        System.out.println(allMatch);
        // 任意一个匹配成功则返回true
        boolean anyMatch = userList.stream().anyMatch(user -> user.getAge() > 30);
        System.out.println(anyMatch);
        // 所有的都不匹配返回true
        boolean noneMatch = userList.stream().noneMatch(user -> user.getAge() > 40);
        System.out.println(noneMatch);
    }

    @Test
    //去重。会通过 Object.equals()判断两个对象是否相等
    public void testDistinct(){
        List<User> collect = userList.stream().distinct().collect(Collectors.toList());
        System.out.println(Arrays.toString(collect.toArray()));
        Integer[] array = integerList.stream().distinct().toArray(Integer[]::new);
        System.out.println(Arrays.toString(array));
    }

    //--------------------------------------------------------
    /**
     * part 2：
     * findAny    findFirst    flatMap    flatMapToDouble    flatMapToInt    flatMapToLong
     *  forEachOrdered    generate    iterate    mapToDouble    mapToInt    mapToLong
     *  sorted    sorted    builder
     */
    //--------------------------------------------------------
    @Test
    public void testFindXxx(){
        User user = userList.stream().findAny().get();
        User user1 = userList.stream().findFirst().get();
        assert user == user1; // true
    }

    @Test
    public void testFlatXxx(){

    }


}
